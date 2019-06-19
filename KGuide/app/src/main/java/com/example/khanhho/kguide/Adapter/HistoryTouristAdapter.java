package com.example.khanhho.kguide.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.khanhho.kguide.Model.Booking;
import com.example.khanhho.kguide.R;

import java.util.List;

public class HistoryTouristAdapter extends BaseAdapter {
    private List<Booking> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public HistoryTouristAdapter(Context context, List<Booking> listData) {
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
            convertView = layoutInflater.inflate(R.layout.item_history_tourist, null);
            holder = new ViewHolder();

            holder.tvNameTour = (TextView) convertView.findViewById(R.id.tv_tour_name);
            holder.tvStartDate = (TextView) convertView.findViewById(R.id.tv_start_date);
            holder.tvguide = (TextView) convertView.findViewById(R.id.tv_guide_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tvCurentTime = (TextView) convertView.findViewById(R.id.tv_current_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Booking booking = this.listData.get(position);
        holder.tvNameTour.setText("Tour's Name: "+booking.getTourName().toString());
        holder.tvguide.setText("Guide's Name: "+booking.getGuideName().toString());
        holder.tvCurentTime.setText(booking.getCurrentTime().toString());
        holder.tvPrice.setText(booking.getPrice()+" VND ");
        holder.tvStartDate.setText("Date Start: "+booking.getStartDate().toString());
        holder.tvStatus.setText(booking.getStatus().toString());

        return convertView;
    }

    static class ViewHolder {
        TextView tvNameTour, tvguide, tvPrice, tvStatus, tvCurentTime, tvStartDate;
    }
}
