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
import android.widget.ListView;

import com.example.khanhho.kguide.Activities.DetailNotificationActivity;
import com.example.khanhho.kguide.Adapter.NotifictionAdapter;
import com.example.khanhho.kguide.Model.Booking;
import com.example.khanhho.kguide.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private View nRootView;
    private NotifictionAdapter adapter;
    private String currentUser;
    private FirebaseAuth mAuth;
    private Booking booking;
    private List<String> idTourist, idGuide, idTour, idBooking;
    List<Booking> data = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nRootView = inflater.inflate(R.layout.fragment_home, container, false);

        data = getListData();
        Log.d("kiemtra","ahihi3");
        final ListView listView = (ListView)nRootView.findViewById(R.id.lv_notification_guide);
        adapter = new NotifictionAdapter(getContext(),data);
        listView.setAdapter(adapter);

        // Khi người dùng click vào các ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            Intent intent = new Intent(getContext(), DetailNotificationActivity.class);
            intent.putExtra("key","notification");
            intent.putExtra("idTourist",idTourist.get(position));
            intent.putExtra("idGuide", idGuide.get(position));
            intent.putExtra("idTour", idTour.get(position));
            intent.putExtra("idBooking", idBooking.get(position));
            startActivity(intent);
            }
        });
        return nRootView;
    }

    private List<Booking> getListData() {
        final List<Booking> list = new ArrayList<Booking>();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        idTourist = new ArrayList<>();
        idGuide = new ArrayList<>();
        idTour = new ArrayList<>();
        idBooking = new ArrayList<>();
        Log.d("kiemtra","ahihi1");

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Booking");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                Log.d("kiemtra","ahihi5");
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot abc : messageSnapshot.getChildren()) {
                        for (DataSnapshot ab : abc.getChildren()) {
                            String test = abc.getKey();
                            if (test.equals(currentUser)){
                                for (DataSnapshot book : ab.getChildren()) {
                                    booking = book.getValue(Booking.class);
                                    if (booking.getStatus().toString().equals("Waiting cofirm")) {
                                        idTourist.add(messageSnapshot.getKey());
                                        idGuide.add(abc.getKey());
                                        idTour.add(ab.getKey());
                                        idBooking.add(book.getKey());
                                        list.add(booking);
                                        Log.d("kiemtra", "ahihi2");
                                    }
                                }
                            }
                        }
                    }
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
