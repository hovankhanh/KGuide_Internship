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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TouristProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Tourist tourist;
    private String currentUser;
    private TextView tvName, tvGender, tvCountry, tvLanguage, tvDayOfBirth,
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
        tvGender = (TextView) findViewById(R.id.tv_gender);
        tvCountry = (TextView) findViewById(R.id.tv_country);
        tvLanguage = (TextView) findViewById(R.id.tv_language);
        tvPhoneNumber = (TextView) findViewById(R.id.tv_phone_number);
        tvJobPosition = (TextView) findViewById(R.id.tv_job);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvEmail = (TextView) findViewById(R.id.tv_email);
        tvDayOfBirth = (TextView) findViewById(R.id.tv_birtday);
        tvName = (TextView) findViewById(R.id.tv_name_tourist);
        civAvatar = (CircleImageView) findViewById(R.id.civ_avatar_tourist);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Users").child(currentUser);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    tourist = dataSnapshot.getValue(Tourist.class);
                    tvAddress.setText(tourist.getAddress().toString());
                    tvCountry.setText(tourist.getCountry().toString());
                    tvDayOfBirth.setText(tourist.getDayofbirth().toString());
                    tvEmail.setText(tourist.getEmail().toString());
                    tvGender.setText(tourist.getGender().toString());
                    tvJobPosition.setText(tourist.getJobposition());
                    tvLanguage.setText(tourist.getLanguage().toString());
                    tvName.setText(tourist.getName().toString() + " " + tourist.getSurname().toString());
                    String getAvatarImage = tourist.getImage().toString();
                    Picasso.get().load(getAvatarImage).into(civAvatar);
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
