package com.example.khanhho.kguide.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.khanhho.kguide.R;

public class EditProfileActivity extends AppCompatActivity {
    public EditText nFirstName, nLastName, nGender, nCountry, nLanguage, nDayOfBirth, nAddress, nJobPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nFirstName = (EditText)findViewById(R.id.edt_firstname);
        nLastName = (EditText)findViewById(R.id.edt_lastname);
        nGender = (EditText)findViewById(R.id.edt_gender);
        nCountry = (EditText)findViewById(R.id.edt_country);
        nLanguage = (EditText)findViewById(R.id.edt_language);
        nDayOfBirth = (EditText)findViewById(R.id.edt_dayofbirth);
        nAddress = (EditText)findViewById(R.id.edt_address);
        nJobPosition = (EditText)findViewById(R.id.edt_jobposition);
    }

    public void Save(View view) {
        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
