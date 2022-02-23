package com.example.shop1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.shop1.Adapters.AdapterFree;
import com.example.shop1.Adapters.AdapterOnly;
import com.example.shop1.Adapters.AdapterSales;
import com.example.shop1.Adapters.AdapterVisit;
import com.example.shop1.Adapters.MainSliderAdapter;
import com.example.shop1.Class.Link;
import com.example.shop1.Class.MySingleton;
import com.example.shop1.Class.Put;
import com.example.shop1.Models.MainSliderModel;
import com.example.shop1.Models.ModelFree;
import com.example.shop1.Models.ModelOnly;
import com.example.shop1.Models.ModelSales;
import com.example.shop1.Models.ModelVisit;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    SliderView mainSlider;
    ArrayList<MainSliderModel> mainSliderModelArrayList = new ArrayList<>();
    MainSliderAdapter mainSliderAdapter;

    Toolbar toolbarAppbar;
    DrawerLayout drawer;

    SharedPreferences preferences;

    RecyclerView recyclerFree, recyclerOnly, recyclerVisit, recyclerSales;
    CardView cardCategory;
    TextView textLogin, textCount;
    ImageView loginIconToolbar, imageBasketMain;
    private CircleImageView circleImageUser;

    String session, imageuser;
    private String data = "";
    ArrayList<String> strings = new ArrayList<>();

    AdapterFree adapterFree;
    List<ModelFree> modelFreeList = new ArrayList<>();

    AdapterOnly adapterOnly;
    List<ModelOnly> modelOnlyList = new ArrayList<>();

    AdapterVisit adapterVisit;
    List<ModelVisit> modelVisitList = new ArrayList<>();

    AdapterSales adapterSales;
    List<ModelSales> modelSalesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(toolbarAppbar);

        preferences = getSharedPreferences(Put.shared, MODE_PRIVATE);
        textLogin.setText(preferences.getString(Put.email,"login / register"));
        session = preferences.getString(Put.email, "login / register");

        findViewMain();
        setDataMainSlider();
    }

    private void findViewMain() {
        mainSlider = findViewById(R.id.mainSlider);
        toolbarAppbar = findViewById(R.id.toolbarAppbar);
        drawer = findViewById(R.id.drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbarAppbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        recyclerFree = findViewById(R.id.recyclerFree);
        recyclerOnly = findViewById(R.id.recyclerOnly);
        recyclerVisit = findViewById(R.id.recyclerVisit);
        recyclerSales = findViewById(R.id.recyclerSales);

        cardCategory = findViewById(R.id.cardCat);

        textLogin = findViewById(R.id.textLogin);
        loginIconToolbar = findViewById(R.id.loginIconToolbar);
        imageBasketMain = findViewById(R.id.imageBasketMain);
        textCount = findViewById(R.id.textCount);

        circleImageUser=findViewById(R.id.circleImageUser);
        imageuser = preferences.getString(Put.image, "");
        if (imageuser.equals("")) {
            Picasso
                    .get()
                    .load(R.drawable.profileavatar)
                    .into(circleImageUser);
        } else {
            Picasso
                    .get()
                    .load(imageuser)
                    .into(circleImageUser);
        }

        onClickMain();
        getDataFree();
        getDataOnly();
        getDataVisit();
        getDataSales();
    }

    private void getDataSales() {
        adapterSales = new AdapterSales(this, modelSalesList);
        recyclerSales.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerSales.setAdapter(adapterSales);

        String url = Link.linkSales;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                ModelSales[] sales = gson.fromJson(response.toString(), ModelSales[].class);
                for (int i = 0; i < sales.length; i++) {
                    modelSalesList.add(sales[i]);
                    adapterSales.notifyDataSetChanged();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void getDataVisit() {
        adapterVisit = new AdapterVisit(this, modelVisitList);
        recyclerVisit.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerVisit.setAdapter(adapterVisit);

        String url = Link.linkVisit;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                ModelVisit[] visits = gson.fromJson(response.toString(), ModelVisit[].class);
                for (int i = 0; i < visits.length; i++) {
                    modelVisitList.add(visits[i]);
                    adapterVisit.notifyDataSetChanged();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void getDataOnly() {
        adapterOnly = new AdapterOnly(this, modelOnlyList);
        recyclerOnly.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerOnly.setAdapter(adapterOnly);

        String url = Link.linkOnly;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                ModelOnly[] onlies = gson.fromJson(response.toString(), ModelOnly[].class);
                for (int i = 0; i < onlies.length; i++) {
                    modelOnlyList.add(onlies[i]);
                    adapterOnly.notifyDataSetChanged();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener,errorListener);

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void getDataFree() {
        adapterFree = new AdapterFree(getApplicationContext(), modelFreeList);
        recyclerFree.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerFree.setAdapter(adapterFree);

        String url = Link.linkFree;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                ModelFree[] frees = gson.fromJson(response.toString(), ModelFree[].class);
                for (int i = 0; i < frees.length; i++) {
                    modelFreeList.add(frees[i]);
                    adapterFree.notifyDataSetChanged();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void onClickMain() {
        cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ActivityCategory.class));
            }
        });

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textLogin.getText().equals("login / register")){
                    Intent intent=new Intent(MainActivity.this, ActivityLogin.class);
                    startActivityForResult(intent, Put.REQUEST_CODE);
                }else {
                    Intent intent=new Intent(MainActivity.this, ActivityProfile.class);
                    //startActivity(intent);
                    startActivityForResult(intent,Put.REQUEST_EXIT);
                }
            }
        });

        loginIconToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textLogin.getText().equals("login / register")){
                    Intent intent=new Intent(MainActivity.this, ActivityLogin.class);
                    startActivityForResult(intent, Put.REQUEST_CODE);
                }else {
                    Intent intent=new Intent(MainActivity.this, ActivityProfile.class);
                    //startActivity(intent);
                    startActivityForResult(intent,Put.REQUEST_EXIT);
                }
            }
        });

        imageBasketMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, ActivityBasket.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setDataMainSlider() {
        mainSliderModelArrayList.add(new MainSliderModel(R.drawable.onlineshop1));
        mainSliderModelArrayList.add(new MainSliderModel(R.drawable.onlineshop2));
        mainSliderModelArrayList.add(new MainSliderModel(R.drawable.onlineshop3));
        mainSliderModelArrayList.add(new MainSliderModel(R.drawable.onlineshop4));
        mainSliderModelArrayList.add(new MainSliderModel(R.drawable.onlineshop5));

        mainSliderAdapter = new MainSliderAdapter(this, mainSliderModelArrayList);
        mainSlider.setSliderAdapter(mainSliderAdapter);

        mainSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        mainSlider.setScrollTimeInSec(3);
        mainSlider.setAutoCycle(true);
        mainSlider.startAutoCycle();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Put.REQUEST_CODE && resultCode == RESULT_OK){
            String email = data.getStringExtra(Put.email);
            textLogin.setText(email);
            SharedPreferences sharedPreferences = getSharedPreferences(Put.shared, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Put.email, email);
            editor.apply();
        }
        else if(requestCode == Put.REQUEST_EXIT && resultCode == RESULT_OK){
            String email = data.getStringExtra(Put.email);
            textLogin.setText(email);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCount(session);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void getCount(final String email) {
        String url = Link.linkGetCount;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                textCount.setText(response.toString());

                SharedPreferences preferences = getSharedPreferences("c", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("count", textCount.getText().toString());
                editor.apply();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };


        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(Put.email, email);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}