package com.ashutosh.iiitd.mymedicine;

import java.io.Serializable;


/**
 * Created by welcome on 19-10-2016.
 */

public class Medicine implements Serializable{

    private String med_name, type, dosage;
    private int flag = 0, dosage_count;

    public Medicine(String med_name, String type, String dosage, int dosage_count) {

        this.med_name = med_name;
        this.type = type;
        this.dosage = dosage;
        this.dosage_count = dosage_count;
    }


    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getDosage_count() {
        return dosage_count;
    }

    public void setDosage_count(int dosage_count) {
        this.dosage_count = dosage_count;
    }
}
