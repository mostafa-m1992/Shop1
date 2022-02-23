package com.example.shop1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shop1.Class.Link;
import com.example.shop1.Class.MySingleton;
import com.example.shop1.Class.Put;

import java.util.HashMap;
import java.util.Map;

public class ActivitySendComment extends AppCompatActivity {

    EditText editComment, editPositiveComment, editNegativeComment;
    RatingBar ratingbarFinal;
    Button btnComment;

    String session, imageUser, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_comment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getStringExtra(Put.id);
        SharedPreferences preferences = getSharedPreferences(Put.shared, MODE_PRIVATE);
        session = preferences.getString(Put.email, "login / register");
        imageUser = preferences.getString(Put.image,"");

        findViewSendComment();
    }

    private void findViewSendComment() {
        editComment = findViewById(R.id.editComment);
        editPositiveComment = findViewById(R.id.editPositiveComment);
        editNegativeComment = findViewById(R.id.editNegativeComment);
        ratingbarFinal = findViewById(R.id.ratingbarFinal);
        btnComment = findViewById(R.id.btnComment);
        
        onClickSendComment();
    }

    private void onClickSendComment() {
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (session.equals("login / register") || imageUser.equals(""))
                {
                    Toast.makeText(ActivitySendComment.this, "You are not logged in", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (editComment.getText().toString().equals("")|| editNegativeComment.getText().toString().equals("") || editPositiveComment.getText().toString().equals(""))
                    {
                        Toast.makeText(ActivitySendComment.this, "Please fill in the required quantities", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        sendComment(id, session, imageUser, editComment.getText().toString(), editPositiveComment.getText().toString(),
                                editNegativeComment.getText().toString(), ratingbarFinal.getRating()
                        );
                    }
                }
            }
        });
    }

    private void sendComment(final String idproduct,final String email,final String imageuser,final String comment,final String positive,final String negative,final float rating)
    {
        String url = Link.linkSendComment;

        final ProgressDialog progressDialog = new ProgressDialog(ActivitySendComment.this);
        progressDialog.show();
        progressDialog.setMessage("Posting a Comment");

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toString().equals("1")) {
                    Toast.makeText(ActivitySendComment.this, "Information was recorded", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActivitySendComment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST,url,listener,errorListener)
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put(Put.id, idproduct);
                map.put(Put.email, email);
                map.put(Put.image, imageuser);
                map.put(Put.negative, negative);
                map.put(Put.comment, comment);
                map.put(Put.positive, positive);
                map.put(Put.rating, String.valueOf(rating));
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}