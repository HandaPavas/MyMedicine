package com.ashutosh.iiitd.mymedicine;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.DefaultItemAnimator;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;

import java.util.List;
import java.util.ArrayList;

public class Add_medicines_2 extends AppCompatActivity {

    private List<Medicine> medList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Medicine_Adapter mAdapter;
    private final String KEY_FOR_NAME = "med_name";
    private final String KEY_FOR_TYPE = "med_type";
    private int flag_before_exit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicines_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Here goes the code for initialising the recycler view

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new Medicine_Adapter(medList, getBaseContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        Bundle from_details = getIntent().getExtras();
        int num = from_details.getInt("number_of_medicine");
        prepareMedData(num);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    void prepareMedData(int num){

        for(int i=0;i<num;i++){

            Medicine med = new Medicine("Medicine Number "+(i+1), "Alarm Number "+(i+1));
            medList.add(med);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void sendMedicineDataForAlarm(View view){
        int id = view.getId();
        Intent intent = new Intent(Add_medicines_2.this,alarm_activity.class);
        EditText tb_name = (EditText)findViewById((1000 + id));
        Spinner sp_type = (Spinner)findViewById((2000 + id));
        //Toast.smakeText(this, ("Fuck off " + tb_name.getText().toString() + sp_type.getSelectedItem().toString()), Toast.LENGTH_SHORT).show();
        intent.putExtra(KEY_FOR_NAME,tb_name.getText().toString());
        intent.putExtra(KEY_FOR_TYPE,sp_type.getSelectedItem().toString());
        startActivity(intent);

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
                    Toast.makeText(Add_medicines_2.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                }
            });

            alertDialogBuilder.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }
}
