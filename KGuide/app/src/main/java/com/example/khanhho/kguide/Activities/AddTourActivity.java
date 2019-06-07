package com.example.khanhho.kguide.Activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.khanhho.kguide.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTourActivity extends AppCompatActivity {
    private Switch swGroup;
    private LinearLayout lnTime, lnTopic, lnLanguage, lnService, lnAge, lnGroupSize, lnParentGroupSize;
    private TextView tvTime, tvTopic, tvLanguage, tvService, tvAge, tvGroupSize, tvCity;
    private TextView tvAddMo, tvAddTue, tvAddWed, tvAddThu, tvAddFri, tvAddSat, tvAddSun;
    private Boolean blTime = true, blTopic = true, blLanguage = true, blService = true, blAge = true, blGroupSize = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tvAddMo = (TextView) findViewById(R.id.tv_monday);
        tvAddTue = (TextView) findViewById(R.id.tv_tuesday);
        tvAddWed = (TextView) findViewById(R.id.tv_wednesday);
        tvAddThu = (TextView) findViewById(R.id.tv_thursday);
        tvAddFri = (TextView) findViewById(R.id.tv_friday);
        tvAddSat = (TextView) findViewById(R.id.tv_Saturday);
        tvAddSun = (TextView) findViewById(R.id.tv_sunday);
        lnTime = (LinearLayout) findViewById(R.id.ln_time);
        lnAge = (LinearLayout) findViewById(R.id.ln_age);
        lnLanguage = (LinearLayout) findViewById(R.id.ln_language);
        lnService = (LinearLayout) findViewById(R.id.ln_service);
        lnTopic = (LinearLayout) findViewById(R.id.ln_topic);
        lnGroupSize = (LinearLayout) findViewById(R.id.ln_group_size);
        lnParentGroupSize = (LinearLayout) findViewById(R.id.ln_parent_group_size);
        tvCity = (TextView) findViewById(R.id.tv_city);
        tvTime = (TextView)findViewById(R.id.tv_time);
        tvAge = (TextView) findViewById(R.id.tv_age);
        tvLanguage = (TextView) findViewById(R.id.tv_language);
        tvService = (TextView) findViewById(R.id.tv_service);
        tvTopic = (TextView) findViewById(R.id.tv_topic);
        tvGroupSize = (TextView) findViewById(R.id.tv_group_size);
        swGroup = (Switch)findViewById(R.id.sw_group);


        lnTime.setVisibility(View.GONE);
        lnTopic.setVisibility(View.GONE);
        lnService.setVisibility(View.GONE);
        lnLanguage.setVisibility(View.GONE);
        lnAge.setVisibility(View.GONE);
        lnGroupSize.setVisibility(View.GONE);
        lnParentGroupSize.setVisibility(View.GONE);


        swGroup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(swGroup.isChecked())
                {
                    lnParentGroupSize.setVisibility(View.VISIBLE);
                }
                else {
                    lnParentGroupSize.setVisibility(View.GONE);
                }
            }
        });

        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHourPicker();
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blTime) {
                    lnTime.setVisibility(View.VISIBLE);
                    blTime = false;
                }else {
                    lnTime.setVisibility(View.GONE);
                    blTime = true;
                }
            }
        });
        tvTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blTopic) {
                    ArrayList<String> list = new ArrayList<>();
                    ListView lvListview = (ListView)findViewById(R.id.lv_topic);
                    String shortcuts[] = getResources().getStringArray(R.array.topic);
                    for(String abc: shortcuts){
                        list.add(abc);
                    }
                    ArrayAdapter<String> arrayAdapter
                            = new ArrayAdapter<String>(AddTourActivity.this, android.R.layout.simple_list_item_1,list );

                    lvListview.setAdapter(arrayAdapter);
                    lnTopic.setVisibility(View.VISIBLE);
                    blTopic = false;
                }else {
                    lnTopic.setVisibility(View.GONE);
                    blTopic = true;
                }

            }
        });
        tvService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blService) {
                    ArrayList<String> list = new ArrayList<>();
                    ListView lvListview = (ListView)findViewById(R.id.lv_service);
                    String shortcuts[] = getResources().getStringArray(R.array.service);
                    for(String abc: shortcuts){
                        list.add(abc);
                    }
                    ArrayAdapter<String> arrayAdapter
                            = new ArrayAdapter<String>(AddTourActivity.this, android.R.layout.simple_list_item_1,list );

                    lvListview.setAdapter(arrayAdapter);
                    lnService.setVisibility(View.VISIBLE);
                    blService = false;
                }else {
                    lnService.setVisibility(View.GONE);
                    blService = true;
                }
            }
        });
        tvLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blLanguage) {
                    ArrayList<String> list = new ArrayList<>();
                    ListView lvListview = (ListView)findViewById(R.id.lv_language);
                    String shortcuts[] = getResources().getStringArray(R.array.language);
                    for(String abc: shortcuts){
                        list.add(abc);
                    }
                    ArrayAdapter<String> arrayAdapter
                            = new ArrayAdapter<String>(AddTourActivity.this, android.R.layout.simple_list_item_1,list );

                    lvListview.setAdapter(arrayAdapter);
                    lnLanguage.setVisibility(View.VISIBLE);
                    blLanguage = false;
                }else {
                    lnLanguage.setVisibility(View.GONE);
                    blLanguage = true;
                }
            }
        });
        tvAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blAge) {
                    ArrayList<String> list = new ArrayList<>();
                    ListView lvListview = (ListView)findViewById(R.id.lv_age);
                    String shortcuts[] = getResources().getStringArray(R.array.age);
                    for(String abc: shortcuts){
                        list.add(abc);
                    }
                    ArrayAdapter<String> arrayAdapter
                            = new ArrayAdapter<String>(AddTourActivity.this, android.R.layout.simple_list_item_1,list );

                    lvListview.setAdapter(arrayAdapter);
                    lnAge.setVisibility(View.VISIBLE);
                    blAge = false;
                }else {
                    lnAge.setVisibility(View.GONE);
                    blAge = true;
                }
            }
        });
        tvGroupSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blGroupSize) {
                    lnGroupSize.setVisibility(View.VISIBLE);
                    blGroupSize = false;
                }else {
                    lnGroupSize.setVisibility(View.GONE);
                    blGroupSize = true;
                }
            }
        });

        tvAddMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHourPicker();
            }
        });


//        String shortcuts[] = getResources().getStringArray(R.array.province);
//
//        for(String abc: shortcuts){
//            Log.d("abc",abc);
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();
        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showHourPicker() {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);
        final int amPm = myCalender.get(Calendar.AM_PM);

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);
                    myCalender.set(Calendar.AM_PM, amPm);
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                myTimeListener, hour, minute, false);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }
}
