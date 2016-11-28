package com.ashutosh.iiitd.mymedicine;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import helperClasses.DBHelper;
import helperClasses.Prescription_obj;

public class Activity_for_medicine extends AppCompatActivity implements Interface_for_fragment{

    MedicineDialogFragment mdfrag;
    private List<Medicine> medList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Medicine_Adapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_medicine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle from_view_pres = getIntent().getExtras();
        Prescription_obj to_display = (Prescription_obj) from_view_pres.getSerializable("PRES_OBJ");
        TextView tv_doc = (TextView)findViewById(R.id.tv_docName);
        TextView tv_hosp = (TextView)findViewById(R.id.tv_hospName);
        tv_doc.setText(to_display.getDoc_name());
        tv_hosp.setText(to_display.getHosp_name());

        //Recycler view Initialisation
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new Medicine_Adapter(medList, getBaseContext(),1);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareMedData(to_display.getId());
    }

    /*
    Preparing data fr recycler view
     */
    public void prepareMedData(int id){

        DBHelper db = new DBHelper(getApplicationContext());
        DBHelper.Medicine_alarm obj = db.new Medicine_alarm();
        medList = obj.retrieve_data(id);
        mAdapter = new Medicine_Adapter(medList, this,3);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
    /*
    Code for add medicine fragment goes here
     */
    protected void click_to_add_medicine(View v){

        //here we call the dialog fragment and initialise data
        mdfrag = new MedicineDialogFragment();
        mdfrag.show(getSupportFragmentManager(),"dummyText");

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog){
        return;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_fragment_medicine, null);

        EditText editText =  (EditText)dialog.getDialog().findViewById(R.id.project_name);
        Spinner sp_type = (Spinner)dialog.getDialog().findViewById(R.id.sp_type);
        EditText dosage = (EditText)dialog.getDialog().findViewById(R.id.et_dosage);
        EditText dosage_count = (EditText)dialog.getDialog().findViewById(R.id.et_dosage_count);
        String type = sp_type.getSelectedItem().toString();
        String dosage1 = dosage.getText().toString();
        String projectName = editText.getText().toString();
        int dsg_cnt = Integer.parseInt(dosage_count.getText().toString());
        if(!projectName.equals("")){
            Medicine med = new Medicine(projectName, type, dosage1,dsg_cnt);
            Toast.makeText(getApplicationContext(),projectName + type + dosage1,Toast.LENGTH_SHORT).show();
            medList.add(med);
            mAdapter.notifyDataSetChanged();
        }
        else
            onDialogNegativeClick(dialog);


    }

}
