package com.ashutosh.iiitd.mymedicine;

import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity_for_alarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_alarm);

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        RelativeLayout rl_alarm_activity = (RelativeLayout)findViewById(R.id.activity_for_alarm);
        rl_alarm_activity.setBackground(wallpaperDrawable);

        TextView tv_current_time = (TextView)findViewById(R.id.tv_current_time);
        TextView tv_todays_date = (TextView)findViewById(R.id.tv_todays_date);

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm a");
        Date dateobj = new Date();
        String date = df.format(dateobj).toString();
        tv_todays_date.setText(date.substring(0, date.indexOf(' ')));
        tv_current_time.setText(date.substring(date.indexOf(' ')));

        SwipeButton snooze_twenty = (SwipeButton) findViewById(R.id.snooze_twenty);
        SwipeButtonCustomItems swipe_twenty = new SwipeButtonCustomItems() {
            @Override
            public void onSwipeConfirm() {

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
                .setActionConfirmText("Snnozed for 20 minutes");

        if (snooze_twenty != null) {
            snooze_twenty.setSwipeButtonCustomItems(swipe_twenty);
        }

        SwipeButton snooze_ten = (SwipeButton) findViewById(R.id.snooze_ten);
        SwipeButtonCustomItems swipe_ten = new SwipeButtonCustomItems() {
            @Override
            public void onSwipeConfirm() {

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
                .setActionConfirmText("Snnozed for 20 minutes");

        if (snooze_ten != null) {
            snooze_ten.setSwipeButtonCustomItems(swipe_ten);
        }

        SwipeButton done = (SwipeButton) findViewById(R.id.done);
        SwipeButtonCustomItems swipe_done = new SwipeButtonCustomItems() {
            @Override
            public void onSwipeConfirm() {

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
                .setActionConfirmText("Snnozed for 20 minutes");

        if (done != null) {
            done.setSwipeButtonCustomItems(swipe_done);
        }
    }
}
