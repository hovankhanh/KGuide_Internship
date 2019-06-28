package com.example.khanhho.kguide.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khanhho.kguide.Model.Booking;
import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailNotificationActivity extends AppCompatActivity {
    TextView tvNameTourist, tvDate, tvNameTour, tvCurentTime;
    ImageView imgTour;
    String idTourist, idTour, idGuide;
    private Booking booking;
    private Tour tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notification);
        tvDate = findViewById(R.id.tv_start_date);
        tvNameTour = findViewById(R.id.tv_tour_name);
        tvNameTourist = findViewById(R.id.tv_Tourist_name);
        imgTour = findViewById(R.id.img_tour_image);
        tvCurentTime = findViewById(R.id.tv_curent_time);


        final Intent intent = getIntent();
        idTour = intent.getStringExtra("idTour");
        idTourist = intent.getStringExtra("idTourist");
        idGuide = intent.getStringExtra("idGuide");
        tvNameTourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailNotificationActivity.this, TouristProfileActivity.class);
                intent.putExtra("key", idTourist);
                startActivity(intent);
            }
        });
        imgTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailNotificationActivity.this, TourDetailActivity.class);
                intent.putExtra("id", idTour);
                startActivity(intent);
            }
        });

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Booking").child(idTourist).child(idGuide).child(idTour);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                booking = dataSnapshot.getValue(Booking.class);
               tvNameTour.setText("and Tour's name is "+booking.getTourName().toString());
               tvDate.setText("Start date: "+booking.getStartDate().toString());
               tvNameTourist.setText(booking.getTouristName().toString());
               tvCurentTime.setText(booking.getCurrentTime().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference myRef2 = database.child("tour").child(idGuide).child(idTour);
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tour = dataSnapshot.getValue(Tour.class);
                String getAvatarImage = tour.getImageTour().toString();
                Picasso.get().load(getAvatarImage).into(imgTour);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void alertView(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirm")
                .setMessage(message)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    Log.d("kiemtra","ahihi");
                    FirebaseDatabase.getInstance().getReference().child("Booking").child(idTourist).child(idGuide).child(idTour).child("status").setValue("Comming Tour");
                    finish();
                    }
                }).setNegativeButton("Unaccept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase.getInstance().getReference().child("Booking").child(idTourist).child(idGuide).child(idTour).child("status").setValue("unaccepted");
                dialog.dismiss();
                finish();
            }
        }).show();
    }

    public void Confirm(View view) {
        alertView("Do you want to accept this order?");
    }
}
