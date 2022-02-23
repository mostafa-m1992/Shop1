package com.example.shop1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shop1.Class.Link;
import com.example.shop1.Class.MySingleton;
import com.example.shop1.Class.Put;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_Wait extends AppCompatActivity {

    ProgressBar progress;
    String id, image, title, visit, price, label, date, only, sale, color, guarantee, description, freeprice, ratingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        findViewWait();
    }

    private void findViewWait() {
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);
        id = getIntent().getStringExtra(Put.id);
        freeprice = getIntent().getStringExtra(Put.freeprice);

        sendId(id);
    }

    private void sendId(String idd) {
        String url = Link.linkData;

        progress.setVisibility(View.VISIBLE);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        id = object.getString("id");
                        image = object.getString("image");
                        title = object.getString("title");
                        visit = object.getString("visit");
                        price = object.getString("price");
                        label = object.getString("label");
                        date = object.getString("date");
                        only = object.getString("only");
                        sale = object.getString("sale");
                        color = object.getString("color");
                        guarantee = object.getString("guarantee");
                        description = object.getString("description");
                        ratingbar = object.getString("finalrating");
                        float f = Float.parseFloat(ratingbar);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Activity_Wait.this, ActivityProduct.class);

                intent.putExtra(Put.id, id);
                intent.putExtra(Put.image, image);
                intent.putExtra(Put.title, title);
                intent.putExtra(Put.visit, visit);
                intent.putExtra(Put.price, price);
                intent.putExtra(Put.label, label);
                intent.putExtra(Put.date, date);
                intent.putExtra(Put.only, only);
                intent.putExtra(Put.sale, sale);
                intent.putExtra(Put.color, color);
                intent.putExtra(Put.guarantee, guarantee);
                intent.putExtra(Put.description, description);
                intent.putExtra(Put.rating, ratingbar);

                if (!freeprice.equals(""))
                    intent.putExtra(Put.freeprice, freeprice);
                else
                    intent.putExtra(Put.freeprice,"");

                startActivity(intent);

                finish();

                progress.setVisibility(View.GONE);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(Put.id, idd);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}