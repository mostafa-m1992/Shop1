package com.example.shop1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shop1.Adapters.AdapterSliderProduct;
import com.example.shop1.Class.Link;
import com.example.shop1.Class.MySingleton;
import com.example.shop1.Class.Put;
import com.example.shop1.Models.ModelSliderProduct;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.card.MaterialCardView;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityProduct extends AppCompatActivity {

    SliderView productSlider;
    AppBarLayout app_bar_layout;
    ImageView imageBack, imageBasket;
    TextView textCountBasket;

    List<ModelSliderProduct> modelSliderProductList = new ArrayList<>();
    AdapterSliderProduct adapterSliderProduct;

    ArrayList<String> strings = new ArrayList<>();

    String id, image, title, color, guarantee, price, visit, label, freePrice, session;

    int ratingbar;

    TextView textTitle, textColor, textGuarantee, textPrice, textDescription, textNext, textviewFree;

    MaterialCardView cardViewBasket, cardViewComment;

    boolean b = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        SharedPreferences preferences = getSharedPreferences(Put.shared, MODE_PRIVATE);
        session = preferences.getString(Put.email,"Login / Register");

        findViewProduct();
    }

    private void findViewProduct() {
        freePrice = getIntent().getStringExtra(Put.freeprice);
        textviewFree = findViewById(R.id.textPriceShowfree);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        textPrice = findViewById(R.id.textPriceShow);
        textviewFree.setVisibility(View.GONE);
        if (!freePrice.equals("")) {
            textviewFree.setVisibility(View.VISIBLE);
            textviewFree.setText(freePrice + " " + "$");
            textPrice.setTextColor(Color.RED);
            textPrice.setPaintFlags(textPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textviewFree.setVisibility(View.GONE);
        }

        productSlider = findViewById(R.id.productSlider);
        getSlider(id);

        textCountBasket = findViewById(R.id.textCountBasket);
        imageBack = findViewById(R.id.imageBack);
        imageBasket = findViewById(R.id.imageBasket);

        app_bar_layout = findViewById(R.id.app_bar_layout);
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scroll = -(verticalOffset);
                Log.i("collaps", scroll + "");
                if (scroll >= 575) {
                    imageBack.setColorFilter(Color.rgb(255, 255, 255));
                    imageBasket.setColorFilter(Color.rgb(255, 255, 255));
                    textCountBasket.setTextColor(Color.rgb(255, 255, 255));
                } else {
                    imageBack.setColorFilter(Color.rgb(189, 189, 189));
                    imageBasket.setColorFilter(Color.rgb(189, 189, 189));
                    textCountBasket.setTextColor(Color.rgb(189, 189, 189));
                }
            }
        });

        id = getIntent().getStringExtra(Put.id);
        image = getIntent().getStringExtra(Put.image);
        guarantee = getIntent().getStringExtra(Put.guarantee);
        color = getIntent().getStringExtra(Put.color);
        title = getIntent().getStringExtra(Put.title);

        cardViewBasket = findViewById(R.id.cardViewBasket);
        cardViewComment = findViewById(R.id.cardViewComment);

        textTitle = findViewById(R.id.textTitleShow);
        textColor = findViewById(R.id.textcolorShow);
        textGuarantee = findViewById(R.id.textGarantyShow);

        textDescription = findViewById(R.id.textDescriptionShow);
        textNext = findViewById(R.id.textNextShow);
        textNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b) {
                    textDescription.setSingleLine(false);
                    textNext.setText("close");
                    b = false;
                } else {
                    textDescription.setSingleLine(true);
                    textNext.setText("read more");
                    b = true;
                }
            }
        });

        getIntentData();
        onClickProduct();
    }

    private void onClickProduct() {
        cardViewBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (session.equals("Login / Register")) {
                    Toast.makeText(ActivityProduct.this, "please inter to your account", Toast.LENGTH_SHORT).show();
                } else {
                    if (!freePrice.equals(""))
                        sendBasket(id, session, title, color, image, guarantee, freePrice);
                    else
                        sendBasket(id, session, title, color, image, guarantee, price);
                }
            }
        });
        
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityProduct.this, ActivityBasket.class));
            }
        });

        cardViewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProduct.this, ActivityShowComment.class);
                intent.putExtra(Put.id, id);
                startActivity(intent);
            }
        });
    }

    private void sendBasket(String id, String email, String title, String color, String image, String guarantee, String price) {
        String url = Link.linkBasket;

        final ProgressDialog progressDialog = new ProgressDialog(ActivityProduct.this);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                String count = textCountBasket.getText().toString();
                int total = 0;
                total = Integer.parseInt(count) + 1;
                textCountBasket.setText(String.valueOf(total));

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
                map.put(Put.id, id);
                map.put(Put.title, title);
                map.put(Put.color, color);
                map.put(Put.guarantee, guarantee);
                map.put(Put.image, image);
                map.put(Put.price, price);
                map.put(Put.email, email);

                return map;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void getIntentData() {
        visit = getIntent().getStringExtra(Put.visit);
        label = getIntent().getStringExtra(Put.label);
        textTitle.setText(title);
        textColor.setText(color);
        textGuarantee.setText(guarantee);
        price = getIntent().getStringExtra(Put.price);

        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price1 = decimalFormat.format(Integer.valueOf(price)) + " " + "$";
        textPrice.setText(price1);
        textDescription.setText(getIntent().getStringExtra(Put.description));
    }

    private void getSlider(final String idd) {
        adapterSliderProduct = new AdapterSliderProduct(getApplicationContext(), modelSliderProductList);

        productSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        productSlider.setSliderAdapter(adapterSliderProduct);
        productSlider.setScrollTimeInSec(3);
        productSlider.setAutoCycle(true);
        productSlider.startAutoCycle();

        final String url = Link.linkImage;

        final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String pics = jsonObject.getString("pics");
                        strings.add(pics);
                    }
                } catch (Exception e) {
                }
                progressDialog.dismiss();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
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