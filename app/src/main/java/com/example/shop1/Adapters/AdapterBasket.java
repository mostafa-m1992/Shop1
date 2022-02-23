package com.example.shop1.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shop1.Class.Link;
import com.example.shop1.Class.MySingleton;
import com.example.shop1.Class.OnloadPrice;
import com.example.shop1.Class.Put;
import com.example.shop1.Models.ModelBasket;
import com.example.shop1.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterBasket extends RecyclerView.Adapter<AdapterBasket.MyViewHolderBasket> {

    Context context;
    List<ModelBasket> modelBaskets;

    private OnloadPrice onloadPrice;

    public AdapterBasket(Context context, List<ModelBasket> modelBaskets) {
        this.context = context;
        this.modelBaskets = modelBaskets;
    }

    public void setOnloadPrice(OnloadPrice onloadPrice) {
        this.onloadPrice = onloadPrice;
    }

    @NonNull
    @Override
    public MyViewHolderBasket onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_basket,parent,false);
        return new MyViewHolderBasket(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderBasket holder, @SuppressLint("RecyclerView") int position) {
        final ModelBasket basket = modelBaskets.get(position);

        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(basket.getPrice()));
        String allprice = decimalFormat.format(Integer.valueOf(basket.getAllprice()));

        holder.textprice.setText(price + " " + "$");
        holder.textallprice.setText(allprice + " " + "$");

        holder.textGuarantee.setText(basket.getGuarantee());
        holder.textnumber.setText(basket.getNumber() + " " + "digit");
        holder.texttitle.setText(basket.getTitle());
        holder.textcolor.setText(basket.getColor());

        Picasso
                .get()
                .load(basket.getImage())
                .into(holder.imageBasket);

        holder.btnDeleteBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onloadPrice != null){
                    onloadPrice.onloadprice();

                    deleteBasket(basket.getId() + "");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            modelBaskets.remove(position);
                            notifyItemChanged(position);
                            notifyItemRangeRemoved(position, modelBaskets.size());
                        }
                    }, 200);
                }
            }
        });
    }

    private void deleteBasket(final String id) {
        String url = Link.linkDeleteBasket;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(Put.id, id);
                return map;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public int getItemCount() {
        return modelBaskets.size();
    }

    public class MyViewHolderBasket extends RecyclerView.ViewHolder{

        ImageView imageBasket;
        TextView texttitle,textnumber,textcolor,textGuarantee,textprice,textallprice;
        Button btnDeleteBasket;

        public MyViewHolderBasket(@NonNull View itemView) {
            super(itemView);

            texttitle = itemView.findViewById(R.id.textTitleBasket);
            textcolor = itemView.findViewById(R.id.textColorBasket);
            textnumber = itemView.findViewById(R.id.textNumberBasket);
            textGuarantee = itemView.findViewById(R.id.textGarantyBasket);
            textprice = itemView.findViewById(R.id.priceBasket);
            textallprice = itemView.findViewById(R.id.textAllpriceBasket);
            btnDeleteBasket = itemView.findViewById(R.id.btnDeleteBasket);
            imageBasket = itemView.findViewById(R.id.imageBasket);
        }
    }
}
