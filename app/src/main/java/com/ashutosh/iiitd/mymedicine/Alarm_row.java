package com.ashutosh.iiitd.mymedicine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashutosh on 17-11-2016.
 */

public class Alarm_row {

    private String event_name;
    private String time;
    private List<Integer> number= new ArrayList<>();
    private int flag = 0;

    public Alarm_row(String event_name){
        this.event_name = event_name;
        number.add(1);
        number.add(2);
        number.add(3);
        number.add(4);
        number.add(5);
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public List<Integer> getNumber() {
        return number;
    }

    public void setNumber(List<Integer> type) {
        this.number = type;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
