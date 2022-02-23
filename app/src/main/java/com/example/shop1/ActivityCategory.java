package com.example.shop1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.shop1.Adapters.AdapterCategory;
import com.example.shop1.Class.Link;
import com.example.shop1.Class.MySingleton;
import com.example.shop1.Models.ModelCategory;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ActivityCategory extends AppCompatActivity {

    RecyclerView recyclerCategory;

    AdapterCategory adapterCategory;
    List<ModelCategory> modelCategoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewCategory();
    }

    private void findViewCategory() {
        recyclerCategory = findViewById(R.id.recyclerCategory);

        setDataCategory();
    }

    private void setDataCategory() {
        adapterCategory = new AdapterCategory(this, modelCategoryList);
        recyclerCategory.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerCategory.setAdapter(adapterCategory);

        String url= Link.linkCategory;

        final ProgressDialog progressDialog = new ProgressDialog(ActivityCategory.this);
        progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                ModelCategory[] categories = gson.fromJson(response.toString(),ModelCategory[].class);
                for (int i = 0 ; i<categories.length;i++)
                {
                    modelCategoryList.add(categories[i]);
                    adapterCategory.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ActivityCategory.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null,listener,errorListener);

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