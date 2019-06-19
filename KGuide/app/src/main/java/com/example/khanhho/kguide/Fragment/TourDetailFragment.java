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
import android.widget.ListView;

import com.example.khanhho.kguide.Activities.GuideDetailActivity;
import com.example.khanhho.kguide.Activities.TourDetailActivity;
import com.example.khanhho.kguide.Adapter.TourFragmentAdapter;
import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.R;
import com.google.firebase.auth.FirebaseAuth;
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
    private String key;
    private String currentUser;
    private FirebaseAuth mAuth;
    private List<String> idList = new ArrayList<>();




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nRootView = inflater.inflate(R.layout.fragment_tour_guide, container, false);
        List<Tour> data = getListData();
        ListView listView = (ListView) nRootView.findViewById(R.id.lv_tour);
        adapter = new TourFragmentAdapter(getContext(),data);
        listView.setAdapter(adapter);

        // Khi người dùng click vào các ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent mIntent = new Intent(getContext(), TourDetailActivity.class);
                mIntent.putExtra("id",idList.get(position));
                mIntent.putExtra("key",key);
                startActivity(mIntent);
            }
        });

        return nRootView;
    }

    private  List<Tour> getListData() {
        final List<Tour> list = new ArrayList<Tour>();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        if (!GuideDetailActivity.currentUser.equals(currentUser)){
            key = GuideDetailActivity.currentUser;
        }else {
            key = currentUser;
        }
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("tour").child(key);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    tour = messageSnapshot.getValue(Tour.class);
                    list.add(tour);
                    idList.add(messageSnapshot.getKey());
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
