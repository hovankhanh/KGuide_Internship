package com.example.khanhho.kguide.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khanhho.kguide.Activities.TourDetailActivity;
import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecycleTourAdapter extends RecyclerView.Adapter<RecycleTourAdapter.ImageViewHoder>{

    private List<Tour> tourList;
    private List<String> mKeyList;
    private List<String> mIdList;
    public RecycleTourAdapter(List<Tour> tourList,List<String> keyList, List<String> idList,  Context context){
        this.tourList = tourList;
        this.context = context;
        this.mKeyList = keyList;
        this.mIdList = idList;
    }
    Context context;



    @NonNull
    @Override
    public ImageViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tour,viewGroup,false) ;
        ImageViewHoder imageViewHoder = new ImageViewHoder(view)  ;
        return imageViewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHoder viewHolder,  int i) {
        final Tour tour = tourList.get(i);
        final int position = i;

        if (tour.getImageTour() != null){
            String getAvatarImage = tour.getImageTour().toString();
            Picasso.get().load(getAvatarImage).into(viewHolder.imgImage);
        }else {
            viewHolder.imgImage.setImageResource(R.drawable.vn);
        }

        viewHolder.tvTourName.setText(tour.getName());
        viewHolder.tvPrice.setText(tour.getPrice()+" VND");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tourDetail = new Intent(context, TourDetailActivity.class);
                tourDetail.putExtra("key",mKeyList.get(position));
                tourDetail.putExtra("id",mIdList.get(position));
                context.startActivity(tourDetail);
            }
        });
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
