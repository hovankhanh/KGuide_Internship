package com.example.khanhho.kguide.Activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.khanhho.kguide.R;

import java.util.ArrayList;

public class TourDetailActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);

        viewFlipper =(ViewFlipper) findViewById(R.id.viewlipper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionViewFlipper();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();
        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void ActionViewFlipper() {
        ArrayList<Integer> mangQuangcao = new ArrayList<>();
        mangQuangcao.add(R.drawable.us);
        mangQuangcao.add(R.drawable.rs);
        mangQuangcao.add(R.drawable.vn);
        for (int i = 0; i < mangQuangcao.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mangQuangcao.get(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        Animation  animation_slide_out = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        viewFlipper.setAnimation(animation_slide_in);
        viewFlipper.setAnimation(animation_slide_out);
    }
}
