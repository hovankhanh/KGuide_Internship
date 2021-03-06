package com.example.khanhho.kguide.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.khanhho.kguide.Model.Booking;
import com.example.khanhho.kguide.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotifictionAdapter extends BaseAdapter {
    private List<Booking> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public NotifictionAdapter(Context context, List<Booking> listData) {
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
            convertView = layoutInflater.inflate(R.layout.item_notification_guide, null);
            holder = new ViewHolder();

            holder.tvNameTourist = (TextView) convertView.findViewById(R.id.tv_Tourist_name);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            holder.tvCurentTime = (TextView) convertView.findViewById(R.id.tv_current_time);
            holder.civAvatar = convertView.findViewById(R.id.civ_avatar_notification);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Booking booking = this.listData.get(position);

        holder.tvCurentTime.setText(booking.getCurrentTime().toString());
        holder.tvContent.setText("Your "+booking.getTourName()+ " tour was booked. Please click here to confirm it");
        holder.tvNameTourist.setText(booking.getTouristName().toString());
        String getAvatarImage = booking.getTouristAvatar().toString();
        Picasso.get().load(getAvatarImage).into(holder.civAvatar);


        return convertView;
    }
    static class ViewHolder {
        TextView tvNameTourist, tvCurentTime, tvContent;
        CircleImageView civAvatar;
    }

}
