package com.ashutosh.iiitd.mymedicine;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import helperClasses.DBHelper;

public class Activity_for_alarm extends AppCompatActivity {

    String arr[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_for_alarm);

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        RelativeLayout rl_alarm_activity = (RelativeLayout)findViewById(R.id.activity_for_alarm);
        rl_alarm_activity.setBackground(wallpaperDrawable);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

        TextView tv_current_time = (TextView)findViewById(R.id.tv_current_time);
        TextView tv_todays_date = (TextView)findViewById(R.id.tv_todays_date);
        TextView tv_medicine_details = (TextView)findViewById(R.id.tv_medicine_details) ;

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm a");
        Date dateobj = new Date();
        String date = df.format(dateobj).toString();
        tv_todays_date.setText(date.substring(0, date.indexOf(' ')));
        tv_current_time.setText(date.substring(date.indexOf(' ')));
        final String csv_med = getIntent().getExtras().getString("CSV_LIST_MED");
        final String time = getIntent().getExtras().getString("time");
        String called_from = getIntent().getExtras().getString("called_from");
        final int alarm_id = getIntent().getExtras().getInt("alarm_id");
        if(csv_med != null){
            arr = csv_med.split("[,]");
            DBHelper db = new DBHelper(getApplicationContext());
            DBHelper.Medicine_alarm med_alarm = db.new Medicine_alarm();
            ArrayList<String> medicine_name = new ArrayList<>();
            String display = "Take medicines :-\n";
            for(int i = 0 ; i < arr.length ; i++){
                display += med_alarm.get_med_name_for_id(Integer.parseInt(arr[i])) + "\n";
                medicine_name.add(med_alarm.get_med_name_for_id(Integer.parseInt(arr[i])));
            }
            tv_medicine_details.setText(display);

            if(called_from.equals("application")){
                //decrement the count of the medicine alarms
                for(int i = 0 ; i < arr.length ; i++) {
                    med_alarm.decrement_dosage_count(Integer.parseInt(arr[i]));
                }
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "There is some problem. Try again !", Toast.LENGTH_SHORT).show();
        }

        SwipeButton snooze_twenty = (SwipeButton) findViewById(R.id.snooze_twenty);
        SwipeButtonCustomItems swipe_twenty = new SwipeButtonCustomItems() {
            @Override
            public void onSwipeConfirm() {
                Intent intent = new Intent(getApplicationContext(), Receiver.class);
                intent.putExtra("CSV_LIST_MED", csv_med);
                intent.putExtra("time", time);
                intent.putExtra("called_from", "alarm");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, (20*60*1000), pendingIntent);
                r.stop();
                Intent inte = (Intent)getIntent().getExtras().get("intent");
                PendingIntent.getBroadcast(getApplicationContext(), alarm_id, inte, PendingIntent.FLAG_UPDATE_CURRENT).cancel();
                finish();
            }
        };
        swipe_twenty
                .setButtonPressText("Keep Swiping Right")
                .setGradientColor1(0xFF888888)
                .setGradientColor2(0xFF666666)
                .setGradientColor2Width(60)
                .setGradientColor3(0xFF333333)
                .setPostConfirmationColor(0xFF888888)
                .setActionConfirmDistanceFraction(0.8)
                .setActionConfirmText("Snoozed for 20 minutes");

        if (snooze_twenty != null) {
            snooze_twenty.setSwipeButtonCustomItems(swipe_twenty);
        }

        SwipeButton snooze_ten = (SwipeButton) findViewById(R.id.snooze_ten);
        SwipeButtonCustomItems swipe_ten = new SwipeButtonCustomItems() {
            @Override
            public void onSwipeConfirm() {
                Intent intent = new Intent(getApplicationContext(), Receiver.class);
                intent.putExtra("CSV_LIST_MED", csv_med);
                intent.putExtra("time", time);
                intent.putExtra("called_from", "alarm");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, (10*60*1000), pendingIntent);
                r.stop();
                Intent inte = (Intent)getIntent().getExtras().get("intent");
                PendingIntent.getBroadcast(getApplicationContext(), alarm_id, inte, PendingIntent.FLAG_UPDATE_CURRENT).cancel();
                finish();
            }
        };
        swipe_ten
                .setButtonPressText("Keep Swiping Right")
                .setGradientColor1(0xFF888888)
                .setGradientColor2(0xFF666666)
                .setGradientColor2Width(60)
                .setGradientColor3(0xFF333333)
                .setPostConfirmationColor(0xFF888888)
                .setActionConfirmDistanceFraction(0.8)
                .setActionConfirmText("Snoozed for 10 minutes");

        if (snooze_ten != null) {
            snooze_ten.setSwipeButtonCustomItems(swipe_ten);
        }

        SwipeButton done = (SwipeButton) findViewById(R.id.done);
        SwipeButtonCustomItems swipe_done = new SwipeButtonCustomItems() {
            @Override
            public void onSwipeConfirm() {
                //now set the new alarm
                Intent intent = new Intent(getApplicationContext(), Receiver.class);
                intent.putExtra("CSV_LIST_MED", csv_med);
                intent.putExtra("time", time);
                intent.putExtra("called_from", "application");
                intent.putExtra("alarm_id", getIntent().getExtras().getInt("alarm_id"));
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Calendar alarm = Calendar.getInstance();
                Calendar now = Calendar.getInstance();
                alarm.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0, time.indexOf(':'))));
                alarm.set(Calendar.MINUTE, Integer.parseInt(time.substring(time.indexOf(':')+1)));
                if(alarm.before(now))
                    alarm.add(Calendar.DAY_OF_MONTH, 1);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.getTimeInMillis(), pendingIntent);
                r.stop();
                finish();
            }
        };
        swipe_done
                .setButtonPressText("Keep Swiping Right")
                .setGradientColor1(0xFF888888)
                .setGradientColor2(0xFF666666)
                .setGradientColor2Width(60)
                .setGradientColor3(0xFF333333)
                .setPostConfirmationColor(0xFF888888)
                .setActionConfirmDistanceFraction(0.8)
                .setActionConfirmText("Done");

        if (done != null) {
            done.setSwipeButtonCustomItems(swipe_done);
        }
    }
}
