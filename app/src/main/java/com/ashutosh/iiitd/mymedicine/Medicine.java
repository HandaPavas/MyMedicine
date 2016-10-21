package com.ashutosh.iiitd.mymedicine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by welcome on 19-10-2016.
 */

public class Medicine {

    private String med_name, btn_name;
    private List<String> type= new ArrayList<>();

    public Medicine(String med_name,String btn_name){

        this.med_name = med_name;
        this.btn_name = btn_name;
        this.type.add("Tablet");
        this.type.add("Capsule");
        this.type.add("Powder");
        this.type.add("Syrup");
        this.type.add("Injection");
        this.type.add("Others");
    }


    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public String getBtn_name() {
        return btn_name;
    }

    public void setBtn_name(String btn_name) {
        this.btn_name = btn_name;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }
}
