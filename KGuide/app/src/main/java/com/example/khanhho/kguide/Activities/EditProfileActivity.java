package com.example.khanhho.kguide.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.Model.Tourist;
import com.example.khanhho.kguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {
   private FirebaseAuth mAuth;
   private DatabaseReference DBf;
   private Tourist tourist;
    public Uri imageUri;
   private String currentUser;
   private ImageView imgAvatar, imgIcon;
   private int Request_Code_Image = 1;
   private EditText edName, edSurname, edGender, edCountry, edLanguage, edDayOfBirth, edAddress, edJobPosition, edPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        imgAvatar = (ImageView)findViewById(R.id.img_avatarEdit);
        imgIcon = (ImageView)findViewById(R.id.icon_avatar);
        edName = (EditText)findViewById(R.id.edt_name);
        edSurname = (EditText)findViewById(R.id.edt_surname);
        edGender = (EditText)findViewById(R.id.edt_gender);
        edCountry = (EditText)findViewById(R.id.edt_country);
        edLanguage = (EditText)findViewById(R.id.edt_language);
        edDayOfBirth = (EditText)findViewById(R.id.edt_dayofbirth);
        edAddress = (EditText)findViewById(R.id.edt_address);
        edJobPosition = (EditText)findViewById(R.id.edt_jobposition);
        edPhoneNumber = (EditText)findViewById(R.id.edt_phonenumber);

        mAuth = FirebaseAuth.getInstance();
        DBf = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser().getUid();

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Users");

        myRef.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tourist = dataSnapshot.getValue(Tourist.class);
                edName.setText(tourist.getName());
                edSurname.setText(tourist.getSurname());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Request_Code_Image);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Request_Code_Image && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            imgIcon.setVisibility(View.INVISIBLE);
            imgAvatar.setImageURI(imageUri);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void Save(View view) {
        UpdateInfoProfile();
    }

    private void UpdateInfoProfile() {
        String nName = edName.getText().toString();
        String nSurname = edSurname.getText().toString();
        String nGender = edGender.getText().toString();
        String nCountry = edCountry.getText().toString();
        String nLanguage = edLanguage.getText().toString();
        String nDayOfBirth = edDayOfBirth.getText().toString();
        String nAddress = edAddress.getText().toString();
        String nJobPosition = edJobPosition.getText().toString();
        String nPhoneNumber = edPhoneNumber.getText().toString();


        if (TextUtils.isEmpty(nName)|| TextUtils.isEmpty(nSurname)
                || TextUtils.isEmpty(nGender) || TextUtils.isEmpty(nCountry)
                || TextUtils.isEmpty(nLanguage) || TextUtils.isEmpty(nDayOfBirth)
                || TextUtils.isEmpty(nAddress) || TextUtils.isEmpty(nJobPosition)
                || TextUtils.isEmpty(nPhoneNumber)){
            Toast.makeText(this, "Please enter your information ...", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String,Object> profileMap = new HashMap<>();
            profileMap.put("name", nName);
            profileMap.put("surname", nSurname);
            profileMap.put("gender", nGender);
            profileMap.put("country", nCountry);
            profileMap.put("language", nLanguage);
            profileMap.put("dayofbirth", nDayOfBirth);
            profileMap.put("address", nAddress);
            profileMap.put("jobposition", nJobPosition);
            profileMap.put("phonenumber", nPhoneNumber);
            profileMap.put("status", "tourist");
            currentUser = mAuth.getCurrentUser().getUid();
            DBf.child("Users").child(currentUser).updateChildren(profileMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(EditProfileActivity.this,"Update info successfully ...", Toast.LENGTH_SHORT).show();
                            }else{
                                String message = task.getException().toString();
                                Toast.makeText(EditProfileActivity.this,"Error: "+message, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }
}
