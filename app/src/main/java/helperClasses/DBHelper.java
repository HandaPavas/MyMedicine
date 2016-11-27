package helperClasses;

/**
 * Created by Ashutosh on 21-10-2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.ashutosh.iiitd.mymedicine.Medicine;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "MyMedicineDB";
    static Prescription p;
    static Medicine_alarm m;
    static Alarm a;
    DBHelper obj_to_use = this;

    public class Prescription{
        public static final String TABLE_NAME = "PRESCRIPTION";
        public static final String COL_ID = "ID";
        public static final String COL_DOCTOR_NAME = "DOCTOR_NAME";
        public static final String COL_HOSPITAL_NAME = "HOSPITAL_NAME";
        public static final String COL_DATE_OF_PRESCRIPTION = "DATE_OF_PRESCRIPTION";
        public static final String COL_IMAGE = "IMAGE";

        int get_new_image_name(){
            String query = "select max(" + COL_IMAGE + ") from " + TABLE_NAME;
            SQLiteDatabase db = obj_to_use.getReadableDatabase();
            Cursor result = db.rawQuery(query, null);
            result.moveToFirst();
            if(result.isAfterLast() == true){
                int last_insert = result.getInt(result.getColumnIndex(COL_IMAGE));
                return (last_insert + 1);
            }
            else{
                return 1;
            }
        }

        public int insert_data(String doc_name, String hosp_name, String date){
            SQLiteDatabase db = obj_to_use.getWritableDatabase();
            int k = get_new_image_name();
            String query = "insert into " + TABLE_NAME+" ("
                    + COL_DOCTOR_NAME +", "
                    + COL_HOSPITAL_NAME +", "
                    + COL_DATE_OF_PRESCRIPTION +", "
                    + COL_IMAGE +" ) VALUES ( '"
                    + doc_name +"', '"
                    + hosp_name +"' , '"
                    + date +"',"
                    + k +" );";
            db.execSQL(query);

            query = "select max(" + COL_ID + ") from " + TABLE_NAME;
            Cursor result = db.rawQuery(query, null);
            result.moveToFirst();
            int last_insert = 0;
            if(result.isAfterLast() == true){
                last_insert = result.getInt(result.getColumnIndex(COL_ID));

            }
            return last_insert;
        }
    }

    public class Medicine_alarm{
        public static final String TABLE_NAME = "MEDICINE";
        public static final String COL_MED_ID = "MEDICINE_ID";
        public static final String COL_PRE_ID = "PRESCRIPTION_ID";
        public static final String COL_NAME = "MEDICINE_NAME";
        public static final String COL_MAKE = "MAKE";
        public static final String COL_DOSAGE = "DOSAGE";
        public static final String COL_DOSAGE_COUNT = "DOSAGE_COUNT";

        //insert medicines into database here
        public void insert_med(Medicine med, int pres_id){
            SQLiteDatabase db = obj_to_use.getWritableDatabase();
            String query = "insert into "+ TABLE_NAME +" ("
                    + COL_NAME +", "
                    + COL_MAKE +", "
                    + COL_DOSAGE +", "
                    + COL_PRE_ID +", "
                    + COL_DOSAGE_COUNT +" ) VALUES ( '"
                    + med.getMed_name() +"', '"
                    + med.getType() +"', '"
                    + med.getDosage() +"', "
                    + pres_id +", "
                    + med.getDosage_count()+" );";
            db.execSQL(query);
        }
    }

    class Alarm{
        public static final String TABLE_NAME = "ALARM";
        public static final String COL_ID = "ALARM_ID";
        public static final String COL_TIME = "TIME";
        public static final String COL_PERIOD = "PERIOD";
        public static final String COL_RECURRENCE_LEFT = "RECURRENCE_LEFT";
        public static final String COL_MEDICATION = "MEDICATION";
        public static final String COL_IS_INFINITE = "IS_INFINITE";
        public static final String COL_IS_ACTIVE = "IS_ACTIVE";
    }

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create the Prescription table
        String create_prescription = "create table " + p.TABLE_NAME + " ( " +
                p.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                p.COL_DOCTOR_NAME + " text, " +
                p.COL_HOSPITAL_NAME + " text, " +
                p.COL_DATE_OF_PRESCRIPTION + " text, " +
                p.COL_IMAGE + " INTEGER " +
                ")";

        String create_medicine = "create table " + m.TABLE_NAME + " ( " +
                m.COL_MED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                m.COL_PRE_ID + " INTEGER, " +
                m.COL_NAME + " text, " +
                m.COL_MAKE + " text, " +
                m.COL_DOSAGE + " text, " +
                m.COL_DOSAGE_COUNT  + " INTEGER " +
                ")";

        String create_alarm = "create table " + a.TABLE_NAME + " ( " +
                a.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                a.COL_TIME + " text, " +
                a.COL_PERIOD + " INTEGER, " +
                a.COL_RECURRENCE_LEFT + " INTEGER, " +
                a.COL_MEDICATION + " text, " +
                a.COL_IS_ACTIVE + " text, " +
                a.COL_IS_INFINITE + " text " +
                ")";

        db.execSQL(create_prescription);
        db.execSQL(create_medicine);
        db.execSQL(create_alarm);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop doctor_details
        String SQL_DELETE_TABLE;
        SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + p.TABLE_NAME;
        db.execSQL(SQL_DELETE_TABLE);
        SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + a.TABLE_NAME;
        db.execSQL(SQL_DELETE_TABLE);
        SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + m.TABLE_NAME;
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);

    }
}