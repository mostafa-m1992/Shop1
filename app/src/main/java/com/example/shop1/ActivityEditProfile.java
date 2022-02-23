package com.example.shop1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop1.Class.Link;
import com.example.shop1.Class.MySingleton;
import com.example.shop1.Class.Put;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class ActivityEditProfile extends AppCompatActivity {

    TextInputEditText userEdit, phoneEdit, addressEdit;
    Button btnEditProfile;

    SharedPreferences preferences;
    private String emailEdit;

    SharedPreferences preferences2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences(Put.shared, MODE_PRIVATE);
        emailEdit = preferences.getString(Put.email, "email");

        findViewEditProfile();
    }

    private void findViewEditProfile() {
        userEdit = findViewById(R.id.userEdit);
        phoneEdit = findViewById(R.id.phoneEdit);
        addressEdit = findViewById(R.id.addressEdit);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        preferences2 = getSharedPreferences(Put.shared2, MODE_PRIVATE);
        userEdit.setText(preferences2.getString(Put.username, ""));
        phoneEdit.setText(preferences2.getString(Put.phone, ""));
        addressEdit.setText(preferences2.getString(Put.address, ""));

        onClickEditProfile();
    }

    private void onClickEditProfile() {
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userEdit.getText().toString().trim();
                String phone = phoneEdit.getText().toString().trim();
                String address = addressEdit.getText().toString().trim();
                if (user.equalsIgnoreCase("") || phone.equalsIgnoreCase("") ||address.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "please insert your data", Toast.LENGTH_SHORT).show();
                } else {
                    editProfile(emailEdit, user, phone, address);
                }
            }
        });
    }

    private void editProfile(final String email, final String username, final String phone, final String address) {
        String url = Link.linkEdit;

        final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toString().equals("Editing information completed successfully")) {
                    preferences2 = getSharedPreferences(Put.shared2, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences2.edit();
                    editor.putString(Put.username, username);
                    editor.putString(Put.phone, phone);
                    editor.putString(Put.address, address);
                    editor.apply();

                    Toast.makeText(getApplicationContext(), "inserted data", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        } ;

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(Put.email, email);
                map.put(Put.username, username);
                map.put(Put.phone, phone);
                map.put(Put.address, address);
                return map;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}