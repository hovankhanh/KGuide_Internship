package com.example.khanhho.kguide.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.khanhho.kguide.Model.DraffTour;
import com.example.khanhho.kguide.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DraffActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference DBf;
    String currentUser;
    TextView nhap;
    DraffTour draffTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draff);
        nhap = (TextView) findViewById(R.id.nhap);
        nhap.setText("hsaha");

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("tour/");

        myRef.child("tour").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                draffTour = dataSnapshot.getValue(DraffTour.class);
                nhap.setText(draffTour.getCity().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }
}
