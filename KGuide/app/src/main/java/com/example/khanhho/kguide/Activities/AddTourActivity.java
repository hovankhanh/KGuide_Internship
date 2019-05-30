package com.example.khanhho.kguide.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.khanhho.kguide.R;

public class AddTourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String shortcuts[] = getResources().getStringArray(R.array.province);

        for(String abc: shortcuts){
            Log.d("abc",abc);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();
        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
