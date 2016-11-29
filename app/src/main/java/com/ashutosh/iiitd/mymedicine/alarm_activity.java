package com.ashutosh.iiitd.mymedicine;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaExtractor;
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
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import helperClasses.DBHelper;

public class alarm_activity extends AppCompatActivity{

    private Alarm_adapter mAdapterTime;
    private ArrayList<Alarm_row> alarmList = new ArrayList<>();
    RecyclerView rv_edit_alarm;
    boolean medicine_display_state = false;
    int selected_item_days = 0;
    String csv_med = "";
    Spinner sp_days, sp_alarm_frequency;
    ArrayList<Medicine> selected_medicines;
    DBHelper db ;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle from_details = getIntent().getExtras();
        setContentView(R.layout.activity_alarm_activity);
        db = new DBHelper(getApplicationContext());
        //Recycler view for editing alarms
        selected_medicines = (ArrayList<Medicine>)from_details.getSerializable("KEY_FOR_LIST");
        //Setting medicines text view
        TextView tv_all_medicines = (TextView)findViewById(R.id.tv_all_medicines);
        Iterator<Medicine> it = selected_medicines.iterator();
        String all_medicines = "\n";
        Medicine m; int id;
        DBHelper.Medicine_alarm db_medicine = db.new Medicine_alarm();
        while(it.hasNext()){
            m = it.next();
            all_medicines += m.getMed_name() + "\n";
            id = db_medicine.get_id(getIntent().getExtras().getInt("PRES_ID"), m.getMed_name());
            m.setId(id);
            csv_med += id + ",";
        }
        csv_med = csv_med.substring(0, csv_med.lastIndexOf(','));
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
        sp_alarm_frequency = (Spinner) findViewById(R.id.sp_alarm_frequency);
        List<String> frequency = new ArrayList<String>();
        frequency.add("Select");
        frequency.add("Daily");
        frequency.add("Weekly");
        frequency.add("Monthly");
        ArrayAdapter<String> frequency_adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, frequency);
        frequency_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_alarm_frequency.setAdapter(frequency_adapter);
        //setting up listner for above spinner
        sp_alarm_frequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout layout;
                if(i == 1){
                    //case for daily
                    layout = (LinearLayout)findViewById(R.id.layout_weekly);
                    layout.setVisibility(View.GONE);
                    layout = (LinearLayout)findViewById(R.id.layout_monthly);
                    layout.setVisibility(View.GONE);
                    //make others disapperar
                    layout = (LinearLayout)findViewById(R.id.layout_daily);
                    layout.setVisibility(View.VISIBLE);
                    //and make this appear
                }
                else if(i == 2){
                    //case for weekly
                    layout = (LinearLayout)findViewById(R.id.layout_daily);
                    layout.setVisibility(View.GONE);
                    layout = (LinearLayout)findViewById(R.id.layout_monthly);
                    layout.setVisibility(View.GONE);
                    //make others disapperar
                    layout = (LinearLayout)findViewById(R.id.layout_weekly);
                    layout.setVisibility(View.VISIBLE);
                    //and make this appear
                }
                else if(i == 3){
                    //case for weekly
                    layout = (LinearLayout)findViewById(R.id.layout_daily);
                    layout.setVisibility(View.GONE);
                    layout = (LinearLayout)findViewById(R.id.layout_weekly);
                    layout.setVisibility(View.GONE);
                    //make others disapperar
                    layout = (LinearLayout)findViewById(R.id.layout_monthly);
                    layout.setVisibility(View.VISIBLE);
                    //and make this appear
                }
                else{
                    onNothingSelected(adapterView);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                LinearLayout layout;
                layout = (LinearLayout)findViewById(R.id.layout_weekly);
                layout.setVisibility(View.GONE);
                layout = (LinearLayout)findViewById(R.id.layout_monthly);
                layout.setVisibility(View.GONE);
                layout = (LinearLayout)findViewById(R.id.layout_daily);
                layout.setVisibility(View.GONE);
            }
        });

        //setting spinner for days
        sp_days = (Spinner)findViewById(R.id.sp_days);
        List<Integer> days = new ArrayList<Integer>();
        for(int i = 1 ; i <= 31 ; i++){
            days.add(i);
        }
        ArrayAdapter<Integer> days_adapter = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, days);
        sp_days.setAdapter(days_adapter);
        sp_days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_item_days = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selected_item_days = 0;
            }
        });


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

    //set the alarms here
    public void set_alarm(View v){

        if(sp_alarm_frequency.getSelectedItem().toString().equals("Select")){
            Toast.makeText(getApplicationContext(), "Select the frequency !", Toast.LENGTH_SHORT).show();
            return;
        }

        //Get the checkboxes ticked
        Calendar calendar = Calendar.getInstance();
        Iterator<Alarm_row> itr = alarmList.iterator();
        AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        while(itr.hasNext())
        {
            Alarm_row med = itr.next();
            if(med.getFlag()==1)
            {
                String time = med.getTime().trim();
                int hr = Integer.parseInt(time.substring(0, time.indexOf(':')));
                int min = Integer.parseInt(time.substring(time.indexOf(':')+1));
                //Toast.makeText(getApplicationContext(), hr + " bhasad " + min, Toast.LENGTH_SHORT).show();
                calendar.set(Calendar.HOUR_OF_DAY, hr);
                calendar.set(Calendar.MINUTE, min);

                DBHelper.Alarm alarm = db.new Alarm();
                int alarm_id = alarm.get_new_alarm_id();
                Intent intent = new Intent(getApplicationContext(), Receiver.class);
                intent.putExtra("CSV_LIST_MED", csv_med);
                intent.putExtra("time", time);
                intent.putExtra("called_from", "application");
                intent.putExtra("alarm_id", alarm_id);
                pendingIntent = PendingIntent.getBroadcast(alarm_activity.this, alarm_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                //alarm.insert_alarm(time, alarm_id, );
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",selected_medicines);
                returnIntent.putExtra("Pres_id",getIntent().getExtras().getInt("PRES_ID"));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        }

        //csv_med is the CSV of medications

        if(sp_alarm_frequency.getSelectedItem().toString().equals("Daily")){
            Iterator<Medicine> it = selected_medicines.iterator();
            DBHelper.Medicine_alarm db_medicine = db.new Medicine_alarm();
            Medicine m;
            while(it.hasNext()){
                m = it.next();
            }


        }
        else if(sp_alarm_frequency.getSelectedItem().toString().equals("Weekly")){

            boolean week[] = new boolean[7];
            for(int i = 0 ; i < 7 ; i++){
                week[i] = false;
            }
            //start filling all the entries for days
            CheckBox ck_day = (CheckBox) findViewById(R.id.ckb_sunday);
            if(ck_day.isChecked()){
                week[0] = true;
            }
            ck_day = (CheckBox) findViewById(R.id.ckb_monday);
            if(ck_day.isChecked()){
                week[1] = true;
            }
            ck_day = (CheckBox) findViewById(R.id.ckb_tuesday);
            if(ck_day.isChecked()){
                week[2] = true;
            }
            ck_day = (CheckBox) findViewById(R.id.ckb_wednesday);
            if(ck_day.isChecked()){
                week[3] = true;
            }
            ck_day = (CheckBox) findViewById(R.id.ckb_thursday);
            if(ck_day.isChecked()){
                week[4] = true;
            }
            ck_day = (CheckBox) findViewById(R.id.ckb_friday);
            if(ck_day.isChecked()){
                week[5] = true;
            }
            ck_day = (CheckBox) findViewById(R.id.ckb_saturday);
            if(ck_day.isChecked()){
                week[6] = true;
            }
            //end filling all the entries


        }
        else if(sp_alarm_frequency.getSelectedItem().toString().equals("Monthly")){

        }
    }
}