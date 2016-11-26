package com.ashutosh.iiitd.mymedicine;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.DefaultItemAnimator;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.util.List;
import java.util.ArrayList;

public class Add_medicines_2 extends AppCompatActivity implements Interface_for_fragment{

    private List<Medicine> medList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Medicine_Adapter mAdapter;
    private final String KEY_FOR_NAME = "med_name";
    private final String KEY_FOR_TYPE = "med_type";
    private final String KEY_FOR_ALARM = "alarm_table_id";
    private static final int REQ_CODE = 0;
    private int flag_before_exit = 0;
    protected static DBHelper db;
    int medicine_table_id;
    boolean how_many_alarm_set[];
    int curr_id;
    TextView hospital,doctor;
    MedicineDialogFragment mdfrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicines_2);
        db = new DBHelper(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle from_details = getIntent().getExtras();
        hospital = (TextView)findViewById(R.id.tv_hospName);
        doctor = (TextView)findViewById(R.id.tv_docName);

        //INitialising the details of hospital and doctor

        String hosp_name = from_details.getString("hosp_name");
        String doc_name = from_details.getString("doc_name");
        hospital.setText(hosp_name);
        doctor.setText(doc_name);

        //Here goes the code for initialising the recycler view

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new Medicine_Adapter(medList, getBaseContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        int num = from_details.getInt("number_of_medicine");
        medicine_table_id = from_details.getInt("medicine_table_id");
        how_many_alarm_set = new boolean[num];
        for(boolean alarm:how_many_alarm_set){
            alarm = false;
        }
        //prepareMedData(num);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                int i;
                for(i = 0 ; i < how_many_alarm_set.length ; i++){
                    if(how_many_alarm_set[i] == false)
                        break;
                }
                if(i == how_many_alarm_set.length){
                    flag_before_exit = 1;
                }
                else{
                    flag_before_exit = 0;
                }
                onBackPressed();
            }
        });
    }

    /*void prepareMedData(int num){

        for(int i=0;i<num;i++){

            Medicine med = new Medicine("Medicine Number "+(i+1), "Alarm Number "+(i+1));
            medList.add(med);
        }
        mAdapter.notifyDataSetChanged();
    }*/

    public void sendMedicineDataForAlarm(View view){
        int id = view.getId();
        curr_id = id;
        EditText tb_name = (EditText)findViewById((1000 + id));
        Spinner sp_type = (Spinner)findViewById((2000 + id));
        if(!tb_name.getText().toString().equals("")) {
            int alarm_table_id = db.insert_data_into_medicine_details(medicine_table_id, (tb_name.getText().toString() + " " + sp_type.getSelectedItem().toString()));
            //Toast.makeText(this, (id), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Add_medicines_2.this, alarm_activity.class);
            //how_many_alarm_set[(id - 1)] = true;
            intent.putExtra(KEY_FOR_NAME, tb_name.getText().toString());
            intent.putExtra(KEY_FOR_TYPE, sp_type.getSelectedItem().toString());
            intent.putExtra(KEY_FOR_ALARM, alarm_table_id);
            startActivityForResult(intent, REQ_CODE);
            overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
        }
        else{
            Toast.makeText(getApplicationContext(), "Medicine Name cannot be left blank !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed(){
        if(flag_before_exit == 0)
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("The values are yet to save. Do you wish to exit now ?");

            alertDialogBuilder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    //Toast.makeText(Add_medicines_2.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                }
            });

            alertDialogBuilder.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.clear_state(medicine_table_id);
                    finish();
                    overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Data Saved !", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data )
    {
        if(resultcode == RESULT_OK){
            how_many_alarm_set[curr_id] = true;
        }
    }

    protected void click_to_add_medicine(View v){

        //here we call the dialog fragment and initialise data
        mdfrag = new MedicineDialogFragment();
        mdfrag.show(getSupportFragmentManager(),"dummyText");

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        return;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_fragment_medicine, null);

        EditText editText =  (EditText)dialog.getDialog().findViewById(R.id.project_name);
        Spinner sp_type = (Spinner)dialog.getDialog().findViewById(R.id.sp_type);
        EditText dosage = (EditText)dialog.getDialog().findViewById(R.id.et_dosage);
        String type = sp_type.getSelectedItem().toString();
        String dosage1 = dosage.getText().toString();
        String projectName = editText.getText().toString();
        if(!projectName.equals("")){
            Medicine med = new Medicine(projectName, type, dosage1);
            Toast.makeText(getApplicationContext(),projectName + type + dosage1,Toast.LENGTH_SHORT).show();
            medList.add(med);
            mAdapter.notifyDataSetChanged();
        }
        else
            onDialogNegativeClick(dialog);


    }

}