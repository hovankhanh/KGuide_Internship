package com.example.khanhho.kguide.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.Model.Tourist;
import com.example.khanhho.kguide.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GuideFragment extends Fragment {
    private View nRootView;
    private FirebaseAuth mAuth;
    private String currentUser;
    private Guide guide;
    private List<Guide> guideList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nRootView = inflater.inflate(R.layout.fragment_guide, container, false);
//        saveTourData();
        final GridView gridView = (GridView) nRootView.findViewById(R.id.gridView);
        gridView.setAdapter(new GridGuideAdapter(getContext(), guideList));

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

    private void saveTourData() {
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
                        Log.d("quang khanh ", status);
                        guide = messageSnapshot.getValue(Guide.class);
                        Log.d(" khanh ", guide.getName().toString());
                        guideList.add(guide);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
