package com.ashutosh.iiitd.mymedicine;

/**
 * Created by Ashutosh on 19-10-2016.
 */
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
public class Medicine_Adapter extends RecyclerView.Adapter<Medicine_Adapter.MyViewHolder>{

    private List<Medicine> medList;
    Context context;
    int status;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,dosage,type,dosage_count;
        public CheckBox cb_alarm;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.tv_name);
            dosage = (TextView)view.findViewById(R.id.tv_dosage);
            type = (TextView)view.findViewById(R.id.tv_type);
            dosage_count = (TextView)view.findViewById(R.id.tv_dosage_count);
            cb_alarm = (CheckBox)view.findViewById(R.id.cb_alarm);
        }
    }

    public Medicine_Adapter(List<Medicine> medList, Context context, int k) {
        this.medList = medList;
        this.context = context;
        this.status = k;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Medicine med = medList.get(position);
        //String hint_prior = holder.name.getHint().toString();
        if(med.getFlag()==0){
            holder.name.setText(med.getMed_name());
            holder.dosage.setText(med.getDosage());
            holder.type.setText(med.getType());
            holder.dosage_count.setText(med.getDosage_count()+"");
            if(position<status){
                holder.cb_alarm.setVisibility(View.GONE);
            }
            else
            {
                holder.cb_alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        if ( isChecked ) {
                            Add_medicines_2.getInstance().toggle_fab_visible();
                            med.setFlag(1);

                        }
                        else{
                            Add_medicines_2.getInstance().toggle_fab_gone();
                            med.setFlag(0);
                        }

                    }
                });
            }

        }


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
    /*
    Code for support of swipe
     */
    public boolean isPendingRemoval(int position) {
        //String[] item = medList.get(position);
        return false;
    }

    public void pendingRemoval(int position) {
        /*final String[] item = medList.get(position);
        if (!itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.add(item);
            // this will redraw row in "undo" state
            notifyItemChanged(position);
            // let's create, store and post a runnable to remove the item
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    remove(data_list.indexOf(item));
                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(item, pendingRemovalRunnable);
        }*/
    }

    public void remove(int position) {
        Medicine item = medList.get(position);
        if (medList.contains(item)) {
            medList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public boolean isUndoOn(){
        return false;
    }

}
