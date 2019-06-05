package com.example.khanhho.kguide.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MytourAdapter extends BaseAdapter {
    private List<Tour> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public MytourAdapter(Context context, List<Tour> listData) {
        this.context = context;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_my_tour, null);
            holder = new ViewHolder();
            holder.imgMyTour = (ImageView) convertView.findViewById(R.id.img_image_mytour);
            holder.tvNameMyTour = (TextView) convertView.findViewById(R.id.tv_name_mytour);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Tour tour = this.listData.get(position);
        holder.tvNameMyTour.setText(tour.getName());
        String getAvatarImage = tour.getImageTour().toString();
        Picasso.get().load(getAvatarImage).into(holder.imgMyTour);



        return convertView;
    }

    static class ViewHolder {
        ImageView imgMyTour;
        TextView tvNameMyTour;
    }
}
