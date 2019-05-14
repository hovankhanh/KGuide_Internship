package com.example.khanhho.kguide.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khanhho.kguide.Activities.TourDetailActivity;
import com.example.khanhho.kguide.Adapter.RecycleTourAdapter;
import com.example.khanhho.kguide.Model.DraffTour;
import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.R;
import com.example.khanhho.kguide.Ultil.CheckConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TourFragment extends Fragment {
    private View nRootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecycleTourAdapter adapter;
    Tour tour;
    private List<Tour> tourList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nRootView = inflater.inflate(R.layout.fragment_tour, container, false);


        mRecyclerView = nRootView.findViewById(R.id.rcv_tour);
        adapter = new RecycleTourAdapter(tourList,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        if (CheckConnection.haveNetworkConnection(getContext())){
            saveTourData();
        }else {
            CheckConnection.ShowToast_Short(getContext(),"");
        }

        return nRootView;

    }

    private void saveTourData() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("tour/");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {

                    for (DataSnapshot abc : messageSnapshot.getChildren()) {
                        tour = abc.getValue(Tour.class);
                        tourList.add(tour);
                        Log.d("abc", tour.getCity());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }


}
