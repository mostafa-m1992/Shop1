package com.example.shop1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop1.Class.Link;
import com.example.shop1.Class.MySingleton;
import com.example.shop1.Class.RuntimePermissionsActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityRegister extends RuntimePermissionsActivity {

    LinearLayout linearLayout;
    Button btnRegister;
    TextInputEditText emailRegEdit, passwordRegEdit;
    CircleImageView circleImageUserRegister;
    TextView textTakeImage;

    private String imagencode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewRegister();
        onClickRegister();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1001);
    }

    @Override
    public void onPermissionsDeny(int requestCode) {

    }

    private void onClickRegister() {
        linearLayout.setOnClickListener(null);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(emailRegEdit.getText().toString().trim(), passwordRegEdit.getText().toString().trim(), imagencode);
            }
        });
        textTakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Register.super.requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
            }
        });
    }

    private void register(final String email, final String password, final String image) {
        String url = Link.linkRegister;

        final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.show();

        emailRegEdit.setText("");
        passwordRegEdit.setText("");

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("email", email);
                map.put("password", password);
                map.put("image", image);
                return map;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void findViewRegister() {
        linearLayout = findViewById(R.id.linearLayout);
        btnRegister = findViewById(R.id.btnRegister);
        emailRegEdit = findViewById(R.id.emailRegEdit);
        passwordRegEdit = findViewById(R.id.passwordRegEdit);
        circleImageUserRegister = findViewById(R.id.circleImageUserRegister);
        textTakeImage = findViewById(R.id.textTakeImage);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1001 && resultCode == RESULT_OK) {

            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            circleImageUserRegister.setImageBitmap(thumbnail);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            imagencode = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            c.close();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}