package com.example.khanhho.kguide.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.khanhho.kguide.Adapter.HistoryTouristAdapter;
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

public class MyTourTouristActivity extends AppCompatActivity {
    private HistoryTouristAdapter adapter;
    private String currentUser;
    private FirebaseAuth mAuth;
    private Booking booking;
    private SharedPreferences sharedPreferences;
    private List idTourist, idGuide, idTour, idBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tour_tourist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        List<Booking> data = getListData();
        final ListView listView = (ListView)findViewById(R.id.lv_history_tourist);
        adapter = new HistoryTouristAdapter(this,data);
        listView.setAdapter(adapter);



        // Khi người dùng click vào các ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                if (sharedPreferences.getString("user", "").equals("guide")) {
                    Intent intent = new Intent(MyTourTouristActivity.this, DetailNotificationActivity.class);
                    intent.putExtra("idTourist", String.valueOf(idTourist.get(position)));
                    intent.putExtra("key", "history guide");
                    intent.putExtra("idGuide", String.valueOf(idGuide.get(position)));
                    intent.putExtra("idTour", String.valueOf(idTour.get(position)));
                    intent.putExtra("idBooking", String.valueOf(idBooking.get(position)));
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(MyTourTouristActivity.this, DetailNotificationActivity.class);
                    intent.putExtra("idTourist", currentUser);
                    intent.putExtra("key", "history tourist");
                    intent.putExtra("idGuide", String.valueOf(idGuide.get(position)));
                    intent.putExtra("idTour", String.valueOf(idTour.get(position)));
                    intent.putExtra("idBooking", String.valueOf(idBooking.get(position)));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();
        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private  List<Booking> getListData() {
        final List<Booking> list = new ArrayList<Booking>();
        idTourist = new ArrayList<>();
        idGuide = new ArrayList<>();
        idTour = new ArrayList<>();
        idBooking = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if (sharedPreferences.getString("user", "").equals("guide")) {
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            DatabaseReference myRef = database.child("Booking");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot abc : messageSnapshot.getChildren()) {
                            if (abc.getKey().equals(currentUser)){
                                for (DataSnapshot ab : abc.getChildren()) {
                                    for (DataSnapshot book : ab.getChildren()) {
                                        booking = book.getValue(Booking.class);
                                        if (booking.getStatus().toString().equals("Done") || booking.getStatus().toString().equals("Comming Tour")) {
                                            list.add(booking);
                                            idGuide.add(abc.getKey());
                                            idTour.add(ab.getKey());
                                            idTourist.add(messageSnapshot.getKey());
                                            idBooking.add(book.getKey());
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
        }else {
            Log.d("Comming tour ", "1004");
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            DatabaseReference myRef = database.child("Booking").child(currentUser);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot tour : messageSnapshot.getChildren()) {
                            for (DataSnapshot book : tour.getChildren()) {
                                booking = book.getValue(Booking.class);
                                idGuide.add(messageSnapshot.getKey());
                                idTour.add(tour.getKey());
                                idBooking.add(book.getKey());
                                list.add(booking);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        return list;
    }


}
