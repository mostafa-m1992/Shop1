package com.example.shop1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop1.Models.ModelComment;
import com.example.shop1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.MyViewHolderComment> {

    Context context;
    List<ModelComment> modelComments;

    public AdapterComment(Context context, List<ModelComment> modelComments) {
        this.context = context;
        this.modelComments = modelComments;
    }

    @NonNull
    @Override
    public MyViewHolderComment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment,parent,false);
        return new MyViewHolderComment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderComment holder, int position) {
        ModelComment modelComment = modelComments.get(position);

        holder.textuser.setText(modelComment.getUser());
        holder.textpo.setText(modelComment.getPositive());
        holder.textne.setText(modelComment.getNegative());
        holder.textcomment.setText(modelComment.getComment());
        Picasso
                .get()
                .load(modelComment.getImage())
                .into(holder.circleImageView);
        holder.ratingBar.setRating(modelComment.getRating());
    }

    @Override
    public int getItemCount() {
        return modelComments.size();
    }

    public class MyViewHolderComment extends RecyclerView.ViewHolder{

        TextView textcomment,textuser,textpo,textne;
        CircleImageView circleImageView;
        RatingBar ratingBar;

        public MyViewHolderComment(@NonNull View itemView) {
            super(itemView);

            textcomment = itemView.findViewById(R.id.textCommnet);
            textne = itemView.findViewById(R.id.textNegativeComment);
            textpo = itemView.findViewById(R.id.textPosotiveComment);
            textuser = itemView.findViewById(R.id.textUserComment);
            ratingBar = itemView.findViewById(R.id.ratingbarcomment);
            circleImageView = itemView.findViewById(R.id.imageUserComment);
        }
    }
}
