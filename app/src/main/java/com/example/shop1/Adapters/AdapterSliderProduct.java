package com.example.shop1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shop1.Models.ModelSliderProduct;
import com.example.shop1.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class AdapterSliderProduct extends SliderViewAdapter<AdapterSliderProduct.MyViewHolderSliderProduct> {

    Context context;
    List<ModelSliderProduct> modelSliderProducts;

    public AdapterSliderProduct(Context context, List<ModelSliderProduct> modelSliderProducts) {
        this.context = context;
        this.modelSliderProducts = modelSliderProducts;
    }

    @Override
    public MyViewHolderSliderProduct onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_product_layout, parent, false);
        return new MyViewHolderSliderProduct(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolderSliderProduct viewHolder, int position) {
        ModelSliderProduct modelSliderProduct = modelSliderProducts.get(position);

        viewHolder.imageSliderProduct.setImageResource(Integer.valueOf(modelSliderProduct.getImageViewSlider()));
    }

    @Override
    public int getCount() {
        return modelSliderProducts.size();
    }

    public class MyViewHolderSliderProduct extends SliderViewAdapter.ViewHolder{

        ImageView imageSliderProduct;

        public MyViewHolderSliderProduct(View itemView) {
            super(itemView);

            imageSliderProduct = itemView.findViewById(R.id.imageSliderProduct);
        }
    }
}
