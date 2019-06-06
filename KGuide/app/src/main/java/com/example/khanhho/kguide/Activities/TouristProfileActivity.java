package com.example.khanhho.kguide.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.khanhho.kguide.Model.Tourist;
import com.example.khanhho.kguide.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class TouristProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Tourist tourist;
    private String currentUser;
    private TextView tvName, tvSurname, tvGender, tvCountry, tvLanguage, tvDayOfBirth,
            tvAddress, tvJobPosition, tvPhoneNumber, tvEmail;
    private TextView tvEdit;
    CircleImageView civAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_profile);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();

        tvEdit = (TextView) findViewById(R.id.tv_edit);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Users").child(currentUser);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    tourist = dataSnapshot.getValue(Tourist.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TouristProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

}
