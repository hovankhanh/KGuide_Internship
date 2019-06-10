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

import com.example.khanhho.kguide.Activities.GuideDetailActivity;
import com.example.khanhho.kguide.Adapter.GridGuideAdapter;
import com.example.khanhho.kguide.Model.Guide;
import com.example.khanhho.kguide.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GuideFragment extends Fragment {
    private View nRootView;
    private FirebaseAuth mAuth;
    private String currentUser;
    private Guide guide;
    private GridGuideAdapter adapter;
    private List<Guide> guideList;
    private List<String> keyList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nRootView = inflater.inflate(R.layout.fragment_guide, container, false);
        guideList = getListData();
        final GridView gridView = (GridView) nRootView.findViewById(R.id.gridView);
        adapter = new GridGuideAdapter(getContext(), guideList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
//                Object o = gridView.getItemAtPosition(position);
//                Guide country = (Guide) o;
//                Toast.makeText(getContext(), "Selected :"
//                        + " " + country, Toast.LENGTH_LONG).show();

                Intent mIntent = new Intent(getContext(), GuideDetailActivity.class);
                mIntent.putExtra("key",keyList.get(position));
                startActivity(mIntent);
            }
        });
        return nRootView;
    }

    private  List<Guide> getListData() {
        final List<Guide> list = new ArrayList<Guide>();
        keyList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Users/");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String status = messageSnapshot.child("status").getValue().toString();

                    if (status.equals("guide")) {
                        guide = messageSnapshot.getValue(Guide.class);
                        list.add(guide);
                        keyList.add(messageSnapshot.getKey());
                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }
}
