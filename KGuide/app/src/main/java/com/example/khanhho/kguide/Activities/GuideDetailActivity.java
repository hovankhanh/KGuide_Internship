package com.example.khanhho.kguide.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.khanhho.kguide.Adapter.GuideDetailFragmentAdapter;
import com.example.khanhho.kguide.Model.Guide;
import com.example.khanhho.kguide.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class GuideDetailActivity extends AppCompatActivity {
    private ViewPager nVPTourist;
    private CircleImageView cvAvatarGuide;
    private TextView tvNameGuide;
    private String currentUser;
    private FirebaseAuth mAuth;
    private RatingBar rbStarDetail;
    private Guide guide;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        cvAvatarGuide = (CircleImageView) findViewById(R.id.civ_avatar_guide) ;
        tvNameGuide = (TextView) findViewById(R.id.tv_guide_name);
        rbStarDetail = (RatingBar) findViewById(R.id.rb_star_detail);
        nVPTourist = (ViewPager) findViewById(R.id.vp_tourist);

        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewTouristFragment();
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        if (sharedPreferences.getString("user", "").equals("guide")) {
            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser().getUid();
        }else {
//            currentUser = "KmnCr8IYDqe6u1fC139P2QAZvul1";
            Intent intent = getIntent();
            currentUser = intent.getStringExtra("key");
        }

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Users").child(currentUser);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                guide = dataSnapshot.getValue(Guide.class);
                String getAvatarImage = guide.getImage().toString();
                Picasso.get().load(getAvatarImage).into(cvAvatarGuide);
                tvNameGuide.setText(guide.getName().toString() +" "+ guide.getSurname().toString());
                rbStarDetail.setRating(guide.getStar());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();
        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void ViewTouristFragment() {
        GuideDetailFragmentAdapter myPagerAdapter = new GuideDetailFragmentAdapter(getSupportFragmentManager());
        nVPTourist.setAdapter(myPagerAdapter);
        TabLayout tablayout = (TabLayout) findViewById(R.id.tablayout);
        tablayout.setupWithViewPager(nVPTourist);
    }

}
