package com.ashutosh.iiitd.mymedicine;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.AlarmClock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class alarm_activity extends AppCompatActivity{

    private Alarm_adapter mAdapterTime;
    private ArrayList<Alarm_row> alarmList = new ArrayList<>();
    RecyclerView rv_edit_alarm;
    boolean medicine_display_state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle from_details = getIntent().getExtras();
        setContentView(R.layout.activity_alarm_activity);

        //Recycler view for editing alarms
        ArrayList<Medicine> selected_medicines = (ArrayList<Medicine>)from_details.getSerializable("KEY_FOR_LIST");
        //Setting medicines text view
        TextView tv_all_medicines = (TextView)findViewById(R.id.tv_all_medicines);
        Iterator<Medicine> it = selected_medicines.iterator();
        String all_medicines = "\n";
        while(it.hasNext()){
            all_medicines += it.next().getMed_name() + "\n";
        }
        tv_all_medicines.setText(all_medicines);

        //setting image button for hiding
        final ImageButton btn_view_all = (ImageButton)findViewById(R.id.btn_view_all);
        btn_view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(medicine_display_state == false){
                    medicine_display_state = true;
                    btn_view_all.setImageResource(R.drawable.ic_remove_black_24px);
                    LinearLayout ll = (LinearLayout)findViewById(R.id.ll_medicines);
                    ll.setVisibility(View.VISIBLE);
                }
                else{
                    medicine_display_state = false;
                    btn_view_all.setImageResource(R.drawable.ic_add_black_24px);
                    LinearLayout ll = (LinearLayout)findViewById(R.id.ll_medicines);
                    ll.setVisibility(View.GONE);
                }
            }
        });

        //setting spinner for frequency
        Spinner sp_alarm_frequency = (Spinner) findViewById(R.id.sp_alarm_frequency);
        List<String> frequency = new ArrayList<String>();
        frequency.add("Daily");
        frequency.add("Monthly");
        frequency.add("Weekly");
        ArrayAdapter<String> frequency_adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, frequency);
        frequency_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_alarm_frequency.setAdapter(frequency_adapter);

        //setting spinner for days
        Spinner sp_days = (Spinner)findViewById(R.id.sp_days);
        List<Integer> days = new ArrayList<Integer>();
        for(int i = 1 ; i <= 31 ; i++){
            days.add(i);
        }
        ArrayAdapter<Integer> days_adapter = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, days);
        sp_days.setAdapter(days_adapter);


        rv_edit_alarm = (RecyclerView)findViewById(R.id.rv_edit_alarm);
        mAdapterTime = new Alarm_adapter(alarmList, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_edit_alarm.setLayoutManager(mLayoutManager);
        rv_edit_alarm.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv_edit_alarm.setItemAnimator(new DefaultItemAnimator());
        rv_edit_alarm.setAdapter(mAdapterTime);
        prepareInitialAlarmData();

    }

    //prepare Data for alarm recycler view
    void prepareInitialAlarmData(){


        Alarm_row alarm = new Alarm_row("Breakfast");
        alarmList.add(alarm);
        alarm = new Alarm_row("Lunch");
        alarmList.add(alarm);
        alarm = new Alarm_row("Dinner");
        alarmList.add(alarm);
        mAdapterTime.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}