package com.example.khanhho.kguide.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.khanhho.kguide.Activities.GuideDetailActivity;
import com.example.khanhho.kguide.Activities.MainActivity;
import com.example.khanhho.kguide.Activities.TourDetailActivity;
import com.example.khanhho.kguide.Adapter.GridGuideAdapter;
import com.example.khanhho.kguide.Model.Guide;
import com.example.khanhho.kguide.R;

import java.util.ArrayList;
import java.util.List;

public class GuideFragment extends Fragment {
    private View nRootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nRootView = inflater.inflate(R.layout.fragment_guide, container, false);
        List<Guide> image_details = getListData();
        final GridView gridView = (GridView) nRootView.findViewById(R.id.gridView);
        gridView.setAdapter(new GridGuideAdapter(getContext(), image_details));

        // Khi người dùng click vào các GridItem
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = gridView.getItemAtPosition(position);
                Guide country = (Guide) o;
                Toast.makeText(getContext(), "Selected :"
                        + " " + country, Toast.LENGTH_LONG).show();
                Intent login = new Intent(getContext(), GuideDetailActivity.class);
                startActivity(login);
            }
        });
        return nRootView;
    }

    private  List<Guide> getListData() {
        List<Guide> list = new ArrayList<Guide>();
        Guide vietnam = new Guide("Vietnam", "vn", 1);
        Guide usa = new Guide("United States", "us", 3);
        Guide russia = new Guide("Russia", "rs", 3);
        Guide australia = new Guide("Australia", "vn", 5);
        Guide japan = new Guide("Japan", "rr", 3);

        list.add(vietnam);
        list.add(usa);
        list.add(russia);
        list.add(australia);
        list.add(japan);

        return list;
    }
}
