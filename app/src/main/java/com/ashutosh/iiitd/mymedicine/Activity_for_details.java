package com.ashutosh.iiitd.mymedicine;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import helperClasses.DBHelper;

public class Activity_for_details extends AppCompatActivity {

    protected Button mButton_for_redirect;
    private final static String KEY_NUMBERS = "number_of_medicine";
    private final static String KEY_TAB_ID = "medicine_table_id";
    private final static String KEY_FOR_NAME = "doc_name";
    private final static String KEY_FOR_HOSP = "hosp_name";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    TextView date;
    TextView doc_name;
    TextView hosp_name;
    ImageView iv_pres;
    String hosp_name_str = "", doc_name_str="",date_str="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_details);
        date = (TextView)findViewById(R.id.tv_date_prescription);
        mButton_for_redirect = (Button)findViewById(R.id.button_for_redirect);
        mButton_for_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = (TextView)findViewById(R.id.tv_date_prescription);
                doc_name = (TextView)findViewById(R.id.input_doc_name);
                hosp_name = (TextView)findViewById(R.id.input_hospital);
                if(!(doc_name.getText().toString().equals("") || hosp_name.getText().toString().equals("") ||
                        date.getText().toString().equals(""))) {
                    try {
                        date_str = date.getText().toString();
                        hosp_name_str = hosp_name.getText().toString();
                        doc_name_str = doc_name.getText().toString();
                    } catch (Exception ex) {

                    }
                    DBHelper db = new DBHelper(getApplicationContext());
                    DBHelper.Prescription obj_pres = db.new Prescription();
                    int presc_id = obj_pres.insert_data(doc_name_str,hosp_name_str,date_str);
                    Intent intent_for_adding = new Intent(Activity_for_details.this, Add_medicines_2.class);
                    //intent_for_adding.putExtra(KEY_NUMBERS, num);
                    //intent_for_adding.putExtra(KEY_TAB_ID, id);
                    intent_for_adding.putExtra(KEY_FOR_NAME,doc_name_str);
                    intent_for_adding.putExtra(KEY_FOR_HOSP,hosp_name_str);
                    intent_for_adding.putExtra("KEY_FOR_PRES_ID",presc_id);
                    intent_for_adding.putExtra("KEY_FOR_APP",1);
                    //Toast.makeText(getApplicationContext(), "Inserted " + id, Toast.LENGTH_SHORT).show();
                    startActivity(intent_for_adding);
                    overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Enter data in all the fields !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btn_pres = (Button)findViewById(R.id.btn_pres);
        btn_pres.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                iv_pres = (ImageView)findViewById(R.id.iv_pres);
                take_image_and_display();
            }
        });
    }

    //date picker fragment

    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        date.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    @Override
    public void onBackPressed(){

        finish();
        overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
    }

    public void take_image_and_display(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 1000, bytes);
            File folder= new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"MyMedicine");
            folder.mkdirs();
            DBHelper db = new DBHelper(getApplicationContext());
            DBHelper.Prescription obj_pres = db.new Prescription();
            File f = new File(folder.getAbsolutePath(), (obj_pres.get_new_image_name() + ".jpg"));
            try {
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                //5
                fo.write(bytes.toByteArray());
                fo.close();
                iv_pres.setImageBitmap(imageBitmap);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                Toast.makeText(getApplicationContext(), "The Image can't be updated now. Try later !", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
