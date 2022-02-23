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
import com.example.shop1.Models.ModelOnly;
import com.example.shop1.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterOnly extends RecyclerView.Adapter<AdapterOnly.MyViewHolderOnly> {

    Context context;
    List<ModelOnly> modelOnlies;

    public AdapterOnly(Context context, List<ModelOnly> modelOnlies) {
        this.context = context;
        this.modelOnlies = modelOnlies;
    }

    @NonNull
    @Override
    public MyViewHolderOnly onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_only, parent, false);
        return new MyViewHolderOnly(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderOnly holder, int position) {
        ModelOnly modelOnly = modelOnlies.get(position);

        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(modelOnly.getPrice());
        holder.textPriceOnly.setText(price + " " + "$");

        holder.textTitleOnly.setText(modelOnly.getTitle());
        holder.textVisitOnly.setText(modelOnly.getVisit());

        Picasso.get()
                .load(modelOnly.getImage())
                .into(holder.imageOnly);

        holder.cardViewOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdapterOnly.this.context, Activity_Wait.class);
                intent.putExtra(Put.id, modelOnly.getId() + "");

                intent.putExtra(Put.freeprice, "");

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelOnlies.size();
    }

    public class MyViewHolderOnly extends RecyclerView.ViewHolder{

        MaterialCardView cardViewOnly;
        ImageView imageOnly;
        TextView textTitleOnly, textVisitOnly, textPriceOnly;

        public MyViewHolderOnly(@NonNull View itemView) {
            super(itemView);

            cardViewOnly = itemView.findViewById(R.id.cardViewOnly);
            imageOnly = itemView.findViewById(R.id.imageOnly);
            textTitleOnly = itemView.findViewById(R.id.textTitleOnly);
            textVisitOnly = itemView.findViewById(R.id.textVisitOnly);
            textPriceOnly = itemView.findViewById(R.id.textPriceOnly);
        }
    }
}
