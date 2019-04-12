package com.example.khanhho.kguide.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.khanhho.kguide.Model.Guide;
import com.example.khanhho.kguide.R;

import java.util.List;

public class GridGuideAdapter extends BaseAdapter {

    private List<Guide> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public GridGuideAdapter(Context aContext, List<Guide> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
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

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_guide, null);
            holder = new ViewHolder();
            holder.avarta = (ImageView) convertView.findViewById(R.id.img_avarta);
            holder.nameGuide = (TextView) convertView.findViewById(R.id.tv_name_guide);
            holder.star = (RatingBar) convertView.findViewById(R.id.rtb_star);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Guide guide = this.listData.get(position);
        holder.nameGuide.setText(guide.getNameGuide());

        int imageId = this.getMipmapResIdByName("@drawable/"+guide.getAvarta());

        holder.avarta.setImageResource(imageId);
        holder.star.setRating(guide.getStar());

        return convertView;
    }

    // Tìm ID của Image ứng với tên của ảnh (Trong thư mục mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();

        // Trả về 0 nếu không tìm thấy.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomGridView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    static class ViewHolder {
        ImageView avarta;
        TextView nameGuide;
        RatingBar star;
    }

}
