package com.example.shop1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop1.Models.ModelCategory;
import com.example.shop1.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.MyViewHolderCategory> {

    Context context;
    List<ModelCategory> modelCategories;

    public AdapterCategory(Context context, List<ModelCategory> modelCategories) {
        this.context = context;
        this.modelCategories = modelCategories;
    }

    @NonNull
    @Override
    public MyViewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category, parent, false);
        return new MyViewHolderCategory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCategory holder, int position) {
        ModelCategory modelCategory = modelCategories.get(position);

        holder.textTitleCategory.setText(modelCategory.getTitleCategory());
        Picasso.get().load(modelCategory.getImage()).into(holder.imageCategory);

        holder.cardViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, modelCategory.getId() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelCategories.size();
    }

    public class MyViewHolderCategory extends RecyclerView.ViewHolder{

        MaterialCardView cardViewCategory;
        ImageView imageCategory;
        TextView textTitleCategory;

        public MyViewHolderCategory(@NonNull View itemView) {
            super(itemView);

            cardViewCategory = itemView.findViewById(R.id.cardViewCategory);
            imageCategory = itemView.findViewById(R.id.imageCategory);
            textTitleCategory = itemView.findViewById(R.id.textTitleCategory);
        }
    }
}
