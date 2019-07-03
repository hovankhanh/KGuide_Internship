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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
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

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.khanhho.kguide.R.id.nav_notification;

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
    private SearchView searchView;
    private DatabaseReference reference;


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
        }else if (sharedPreferences.getString("user", "").equals("tourist1")){
            reference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser);
            Intent intent = getIntent();
            Map map = new HashMap();
            map.put("email",intent.getStringExtra("email"));
            map.put("name",intent.getStringExtra("name"));
            map.put("surname","");
            map.put("status", "tourist");
            map.put("gender", "");
            map.put("country", "");
            map.put("address", "");
            map.put("dayofbirth", "");
            map.put("phonenumber", "");
            map.put("jobposition", "");
            map.put("language", "");
            map.put("image", "https://firebasestorage.googleapis.com/v0/b/kguide-47a11.appspot.com/o/User%20Image%2Ficon_avatar_editprofile.png?alt=media&token=295f20da-8d3b-4bc5-a0d4-e22e94c70aa7");
            reference.updateChildren(map);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user", "tourist");
            editor.commit();
        }
        else {
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
            SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user", "Logout");
            editor.commit();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            FirebaseAuth.getInstance().signOut();
            finish();

        } else if (id == nav_notification) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        // Associate searchable configuration with the SearchView
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//
//        SearchView searchView = (SearchView) menu.findItem(R.id.action_search);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
////                // filter recycler view when query submitted
////                mAdapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                Log.d("khanh", "huhu");
//                return true;
//            }
//        });
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

}
