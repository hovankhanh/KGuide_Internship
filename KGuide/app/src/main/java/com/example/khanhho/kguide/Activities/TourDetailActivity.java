package com.example.khanhho.kguide.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TourDetailActivity extends AppCompatActivity {
    private Tour tour;
    private ImageView imgImageTour;
    private TextView tvNameGuide, tvPrice, tvDescription, tvTopic, tvService, tvCity, tvPriceBook, tvAvailable, tvNameTour;
    private String key, idTour;
    private LinearLayout lnBook;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);


        tvAvailable = (TextView) findViewById(R.id.tv_available);
        tvPriceBook = (TextView) findViewById(R.id.tv_price_book);
        tvNameGuide = (TextView) findViewById(R.id.tv_name_guide);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvDescription = (TextView) findViewById(R.id.tv_description);
        tvTopic = (TextView) findViewById(R.id.tv_topic);
        tvService = (TextView) findViewById(R.id.tv_service);
        tvCity = (TextView) findViewById(R.id.tv_city);
        imgImageTour = (ImageView) findViewById(R.id.img_tour_detail);
        tvNameTour = (TextView) findViewById(R.id.tv_name_tour);
        lnBook = (LinearLayout) findViewById(R.id.ln_book);

//        setSupportActionBar(toolbar);
//        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
//        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        if (sharedPreferences.getString("user", "").equals("guide")) {
            mAuth = FirebaseAuth.getInstance();
            key = mAuth.getCurrentUser().getUid();
            lnBook.setVisibility(View.GONE);
            tvNameGuide.setVisibility(View.GONE);
            idTour = "1";
        } else {
            Intent intent = getIntent();
            key = intent.getStringExtra("key");
            idTour = intent.getStringExtra("id");
        }

        saveTourData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveTourData() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        Log.d("abc", key + " " + idTour);
        DatabaseReference myRef = database.child("tour").child(key).child(idTour);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tour = dataSnapshot.getValue(Tour.class);
                Log.d("abc", tour.getCity().toString());
                tvPrice.setText(tour.getPrice() + " VND");
                tvPriceBook.setText(tour.getPrice() + " VND");
//                tvDescription.setText(tour.getDescription().toString());
//                tvTopic.setText(tour.getTopic().toString());
//                tvService.setText(tour.getService().toString());
//                tvNameTour.setText(tour.getName().toString());
//                tvAvailable.setText("Available on every " + tour.getTime() + " and suitable for " + tour.getAge());
//                String getAvatarImage = tour.getImageTour().toString();
//                Picasso.get().load(getAvatarImage).into(imgImageTour);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }
}
