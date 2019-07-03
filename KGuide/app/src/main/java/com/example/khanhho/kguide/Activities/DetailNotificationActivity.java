package com.example.khanhho.kguide.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
    private TextView tvNameTourist, tvDate, tvNameTour, tvCurentTime, tvContent, tvNameGuide;
    private ImageView imgTour;
    private String idTourist, idTour, idGuide, key, idBooking;
    private Booking booking;
    private Tour tour;
    private String status;
    private Button btnConfirm, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notification);
        tvDate = findViewById(R.id.tv_start_date);
        tvNameTour = findViewById(R.id.tv_tour_name);
        tvNameTourist = findViewById(R.id.tv_Tourist_name);
        imgTour = findViewById(R.id.img_tour_image);
        tvCurentTime = findViewById(R.id.tv_curent_time);
        btnCancel = findViewById(R.id.btn_cancel);
        btnConfirm = findViewById(R.id.btn_confirm);
        tvContent = findViewById(R.id.tv_content);
        tvNameGuide = findViewById(R.id.tv_guide_name);


        final Intent intent = getIntent();
        idTour = intent.getStringExtra("idTour");
        idTourist = intent.getStringExtra("idTourist");
        idGuide = intent.getStringExtra("idGuide");
        key = intent.getStringExtra("key");
        idBooking = intent.getStringExtra("idBooking");

        if (key.equals("history guide")){
            btnConfirm.setVisibility(View.GONE);
            tvNameGuide.setVisibility(View.GONE);
            tvContent.setText(" booked your tour with:");
        }if (key.equals("history tourist")){
            tvNameTourist.setVisibility(View.GONE);
            btnConfirm.setVisibility(View.GONE);
        } else {
            tvNameGuide.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            tvContent.setText(" booked your tour with:");
        }

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
                intent.putExtra("key", idGuide);
                startActivity(intent);
            }
        });

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Booking").child(idTourist).child(idGuide).child(idTour).child(idBooking);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                booking = dataSnapshot.getValue(Booking.class);
                if (booking.getStatus().toString().equals("Cancel") || booking.getStatus().toString().equals("unaccepted") || booking.getStatus().toString().equals("Tourist Cancel")){
                    btnCancel.setVisibility(View.GONE);
                    btnConfirm.setVisibility(View.GONE);
                }
               tvNameTour.setText("and Tour's name is "+booking.getTourName().toString());
               tvDate.setText("Start date: "+booking.getStartDate().toString());
               tvNameTourist.setText(booking.getTouristName().toString());
               tvCurentTime.setText(booking.getCurrentTime().toString());
               tvNameGuide.setText(booking.getGuideName().toString());
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
    private void alertView(String message, final String key) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        String btr, btl;
        if (key.equals("confirm")){
           btr = "Accept";
           btl = "Unaccept";
           status = "Comming Tour";
        }else {
            btr = "Yes";
            btl = "No";
            if (key.equals("history guide")){
                status = "Guide Cancle";
            }else {
                status = "Tourist Cancel";
            }

        }

        dialog.setTitle("Confirm")
                .setMessage(message)
                .setPositiveButton(btr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Booking").child(idTourist).child(idGuide).child(idTour).child(idBooking).child("status").setValue(status);
                        finish();
                    }
                }).setNegativeButton(btl, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (key.equals("confirm")){
                    FirebaseDatabase.getInstance().getReference().child("Booking").child(idTourist).child(idGuide).child(idTour).child(idBooking).child("status").setValue("unaccepted");
                    dialog.dismiss();
                    finish();
                }else {
                    dialog.dismiss();
                }

            }
        }).show();
    }

    public void Confirm(View view) {
        alertView("Do you want to accept this order?", "confirm");
    }

    public void Cancel(View view) {
        alertView("Do you want to cancel this order?", "cancel");
    }
}
