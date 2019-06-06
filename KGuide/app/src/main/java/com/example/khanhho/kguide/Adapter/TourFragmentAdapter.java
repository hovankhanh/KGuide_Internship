package com.example.khanhho.kguide.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TourFragmentAdapter extends BaseAdapter {
    private List<Tour> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public TourFragmentAdapter(Context context, List<Tour> listData) {
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
            convertView = layoutInflater.inflate(R.layout.item_tour_fragment, null);
            holder = new ViewHolder();
            holder.imgTour = (CircleImageView) convertView.findViewById(R.id.civ_image_tour);
            holder.tvNameTour = (TextView) convertView.findViewById(R.id.tv_tour_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Tour tour = this.listData.get(position);
        holder.tvNameTour.setText(tour.getName().toString());
        String getAvatarImage = tour.getImageTour().toString();
        Picasso.get().load(getAvatarImage).into(holder.imgTour);

        return convertView;
    }

    static class ViewHolder {
        CircleImageView imgTour;
        TextView tvNameTour;
    }
}
