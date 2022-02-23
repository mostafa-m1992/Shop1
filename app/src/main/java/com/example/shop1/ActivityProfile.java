package com.example.shop1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.shop1.Class.Put;

public class ActivityProfile extends AppCompatActivity {

    Button btnEdit, btnFav, btnExit;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        findViewProfile();
    }

    private void findViewProfile() {
        btnEdit = findViewById(R.id.btnEdit);
        btnFav = findViewById(R.id.btnFav);
        btnExit = findViewById(R.id.btnExit);
        
        onClickProfile();
    }

    private void onClickProfile() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ActivityEditProfile.class));
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = getSharedPreferences(Put.shared, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Put.email, "Login / Register");
                editor.apply();

                Intent intent = new Intent();
                intent.putExtra(Put.email, "Login / Register");
                intent.putExtra(Put.image, "");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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