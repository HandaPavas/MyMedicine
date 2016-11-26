package com.ashutosh.iiitd.mymedicine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_for_details extends AppCompatActivity {

    protected Button mButton_for_redirect;
    private final static String KEY_NUMBERS = "number_of_medicine";
    private final static String KEY_TAB_ID = "medicine_table_id";
    private final static String KEY_FOR_NAME = "doc_name";
    private final static String KEY_FOR_HOSP = "hosp_name";
    TextView numbers;
    TextView doc_name;
    TextView hosp_name;
    int num = 0;
    String hosp_name_str = "", doc_name_str="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_details);
        mButton_for_redirect = (Button)findViewById(R.id.button_for_redirect);
        mButton_for_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numbers = (TextView)findViewById(R.id.input_number);
                doc_name = (TextView)findViewById(R.id.input_doc_name);
                hosp_name = (TextView)findViewById(R.id.input_hospital);
                if(!(doc_name.getText().toString().equals("") || hosp_name.getText().toString().equals("") ||
                        numbers.getText().toString().equals(""))) {
                    try {
                        num = Integer.parseInt(numbers.getText().toString());
                        hosp_name_str = hosp_name.getText().toString();
                        doc_name_str = doc_name.getText().toString();
                    } catch (Exception ex) {

                    }
                    DBHelper db = new DBHelper(getApplicationContext());
                    int id = db.insert_data_into_doctor_details(doc_name.getText().toString(), hosp_name.getText().toString());
                    Intent intent_for_adding = new Intent(Activity_for_details.this, Add_medicines_2.class);
                    intent_for_adding.putExtra(KEY_NUMBERS, num);
                    intent_for_adding.putExtra(KEY_TAB_ID, id);
                    intent_for_adding.putExtra(KEY_FOR_NAME,doc_name_str);
                    intent_for_adding.putExtra(KEY_FOR_HOSP,hosp_name_str);
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
    }

    @Override
    public void onBackPressed(){

        finish();
        overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
    }
}
