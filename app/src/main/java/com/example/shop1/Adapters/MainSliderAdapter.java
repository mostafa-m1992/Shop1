package com.example.shop1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shop1.Models.MainSliderModel;
import com.example.shop1.R;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainSliderAdapter extends SliderViewAdapter<MainSliderAdapter.MainViewHolderSlider> {

    Context context;
    List<MainSliderModel> mainSliderModels;

    public MainSliderAdapter(Context context, List<MainSliderModel> mainSliderModels) {
        this.context = context;
        this.mainSliderModels = mainSliderModels;
    }

    @Override
    public MainViewHolderSlider onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_slider_layout, parent, false);
        return new MainViewHolderSlider(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolderSlider viewHolder, int position) {
        MainSliderModel mainSliderModel = mainSliderModels.get(position);

        //viewHolder.mainSliderImage.setImageResource(mainSliderModel.getImage());

        Picasso.get().load(mainSliderModel.getImage()).into(viewHolder.mainSliderImage);
    }

    @Override
    public int getCount() {
        return mainSliderModels.size();
    }

    public class MainViewHolderSlider extends SliderViewAdapter.ViewHolder {

        ImageView mainSliderImage;

        public MainViewHolderSlider(View itemView) {
            super(itemView);

            mainSliderImage = itemView.findViewById(R.id.mainSliderImage);
        }
    }
}
