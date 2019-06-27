package com.example.khanhho.kguide.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

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
        tvTitleGuide = (TextView) findViewById(R.id.tv_title_guide);

//        setSupportActionBar(toolbar);
//        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
//        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        if (sharedPreferences.getString("user", "").equals("guide")) {
            mAuth = FirebaseAuth.getInstance();
            key = mAuth.getCurrentUser().getUid();
            lnBook.setVisibility(View.GONE);
            tvNameGuide.setVisibility(View.GONE);
            tvTitleGuide.setVisibility(View.GONE);
            Intent intent = getIntent();
            idTour = intent.getStringExtra("id");
        } else {
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
                Log.d("abc", tour.getCity().toString());
                tvPrice.setText(tour.getPrice() + " VND");
                tvPriceBook.setText(tour.getPrice() + " VND");
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

//                        edDayOfBirth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

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
                    FirebaseDatabase.getInstance().getReference().child("Booking").child(currentUser).child(key).child(idTour).child("currentTime").setValue(currentTime);
                    FirebaseDatabase.getInstance().getReference().child("Booking").child(currentUser).child(key).child(idTour).child("status").setValue("Waiting cofirm");
                    FirebaseDatabase.getInstance().getReference().child("Booking").child(currentUser).child(key).child(idTour).child("startDate").setValue(date);
                    FirebaseDatabase.getInstance().getReference().child("Booking").child(currentUser).child(key).child(idTour).child("guideName").setValue(guideName);
                    FirebaseDatabase.getInstance().getReference().child("Booking").child(currentUser).child(key).child(idTour).child("price").setValue(tour.getPrice());
                    FirebaseDatabase.getInstance().getReference().child("Booking").child(currentUser).child(key).child(idTour).child("tourName").setValue(tour.getName().toString());
                    FirebaseDatabase.getInstance().getReference().child("Booking").child(currentUser).child(key).child(idTour).child("touristName").setValue(tourist.getName() +" "+tourist.getSurname());
                    FirebaseDatabase.getInstance().getReference().child("Booking").child(currentUser).child(key).child(idTour).child("touristAvatar").setValue(tourist.getImage());
                    Toast.makeText(TourDetailActivity.this, "You have booked succesfuly",
                                Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

}
