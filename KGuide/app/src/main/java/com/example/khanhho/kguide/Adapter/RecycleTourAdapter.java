package com.example.khanhho.kguide.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.R;

import java.util.List;

public class RecycleTourAdapter extends RecyclerView.Adapter<RecycleTourAdapter.ImageViewHoder>{

    private List<Tour> tourList;
    public RecycleTourAdapter(List<Tour> tourList){
        this.tourList = tourList;
    }


    @NonNull
    @Override
    public ImageViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tour,viewGroup,false) ;
        ImageViewHoder imageViewHoder = new ImageViewHoder(view)  ;
        return imageViewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHoder viewHolder, int i) {
        Tour tour = tourList.get(i);
        viewHolder.imgImage.setImageResource(tour.getImageTour());
        viewHolder.tvTourName.setText(tour.getNameTour());
        viewHolder.tvPrice.setText(tour.getPriceTour()+" VND");


    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }


    public static class ImageViewHoder extends RecyclerView.ViewHolder{

        ImageView imgImage;
        TextView tvTourName;
        TextView tvPrice;


        public ImageViewHoder(@NonNull View itemView) {
            super(itemView);
            imgImage = itemView.findViewById(R.id.img_image);
            tvTourName = itemView.findViewById(R.id.tv_name_tour);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}
