package com.ashutosh.iiitd.mymedicine;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Ashutosh on 20-11-2016.
 */

public class MedicineDialogFragment extends DialogFragment {

    private Interface_for_fragment interface_for_fragment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder createProjectAlert = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        createProjectAlert.setTitle("Add Details");
        View view = inflater.inflate(R.layout.dialog_fragment_medicine, null);

        Spinner spin;
        spin = (Spinner)view.findViewById(R.id.sp_type);

        ArrayList<String> list = new ArrayList<String>();
        list.add("Tablet");
        list.add("Syrup");
        list.add("Others");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(dataAdapter);


        createProjectAlert.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        interface_for_fragment.onDialogPositiveClick(MedicineDialogFragment.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        interface_for_fragment.onDialogNegativeClick(MedicineDialogFragment.this);

                    }
                });

        return createProjectAlert.create();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        interface_for_fragment = (Interface_for_fragment) activity;
    }

}


