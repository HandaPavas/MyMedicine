package com.ashutosh.iiitd.mymedicine;

/**
 * Created by Ashutosh on 17-11-2016.
 */
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.List;

import static com.ashutosh.iiitd.mymedicine.R.id.time_checkbox;
import static com.ashutosh.iiitd.mymedicine.R.id.tv_edit_time;

public class Alarm_adapter extends RecyclerView.Adapter<Alarm_adapter.MyViewHolder>{

    private List<Alarm_row> alarmList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_edit_time;
        public RadioButton rb_before,rb_with,rb_after;
        public Spinner sp_dosage;
        public ImageButton btn_toggle_visibility;
        public RelativeLayout rl_edit_alarm;
        public CheckBox time_checkbox;

        public MyViewHolder(View view) {
            super(view);
            tv_edit_time = (TextView) view.findViewById(R.id.tv_edit_time);
            rb_before = (RadioButton) view.findViewById(R.id.rb_before);
            rb_with = (RadioButton) view.findViewById(R.id.rb_with);
            rb_after = (RadioButton) view.findViewById(R.id.rb_after);
            sp_dosage = (Spinner)view.findViewById(R.id.sp_dosage);
            btn_toggle_visibility = (ImageButton)view.findViewById(R.id.btn_edit);
            rl_edit_alarm = (RelativeLayout)view.findViewById(R.id.rl_edit_alarm);
            time_checkbox = (CheckBox)view.findViewById(R.id.time_checkbox);
        }


    }

    public Alarm_adapter(List<Alarm_row> alarmList, Context context) {
        this.alarmList = alarmList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_row, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Alarm_row alarm = alarmList.get(position);
        //String hint_prior = holder.name.getHint().toString();

        holder.tv_edit_time.setText("Edit " + alarm.getEvent_name() + " alarm !");
        holder.rb_before.setText("Before");
        holder.rb_with.setText("With");
        holder.rb_after.setText("After");
        alarm.setTime(holder.time_checkbox.getText().toString());
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_item,alarm.getNumber());
        holder.btn_toggle_visibility.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(holder.rl_edit_alarm.getVisibility()==View.VISIBLE){
                    holder.rl_edit_alarm.setVisibility(View.GONE);
                }
                else
                    holder.rl_edit_alarm.setVisibility(View.VISIBLE);
            }
        });
        holder.tv_edit_time.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        holder.time_checkbox.setText("   "+selectedHour + ":" + selectedMinute);
                        holder.time_checkbox.setChecked(false);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        holder.time_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ) {
                    alarm.setFlag(1);
                    alarm.setTime(holder.time_checkbox.getText().toString());

                }
                else{
                    alarm.setFlag(0);
                }

            }
        });
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.sp_dosage.setAdapter(dataAdapter);
    }


    public int getItemCount() {

        return alarmList.size();
    }

}
