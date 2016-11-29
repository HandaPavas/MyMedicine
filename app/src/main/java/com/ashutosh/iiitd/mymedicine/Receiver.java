package com.ashutosh.iiitd.mymedicine;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class Receiver extends BroadcastReceiver {
    public Receiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent){

        try {

            String list = intent.getExtras().getString("CSV_LIST_MED");
            String time = intent.getExtras().getString("time");
            String called_from = intent.getExtras().getString("called_from");
            int alarm_id = intent.getExtras().getInt("alarm_id");
            Intent i = new Intent(context, Activity_for_alarm.class);
            i.putExtra("CSV_LIST_MED", list);
            i.putExtra("time", time);
            i.putExtra("called_from", called_from);
            i.putExtra("alarm_id", alarm_id);
            i.putExtra("intent", intent);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(i);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

