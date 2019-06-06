package com.example.khanhho.kguide.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.khanhho.kguide.Adapter.TourFragmentAdapter;
import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TourDetailFragment extends Fragment {
    private View nRootView;
    private TourFragmentAdapter adapter;
    private Tour tour;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nRootView = inflater.inflate(R.layout.fragment_tour_guide, container, false);

        List<Tour> data = getListData();
        final ListView listView = (ListView) nRootView.findViewById(R.id.lv_tour);
        adapter = new TourFragmentAdapter(getContext(),data);
        listView.setAdapter(adapter);

        // Khi người dùng click vào các ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Tour tour = (Tour) o;
                Toast.makeText(getContext(), "Selected :" + " " + tour, Toast.LENGTH_LONG).show();
            }
        });
        return nRootView;
    }

    private  List<Tour> getListData() {
        final List<Tour> list = new ArrayList<Tour>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("tour").child("lxlP0qCtVJbnpuYFFr5WoVdC6lB2");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    tour = messageSnapshot.getValue(Tour.class);
                    list.add(tour);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

        return list;
    }

}
