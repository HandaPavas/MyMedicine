package helperClasses;

/**
 * Created by Ashutosh on 28-11-2016.
 */
import java.io.Serializable;
public class Prescription_obj implements Serializable{

    private String doc_name;
    private String hosp_name;
    private int id;

    Prescription_obj(int id, String doc, String hosp){


        this.doc_name = doc;
        this.hosp_name = hosp;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


