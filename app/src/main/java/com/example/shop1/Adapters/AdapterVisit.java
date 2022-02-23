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
import com.example.shop1.Models.ModelVisit;
import com.example.shop1.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterVisit extends RecyclerView.Adapter<AdapterVisit.MyViewHolderVisit> {

    Context context;
    List<ModelVisit> modelVisits;

    public AdapterVisit(Context context, List<ModelVisit> modelVisits) {
        this.context = context;
        this.modelVisits = modelVisits;
    }

    @NonNull
    @Override
    public MyViewHolderVisit onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_visit, parent, false);
        return new MyViewHolderVisit(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderVisit holder, int position) {
        ModelVisit modelVisit = modelVisits.get(position);

        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(modelVisit.getPrice());
        holder.textPriceVisit.setText(price + " " + "$");

        holder.textTitleVisit.setText(modelVisit.getTitle());
        holder.textVisitVisit.setText(modelVisit.getVisit());

        Picasso.get()
                .load(modelVisit.getImage())
                .into(holder.imageVisit);

        holder.cardViewVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdapterVisit.this.context, Activity_Wait.class);
                intent.putExtra(Put.id, modelVisit.getId() + "");

                intent.putExtra(Put.freeprice, "");

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelVisits.size();
    }

    public class MyViewHolderVisit extends RecyclerView.ViewHolder{

        MaterialCardView cardViewVisit;
        ImageView imageVisit;
        TextView textTitleVisit, textVisitVisit, textPriceVisit;

        public MyViewHolderVisit(@NonNull View itemView) {
            super(itemView);

            cardViewVisit = itemView.findViewById(R.id.cardViewVisit);
            imageVisit = itemView.findViewById(R.id.imageVisit);
            textTitleVisit = itemView.findViewById(R.id.textTitleVisit);
            textVisitVisit = itemView.findViewById(R.id.textVisitVisit);
            textPriceVisit = itemView.findViewById(R.id.textPriceVisit);
        }
    }
}
