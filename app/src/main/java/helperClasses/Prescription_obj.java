package helperClasses;

/**
 * Created by Ashutosh on 28-11-2016.
 */

public class Prescription_obj {

    private String doc_name;
    private String hosp_name;

    Prescription_obj(String doc, String hosp){

        this.doc_name = doc;
        this.hosp_name = hosp;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getHosp_name() {
        return hosp_name;
    }

    public void setHosp_name(String hosp_name) {
        this.hosp_name = hosp_name;
    }
}


