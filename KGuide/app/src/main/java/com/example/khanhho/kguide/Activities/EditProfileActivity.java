package com.example.khanhho.kguide.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.khanhho.kguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference DBf;
    String currentUser;
    public EditText edName, edSurname, edGender, edCountry, edLanguage, edDayOfBirth, edAddress, edJobPosition, edPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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
    }

    public void Save(View view) {
        UpdateInfoProfile();
//        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
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


        if (TextUtils.isEmpty(nName)|| TextUtils.isEmpty(nSurname) ){
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
