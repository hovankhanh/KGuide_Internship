package com.example.khanhho.kguide.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khanhho.kguide.Adapter.GuideFragmentAdapter;
import com.example.khanhho.kguide.Adapter.TouristFragmentAdapter;
import com.example.khanhho.kguide.Model.Guide;
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

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager nVPTourist;
    private FirebaseAuth mAuth;
    private Tourist tourist;
    private Guide guide;
    private String currentUser;
    private TextView tvUserName, tvStatus;
    private CircleImageView civAvatar;
    private LinearLayout lnProfile;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nVPTourist = (ViewPager) findViewById(R.id.vp_tourist);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        tvUserName = (TextView) headerLayout.findViewById(R.id.tv_username);
        tvStatus = (TextView) headerLayout.findViewById(R.id.tv_status);
        civAvatar = (CircleImageView) headerLayout.findViewById(R.id.civ_avatar);
        lnProfile = (LinearLayout) headerLayout.findViewById(R.id.ln_profile);

        navigationView.setNavigationItemSelectedListener(this);
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        if (sharedPreferences.getString("user", "").equals("guide")){
            final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            DatabaseReference myRef = database.child("Users");
            myRef.child(currentUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    guide = dataSnapshot.getValue(Guide.class);
                    tvUserName.setText(guide.getName().toString() +" "+ guide.getSurname().toString());
                    tvStatus.setText(guide.getStatus().toString());
                    if (!guide.getImage().toString().equals("")) {
                        String getAvatarImage = guide.getImage().toString();
                        Picasso.get().load(getAvatarImage).into(civAvatar);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            ViewGuideFragment();
        }else {
            ViewTouristFragment();
            final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            DatabaseReference myRef = database.child("Users");
            myRef.child(currentUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    tourist = dataSnapshot.getValue(Tourist.class);
                    tvUserName.setText(tourist.getName()+" "+ tourist.getSurname());
                    tvStatus.setText(tourist.getStatus());
                    if (!tourist.getImage().toString().equals("")) {
                        String getAvatarImage = tourist.getImage().toString();
                        Picasso.get().load(getAvatarImage).into(civAvatar);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        lnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getString("user", "").equals("guide")){
                    Intent login = new Intent(MainActivity.this, GuideDetailActivity.class);
                    startActivity(login);
                }else {
                    Intent login = new Intent(MainActivity.this, TouristProfileActivity.class);
                    startActivity(login);
                }
            }
        });
    }



    private void ViewTouristFragment(){
        TouristFragmentAdapter myPagerAdapter = new TouristFragmentAdapter(getSupportFragmentManager());
        nVPTourist.setAdapter(myPagerAdapter);
        TabLayout tablayout = (TabLayout) findViewById(R.id.tablayout);
        tablayout.setupWithViewPager(nVPTourist);
    }
    private void ViewGuideFragment(){
        GuideFragmentAdapter myPagerAdapter = new GuideFragmentAdapter(getSupportFragmentManager());
        nVPTourist.setAdapter(myPagerAdapter);
        TabLayout tablayout = (TabLayout) findViewById(R.id.tablayout);
        tablayout.setupWithViewPager(nVPTourist);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent login = new Intent(this, MyTourTouristActivity.class);
            startActivity(login);

        } else if (id == R.id.nav_slideshow) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();

        } else if (id == R.id.nav_manage) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(MainActivity.this, "quang khanh1",Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(MainActivity.this, "quang khanh2",Toast.LENGTH_SHORT).show();
                return true;
            }
        };

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setOnActionExpandListener(onActionExpandListener);
        return true;
    }

    @Override
    protected void onPause() {
        Log.d("quang", "on pause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("quang", "on stop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("quang", "on destroy");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.d("quang", "on start");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d("quang", "on restart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d("quang", "on resume");
        super.onResume();
    }
}
