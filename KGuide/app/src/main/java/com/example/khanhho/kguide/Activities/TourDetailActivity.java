package com.example.khanhho.kguide.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khanhho.kguide.Model.Booking;
import com.example.khanhho.kguide.Model.Guide;
import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.Model.Tourist;
import com.example.khanhho.kguide.R;
import com.example.khanhho.kguide.Ultil.App;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TourDetailActivity extends AppCompatActivity {
    private Tour tour;
    private ImageView imgImageTour;
    private TextView tvNameGuide, tvPrice, tvDescription, tvTopic, tvService, tvCity, tvPriceBook, tvAvailable, tvNameTour, tvTitleGuide;
    private String key, idTour, currentUser;
    private LinearLayout lnBook;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private Guide guide;
    private int mYear, mMonth, mDay;
    private Tourist tourist;
    private Booking booking;
    private DatabaseReference reference;
    private NotificationManagerCompat notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);

        notificationManager = NotificationManagerCompat.from(this);

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
        tvTitleGuide = (TextView) findViewById(R.id.tv_title_guide);

//        setSupportActionBar(toolbar);
//        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
//        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        if (sharedPreferences.getString("user", "").equals("guide")) {
            key = mAuth.getCurrentUser().getUid();
            lnBook.setVisibility(View.GONE);
            tvNameGuide.setVisibility(View.GONE);
            tvTitleGuide.setVisibility(View.GONE);
            Intent intent = getIntent();
            idTour = intent.getStringExtra("id");
        } else {
            lnBook.setVisibility(View.VISIBLE);
            Intent intent = getIntent();
            key = intent.getStringExtra("key");
            idTour = intent.getStringExtra("id");
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            DatabaseReference myRef = database.child("Users").child(key);
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    guide = dataSnapshot.getValue(Guide.class);
                    tvNameGuide.setText(guide.getSurname()+" "+guide.getName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
//        hide button
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef1 = database.child("Booking");
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    if (messageSnapshot.getKey().equals(mAuth.getCurrentUser().getUid())){
                    for (DataSnapshot abc : messageSnapshot.getChildren()) {
                        if (abc.getKey().equals(key))
                            for (DataSnapshot ab : abc.getChildren()) {
                                if (ab.getKey().equals(idTour)){
                                    for (DataSnapshot book : ab.getChildren()) {
                                        booking = book.getValue(Booking.class);
                                        if (booking.getStatus().toString().equals("unaccepted") || booking.getStatus().toString().equals("Tourist Cancel")
                                                || booking.getStatus().toString().equals("Cancle")) {
                                            lnBook.setVisibility(View.VISIBLE);
                                            Log.d("khanh", "ok0");
                                        }else {
                                            lnBook.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        getTourData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getTourData() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("tour").child(key).child(idTour);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tour = dataSnapshot.getValue(Tour.class);
                tvPrice.setText(tour.getPrice() + " $ ");
                tvPriceBook.setText(tour.getPrice() + " $ ");
                tvDescription.setText(tour.getDescription().toString());
                tvTopic.setText(tour.getTopic().toString());
                tvService.setText(tour.getService().toString());
                tvNameTour.setText(tour.getName().toString());
                tvCity.setText(tour.getCity().toString());
                tvAvailable.setText("Available on every " + tour.getTime() + " and suitable for " + tour.getAge());
                String getAvatarImage = tour.getImageTour().toString();
                Picasso.get().load(getAvatarImage).into(imgImageTour);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void Book(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_WEEK);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String currentTime = DateFormat.getDateTimeInstance().format(new Date());;
                        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference myRefTour = database.child("tour").child(key).child(idTour);
                        myRefTour.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                tour = dataSnapshot.getValue(Tour.class);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }

                        });
                        DatabaseReference myRefGuide = database.child("Users").child(key);
                        myRefGuide.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                guide = dataSnapshot.getValue(Guide.class);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                        mAuth = FirebaseAuth.getInstance();
                        currentUser = mAuth.getCurrentUser().getUid();
                        DatabaseReference myRefTourist = database.child("Users").child(currentUser);
                        myRefTourist.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                tourist = dataSnapshot.getValue(Tourist.class);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                        String guideName = guide.getName()+" "+guide.getSurname();
                        alertView("Tour Name: "+ tour.getName().toString()+"\n Guide Name: "+guideName+"\n Date start: "+date,currentUser,currentTime,date, guideName,tour.getName());

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void alertView(String message, final String currentUser, final String currentTime, final String date, final String guideName, final String tourName) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("You are booking")
                .setMessage(message)
                .setPositiveButton("Book", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        reference = FirebaseDatabase.getInstance().getReference("Booking").child(currentUser).child(key).child(idTour).child(currentTime);
                        Map map = new HashMap();
                        map.put("currentTime",currentTime);
                        map.put("status","Waiting cofirm");
                        map.put("startDate",date);
                        map.put("guideName", guideName);
                        map.put("price", tour.getPrice());
                        map.put("tourName", tour.getName().toString());
                        map.put("touristName", tourist.getName() +" "+tourist.getSurname());
                        map.put("touristAvatar", tourist.getImage());
                        reference.updateChildren(map);
                    Toast.makeText(TourDetailActivity.this, "You have booked succesfuly",
                                Toast.LENGTH_SHORT).show();
                        sendOnChannel1();
                    finish();
                    startActivity(getIntent());
                    }
                }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public void sendOnChannel1(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.icon_edit)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

            notificationManager.notify(1, builder.build());
    }
}
