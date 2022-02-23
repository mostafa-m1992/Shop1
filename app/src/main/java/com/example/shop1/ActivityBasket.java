package com.example.shop1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shop1.Adapters.AdapterBasket;
import com.example.shop1.Class.Link;
import com.example.shop1.Class.MySingleton;
import com.example.shop1.Class.OnloadPrice;
import com.example.shop1.Class.Put;
import com.example.shop1.Models.ModelBasket;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityBasket extends AppCompatActivity {

    LinearLayout lnrBasket;
    RecyclerView recyBasket;
    TextView textTotal;
    TextView textZarinpal;

    AdapterBasket adapterBasket;
    List<ModelBasket> modelBaskets = new ArrayList<>();

    String session, title, number;
    int nm = 0;
    int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences preferences = getSharedPreferences(Put.shared, MODE_PRIVATE);
        session = preferences.getString(Put.email, "Login / Register");
        
        findViewBasket();
    }

    private void findViewBasket() {
        textTotal = findViewById(R.id.textTotal);
        textZarinpal = findViewById(R.id.textZarinpal);
        lnrBasket = findViewById(R.id.lnrBasket);
        recyBasket = findViewById(R.id.recyBasket);

        adapterBasket = new AdapterBasket(getApplicationContext(), modelBaskets);
        adapterBasket.setOnloadPrice(new OnloadPrice() {
            @Override
            public void onloadprice() {
                recreate();
                Toast.makeText(ActivityBasket.this, "Your cart has been updated", Toast.LENGTH_SHORT).show();
            }
        });

        recyBasket.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyBasket.setAdapter(adapterBasket);
        
        getBasket(session);
    }

    private void getBasket(String email) {
        String url = Link.linkGetBasket;

        final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        title = object.getString(Put.title);

                        String id = object.getString(Put.id);
                        String image = object.getString(Put.image);
                        String color = object.getString(Put.color);
                        String guarantee = object.getString(Put.guarantee);
                        String price = object.getString(Put.price);
                        String allPrice = object.getString(Put.allprice);
                        number = object.getString(Put.number);
                        modelBaskets.add(new ModelBasket(Integer.parseInt(id),title, image, number, color, guarantee, price, allPrice));
                        adapterBasket.notifyDataSetChanged();
                        totalPrice += Integer.parseInt(allPrice);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();

                DecimalFormat decimalFormat = new DecimalFormat("###,###");
                String price1 = decimalFormat.format(Integer.valueOf(totalPrice + ""));
                textTotal.setText(price1 + "" + " " + "$");
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener)

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(Put.email, email);
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