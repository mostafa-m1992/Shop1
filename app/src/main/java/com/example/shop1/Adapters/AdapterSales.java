package com.example.shop1.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop1.Activity_Wait;
import com.example.shop1.Class.Put;
import com.example.shop1.Models.ModelSales;
import com.example.shop1.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterSales extends RecyclerView.Adapter<AdapterSales.MyViewHolderSales> {

    Context context;
    List<ModelSales> modelSales;

    public AdapterSales(Context context, List<ModelSales> modelSales) {
        this.context = context;
        this.modelSales = modelSales;
    }

    @NonNull
    @Override
    public MyViewHolderSales onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sales, parent, false);
        return new MyViewHolderSales(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderSales holder, int position) {
        ModelSales sales = modelSales.get(position);

        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(sales.getPrice());
        holder.textPriceSales.setText(price + " " + "$");

        holder.textTitleSales.setText(sales.getTitle());
        holder.textVisitSales.setText(sales.getVisit());

        Picasso.get()
                .load(sales.getImage())
                .into(holder.imageSales);

        holder.cardViewSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdapterSales.this.context, Activity_Wait.class);
                intent.putExtra(Put.id, sales.getId() + "");

                intent.putExtra(Put.freeprice, "");

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelSales.size();
    }

    public class MyViewHolderSales extends RecyclerView.ViewHolder{

        MaterialCardView cardViewSales;
        ImageView imageSales;
        TextView textTitleSales, textVisitSales, textPriceSales;

        public MyViewHolderSales(@NonNull View itemView) {
            super(itemView);

            cardViewSales = itemView.findViewById(R.id.cardViewSales);
            imageSales = itemView.findViewById(R.id.imageSales);
            textTitleSales = itemView.findViewById(R.id.textTitleSales);
            textVisitSales = itemView.findViewById(R.id.textVisitSales);
            textPriceSales = itemView.findViewById(R.id.textPriceSales);
        }
    }
}
