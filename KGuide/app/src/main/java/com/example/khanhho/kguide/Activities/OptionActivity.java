package com.example.khanhho.kguide.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertView("\n If you want to become a tour guide of KGuide. You must go to our nearest office. Our employee will guide you detail: \n Office of Kguide \n Ha Noi : \n 11F Handico Building, Pham Hung Road, Nam Tu Liem Dist., Ha Noi\n Da Nang :\n 9F HTP Building, 434 Tran Khat Chan, 1km from Bach Khoa University, Ha Noi\n Ho Chi Minh :\n 8F Nice building, 467 Dien Bien Phu Str., Ward 25, Binh Thanh district, HCMC \n");
            }
        });
    }

    private void alertView(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Notification")
                .setMessage(message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.dismiss();
                    }
        }).show();
    }
}
