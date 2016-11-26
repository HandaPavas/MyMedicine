package com.ashutosh.iiitd.mymedicine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by welcome on 19-10-2016.
 */

public class Medicine {

    private String med_name, type, dosage;

    public Medicine(String med_name, String type, String dosage) {

        this.med_name = med_name;
        this.type = type;
        this.dosage = dosage;
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
}
