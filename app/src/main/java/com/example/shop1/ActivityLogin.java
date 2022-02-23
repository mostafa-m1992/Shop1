package com.example.shop1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shop1.Class.Link;
import com.example.shop1.Class.MySingleton;
import com.example.shop1.Class.Put;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class ActivityLogin extends AppCompatActivity {

    RelativeLayout relativeLayout;
    Button signInButton, registerButton;
    TextInputEditText emailEdit, passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewLogin();
        onClickLogin();
    }

    private void onClickLogin() {
        relativeLayout.setOnClickListener(null);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ActivityRegister.class));
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(emailEdit.getText().toString(),passwordEdit.getText().toString());
            }
        });
    }

    private void login(final String email, final String password) {
        String url= Link.linkLogin;

        final ProgressDialog progressDialog=new ProgressDialog(ActivityLogin.this);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("username or password is false")){
                    Toast.makeText(ActivityLogin.this, response.toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    String allStr = response.toString();
                    int emailLength = email.length();
                    int responseLength = allStr.length();
                    String imageUrl = allStr.substring(emailLength, responseLength);

                    Intent intent = new Intent();
                    intent.putExtra(Put.email, email);
                    intent.putExtra(Put.image,imageUrl);
                    setResult(RESULT_OK, intent);
                    finish();

                    progressDialog.dismiss();
                }
            }
        };
        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActivityLogin.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
        StringRequest request=new StringRequest(Request.Method.POST,url,listener,errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put(Put.email, email);
                map.put(Put.password, password);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void findViewLogin() {
        relativeLayout = findViewById(R.id.relativeLayout);
        registerButton = findViewById(R.id.registerButton);

        signInButton = findViewById(R.id.signInButton);
        emailEdit = findViewById(R.id.emailEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
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