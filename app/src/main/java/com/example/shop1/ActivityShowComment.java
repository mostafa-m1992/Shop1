package com.example.shop1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shop1.Adapters.AdapterComment;
import com.example.shop1.Class.Link;
import com.example.shop1.Class.MySingleton;
import com.example.shop1.Class.Put;
import com.example.shop1.Models.ModelComment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityShowComment extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton actionButton;

    AdapterComment adapterComment;
    List<ModelComment> modelComments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String id = getIntent().getStringExtra(Put.id);

        actionButton = findViewById(R.id.floataction);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityShowComment.this, ActivitySendComment.class);
                intent.putExtra(Put.id, id);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyComment);
        adapterComment = new AdapterComment(getApplicationContext(), modelComments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterComment);
        recyclerView.hasFixedSize();
        
        getComment(id);
    }

    private void getComment(final String id) {
        final String url = Link.linkGetComment;
        final ProgressDialog progressDialog = new ProgressDialog(ActivityShowComment.this);
        progressDialog.show();

        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString(Put.id);
                        String image = jsonObject.getString(Put.image);
                        String user = jsonObject.getString("user");
                        String comment = jsonObject.getString("comment");
                        String positive = jsonObject.getString("positive");
                        String negative = jsonObject.getString("negative");
                        String rating = jsonObject.getString("rating");
                        String confirm = jsonObject.getString("confirm");
                        String idproduct = jsonObject.getString("idproduct");
                        modelComments.add(new ModelComment(image, user, comment, positive, negative, Float.parseFloat(rating)));
                        adapterComment.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActivityShowComment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                map.put(Put.id, id);
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