package com.example.khanhho.kguide.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.khanhho.kguide.R;

public class OptionActivity extends AppCompatActivity {
    Button btnTourist, btnGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        btnGuide = (Button) findViewById(R.id.btn_guide);
        btnTourist = (Button) findViewById(R.id.btn_tourist);
        btnTourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(OptionActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
    }
}
