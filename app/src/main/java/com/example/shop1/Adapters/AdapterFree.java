package com.example.shop1.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop1.Activity_Wait;
import com.example.shop1.Class.Put;
import com.example.shop1.Models.ModelFree;
import com.example.shop1.R;
import com.keyboard3.LableView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterFree extends RecyclerView.Adapter<AdapterFree.MyViewHolderFree> {

    Context context;
    List<ModelFree> modelFrees;

    public AdapterFree(Context context, List<ModelFree> modelFrees) {
        this.context = context;
        this.modelFrees = modelFrees;
    }

    @NonNull
    @Override
    public MyViewHolderFree onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_free, parent, false);
        return new MyViewHolderFree(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderFree holder, int position) {
        ModelFree modelFree = modelFrees.get(position);

        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(modelFree.getFree()));
        holder.textPriceFree.setText(price + " " + "$");

        holder.textVisitFree.setText(modelFree.getVisit());
        holder.textTitle.setText(modelFree.getTitle());
        holder.lableView.setText(modelFree.getLabel() + "%");

        Picasso.get()
                .load(modelFree.getImage())
                .into(holder.imageFree);

        holder.cardViewFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdapterFree.this.context, Activity_Wait.class);
                intent.putExtra(Put.id, modelFree.getId() + "");

                intent.putExtra(Put.label, modelFree.getLabel());
                intent.putExtra(Put.freeprice, modelFree.getFree());

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelFrees.size();
    }

    public class MyViewHolderFree extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        LableView lableView;
        CardView cardViewFree;
        ImageView imageFree;
        TextView textTitle, textVisitFree, textPriceFree;

        public MyViewHolderFree(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linearLayout);
            lableView = itemView.findViewById(R.id.labelView);
            cardViewFree = itemView.findViewById(R.id.cardViewFree);
            imageFree = itemView.findViewById(R.id.imageFree);
            textTitle = itemView.findViewById(R.id.textTitle);
            textVisitFree = itemView.findViewById(R.id.textVisitFree);
            textPriceFree = itemView.findViewById(R.id.textPriceFree);
        }
    }
}
