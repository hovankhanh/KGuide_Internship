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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nRootView = inflater.inflate(R.layout.fragment_home, container, false);
        List<Booking> data = getListData();
        final ListView listView = (ListView)nRootView.findViewById(R.id.lv_notification_guide);
        adapter = new NotifictionAdapter(getContext(),data);
        listView.setAdapter(adapter);

        // Khi người dùng click vào các ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

            }
        });
        return nRootView;
    }

    private List<Booking> getListData() {
        final List<Booking> list = new ArrayList<Booking>();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Booking");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot abc : messageSnapshot.getChildren()) {
                        for (DataSnapshot ab : abc.getChildren()) {
                            String test = abc.getKey();
                            if (test.equals(currentUser)){
                                booking = ab.getValue(Booking.class);
                                list.add(booking);
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
