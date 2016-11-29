package helperClasses;

/**
 * Created by Ashutosh on 21-10-2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.ashutosh.iiitd.mymedicine.Medicine;

import java.util.ArrayList;

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
        public static final String COL_IS_COMPLETE = "IS_COMPLETE";

        public int get_new_image_name(){
            String query = "select * from " + TABLE_NAME;
            SQLiteDatabase db = obj_to_use.getReadableDatabase();
            Cursor result = db.rawQuery(query, null);
            result.moveToFirst();
            int last_insert = -1;
            while(result.isAfterLast() == false){
                last_insert = result.getInt(result.getColumnIndex(COL_IMAGE));
                result.moveToNext();
            }
            if(last_insert == -1)
                return 1;
            else
                return (last_insert+1);
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
            db = obj_to_use.getReadableDatabase();
            query = "select * from " + TABLE_NAME;
            Cursor result = db.rawQuery(query, null);
            result.moveToFirst();
            String x;
            int last_insert = 0, count = 0;
            while(result.isAfterLast() == false){
                last_insert = result.getInt(result.getColumnIndex(COL_ID));
                result.moveToNext();
            }
            return last_insert;
        }
        //read and populate data
        public ArrayList<Prescription_obj> getAllPrescription()
        {
            ArrayList<Prescription_obj> array_list = new ArrayList<Prescription_obj>();

            //hp = new HashMap();
            SQLiteDatabase db = obj_to_use.getReadableDatabase();
            Cursor res =  db.rawQuery( "select "+ COL_ID+", "+COL_DOCTOR_NAME + ", "+ COL_HOSPITAL_NAME + " from "+TABLE_NAME, null );
            res.moveToFirst();

            while(res.isAfterLast() == false){
                int id = Integer.parseInt(res.getString(res.getColumnIndex(COL_ID)));
                String doc = res.getString(res.getColumnIndex(COL_DOCTOR_NAME));
                String hosp = res.getString(res.getColumnIndex(COL_HOSPITAL_NAME));
                Prescription_obj obj = new Prescription_obj(id,doc,hosp);
                array_list.add(obj);
                res.moveToNext();
            }
            return array_list;
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

        //retrieve medicines belonging to same prescription
        public ArrayList<Medicine> retrieve_data(int pres_id){

            ArrayList<Medicine> list_for_adapter = new ArrayList<>();
            SQLiteDatabase db = obj_to_use.getReadableDatabase();
            Cursor res =  db.rawQuery("select "+COL_NAME+", "+COL_MAKE+", "+COL_DOSAGE+", "+COL_DOSAGE_COUNT
                                        +" from "+TABLE_NAME+" WHERE "+COL_PRE_ID+" = "+pres_id+" ;",null);
            res.moveToFirst();
            while(res.isAfterLast() == false){
                String name = res.getString(res.getColumnIndex(COL_NAME));
                String make = res.getString(res.getColumnIndex(COL_MAKE));
                String dosage = res.getString(res.getColumnIndex(COL_DOSAGE));
                int dosage_count = Integer.parseInt(res.getString(res.getColumnIndex(COL_DOSAGE_COUNT)));
                Medicine obj = new Medicine(name,make,dosage,dosage_count);
                list_for_adapter.add(obj);
                res.moveToNext();
            }
            return list_for_adapter;
        }

        public int get_id(int pres_id, String med_name){
            SQLiteDatabase db = obj_to_use.getReadableDatabase();
            Cursor res =  db.rawQuery("select "+COL_MED_ID
                    +" from "+TABLE_NAME+" WHERE "+COL_PRE_ID +" = "+pres_id+ " and " + COL_NAME + "='" + med_name+ "' ;",null);
            res.moveToFirst();
            int id = 0;
            while(res.isAfterLast() == false){
                id = res.getInt(res.getColumnIndex(COL_MED_ID));
                res.moveToNext();
            }
            return id;
        }

        public String get_med_name_for_id(int id){
            SQLiteDatabase db = obj_to_use.getReadableDatabase();
            Cursor res =  db.rawQuery("select * from " + TABLE_NAME + " where " + COL_MED_ID + "=" + id, null);
            res.moveToFirst();
            String name="";
            if(res.isAfterLast() == false){
                name = res.getString(res.getColumnIndex(COL_NAME));
            }
            return name;
        }

        public void decrement_dosage_count(int id){
            SQLiteDatabase db = obj_to_use.getWritableDatabase();
            db.execSQL("update " + TABLE_NAME + " set " + COL_DOSAGE_COUNT + "=" + COL_DOSAGE_COUNT + "-1  where " + COL_MED_ID + "=" + id);
            db.execSQL("delete from " + TABLE_NAME + " where " + COL_DOSAGE_COUNT + "=0");
        }
    }

    public class Alarm{
        public static final String TABLE_NAME = "ALARM";
        public static final String COL_ID = "ALARM_ID";
        public static final String COL_TIME = "TIME";
        public static final String COL_PERIOD = "PERIOD";
        public static final String COL_RECURRENCE_LEFT = "RECURRENCE_LEFT";
        public static final String COL_MEDICATION = "MEDICATION";
        public static final String COL_IS_INFINITE = "IS_INFINITE";
        public static final String COL_IS_ACTIVE = "IS_ACTIVE";

        public int get_new_alarm_id(){
            SQLiteDatabase db = obj_to_use.getReadableDatabase();
            Cursor res =  db.rawQuery("select * from " + TABLE_NAME,  null);
            res.moveToFirst();
            int id = 0;
            while(res.isAfterLast() == false){
                id = res.getInt(res.getColumnIndex(COL_ID));
            }
            return (id + 1);
        }
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