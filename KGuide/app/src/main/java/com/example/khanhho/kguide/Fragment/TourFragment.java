package com.example.khanhho.kguide.Fragment;

import android.content.Intent;
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

import com.example.khanhho.kguide.Activities.TourDetailActivity;
import com.example.khanhho.kguide.Adapter.RecycleTourAdapter;
import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.R;

import java.util.ArrayList;
import java.util.List;

public class TourFragment extends Fragment {
    private View nRootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecycleTourAdapter adapter;
    private List<Tour> tourList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nRootView = inflater.inflate(R.layout.fragment_tour, container, false);

        mRecyclerView = nRootView.findViewById(R.id.rcv_tour);
        adapter = new RecycleTourAdapter(tourList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        saveTourData();
        return nRootView;

    }

    private void saveTourData() {
        Tour tour = new Tour(R.drawable.rs, "Action & Adventure", 12);
        tourList.add(tour);

        tour = new Tour(R.drawable.us, "Action & Adventure", 10);
        tourList.add(tour);

        tour = new Tour(R.drawable.vn, "Action & Adventure", 17);
        tourList.add(tour);

        adapter.notifyDataSetChanged();
    }


}
