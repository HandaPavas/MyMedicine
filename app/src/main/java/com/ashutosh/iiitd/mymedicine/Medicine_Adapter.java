package com.ashutosh.iiitd.mymedicine;

/**
 * Created by Ashutosh on 19-10-2016.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
public class Medicine_Adapter extends RecyclerView.Adapter<Medicine_Adapter.MyViewHolder>{

    private List<Medicine> medList;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,dosage,type;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.tv_name);
            dosage = (TextView)view.findViewById(R.id.tv_dosage);
            type = (TextView)view.findViewById(R.id.tv_type);
        }
    }

    public Medicine_Adapter(List<Medicine> medList, Context context) {
        this.medList = medList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Medicine med = medList.get(position);
        //String hint_prior = holder.name.getHint().toString();
        holder.name.setText(med.getMed_name());
        holder.dosage.setText(med.getDosage());
        holder.type.setText(med.getType());
        /*holder.name.setId(position + 1000);
        holder.add_alarm.setText(med.getBtn_name());
        holder.add_alarm.setId(position);
        holder.type_spinner.setId(position + 2000);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,med.getType());

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.type_spinner.setAdapter(dataAdapter);*/
    }

    @Override
    public int getItemCount() {

        return medList.size();
    }

}
