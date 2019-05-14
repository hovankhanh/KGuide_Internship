package com.example.khanhho.kguide.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khanhho.kguide.Adapter.RecycleTourAdapter;
import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.R;
import com.example.khanhho.kguide.Ultil.CheckConnection;

import java.util.ArrayList;
import java.util.List;

public class TourDetailFragment extends Fragment {
    private View nRootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecycleTourAdapter adapter;
    private List<Tour> tourList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nRootView = inflater.inflate(R.layout.fragment_tour_guide, container, false);

        return nRootView;
    }
//    private void saveTourData() {
//        Tour tour = new Tour(R.drawable.rs, "Action & Adventure", 12);
//        tourList.add(tour);
//
//        tour = new Tour(R.drawable.us, "Action & Adventure", 10);
//        tourList.add(tour);
//
//        tour = new Tour(R.drawable.vn, "Action & Adventure", 17);
//        tourList.add(tour);
//
//        adapter.notifyDataSetChanged();
//    }
}
