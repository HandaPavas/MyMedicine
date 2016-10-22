package com.ashutosh.iiitd.mymedicine;

/**
 * Created by Ashutosh on 21-10-2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "MyMedicineDB";

    class Doctor_details{
        public static final String TABLE_NAME = "Doctor_details";
        public static final String COLUMN_DOCTOR = "Doctor";
        public static final String COLUMN_HOSPITAL = "Hospital";
        public static final String COLUMN_DATE = "Date";
        public static final String COLUMN_MEDICINE_TABLE_DATA = "Medicine_table_id";
    }

    class Medicine_details{
        public static final String TABLE_NAME = "Medicine_details";
        public static final String COLUMN_MED_TABLE_ID = "Medicine_table_id";
        public static final String COLUMN_MEDICINE_NAME = "Medicine_name";
        public static final String COLUMN_ALARM_TABLE_ID = "Alarm_table_id";
    }

    class Alarm_details{
        public static final String TABLE_NAME = "Alarm_details";
        public static final String COLUMN_ALARM_TABLE_ID = "Alarm_table_id";
    }

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // cerate the Doctor_details table
        Doctor_details doc = new Doctor_details();
        db.execSQL("create table " + doc.TABLE_NAME + "( " +
                doc.COLUMN_DOCTOR + " varchar(255), " +
                doc.COLUMN_HOSPITAL + " varchar(255)," +
                doc.COLUMN_DATE + " DATETIME," +
                doc.COLUMN_MEDICINE_TABLE_DATA + " INTEGER  PRIMARY KEY AUTOINCREMENT" +
                ");"
        );

        //create the medicine_details table
        Medicine_details md = new Medicine_details();
        db.execSQL("create table " + md.TABLE_NAME + "( " +
                md.COLUMN_MED_TABLE_ID + " INTEGER, " +
                md.COLUMN_MEDICINE_NAME + " varchar(255)," +
                md.COLUMN_ALARM_TABLE_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT" +
                ");"
        );

        //create table Alarm_details
        Alarm_details ad = new Alarm_details();
        db.execSQL("create table " + ad.TABLE_NAME + "(" +
                ad.COLUMN_ALARM_TABLE_ID + " INTEGER" +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop doctor_details
        Doctor_details doc = new Doctor_details();
        db.execSQL("DROP TABLE IF EXISTS " + doc.TABLE_NAME);
        //drop medicine_details
        Medicine_details md = new Medicine_details();
        db.execSQL("DROP TABLE IF EXISTS " + md.TABLE_NAME);
        //drop table Alarm_details
        Alarm_details ad = new Alarm_details();
        db.execSQL("DROP TABLE IF EXISTS " + ad.TABLE_NAME);

        onCreate(db);
    }

    public int insert_data_into_doctor_details(String doctor_name, String hospital_name){
        Doctor_details dd = new Doctor_details();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        int ret = 0;
        String d = dateFormat.format(date);

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                "insert into " + dd.TABLE_NAME + "(" +
                        dd.COLUMN_DOCTOR + ", " +
                        dd.COLUMN_HOSPITAL+ ", "+
                        dd.COLUMN_DATE +
                        ") values('" +
                        doctor_name + "', '" +
                        hospital_name + "', '" +
                        d + "');"
        );

        db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select " + dd.COLUMN_MEDICINE_TABLE_DATA +" from " + dd.TABLE_NAME + " order by " + dd.COLUMN_MEDICINE_TABLE_DATA + " desc limit 1", null);
        result.moveToFirst();
        if(result.isAfterLast() == false)
            ret = result.getInt(result.getColumnIndex(dd.COLUMN_MEDICINE_TABLE_DATA));
        return ret;
    }

    public int insert_data_into_medicine_details(int id, String med_name){

        Medicine_details md = new Medicine_details();
        int ret = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                "insert into " + md.TABLE_NAME + "("+
                        md.COLUMN_MED_TABLE_ID + ", " +
                        md.COLUMN_MEDICINE_NAME + ") " +
                        "values(" +
                        id + ", '"+
                        med_name + "');"
        );
        db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select " + md.COLUMN_ALARM_TABLE_ID +" from " + md.TABLE_NAME + " order by " + md.COLUMN_ALARM_TABLE_ID + " desc limit 1", null);
        result.moveToFirst();
        if(result.isAfterLast() == false)
            ret = result.getInt(result.getColumnIndex(md.COLUMN_ALARM_TABLE_ID));
        return ret;
    }
    public void clear_state(int id){
        //got medicine table id
        //now clear all the three tables of the data
        Doctor_details dd = new Doctor_details();
        Medicine_details md = new Medicine_details();
        Alarm_details ad = new Alarm_details();
        SQLiteDatabase db_w = this.getWritableDatabase();
        SQLiteDatabase db_r = this.getReadableDatabase();

        String query = "delete from " + dd.TABLE_NAME + " where " + dd.COLUMN_MEDICINE_TABLE_DATA + "=" + id;
        db_w.execSQL(query);

        query = "select " + md.COLUMN_ALARM_TABLE_ID + " from " + md.TABLE_NAME + " where " + md.COLUMN_MED_TABLE_ID + "=" + id;
        Cursor cursor = db_r.rawQuery(query, null);
        cursor.moveToFirst();
        String entries_to_remove = "in(";
        while(cursor.isAfterLast() == false){
            entries_to_remove += cursor.getString(cursor.getColumnIndex(md.COLUMN_ALARM_TABLE_ID));
            entries_to_remove +=", ";
            cursor.moveToNext();
        }
        if(entries_to_remove.length() > 3)
            entries_to_remove = entries_to_remove.substring(0, (entries_to_remove.lastIndexOf(',') - 1));
        entries_to_remove += ")";

        query = "delete from " + md.TABLE_NAME + " where " + md.COLUMN_MED_TABLE_ID + "=" + id;
        db_w.execSQL(query);

        query = "delete from " + ad.TABLE_NAME + " where " + ad.COLUMN_ALARM_TABLE_ID + " " + entries_to_remove +";";
        db_w.execSQL(query);


    }
}