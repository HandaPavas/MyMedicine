package com.ashutosh.iiitd.mymedicine;

/**
 * Created by Ashutosh on 28-11-2016.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import helperClasses.Prescription_obj;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.MyViewHolder> {

    private Context mContext;
    private List<Prescription_obj> presList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView doc, hosp;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            doc = (TextView) view.findViewById(R.id.tv_doc_name);
            hosp = (TextView) view.findViewById(R.id.tv_hosp_name);
            //thumbnail = (ImageView) view.findViewById(R.id.thumbnail_pres);
        }
    }

    public PrescriptionAdapter(Context mContext, List<Prescription_obj> presList) {
        this.mContext = mContext;
        this.presList = presList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prescription_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Prescription_obj obj = presList.get(position);
        holder.doc.setText(obj.getDoc_name());
        holder.hosp.setText(obj.getHosp_name());
        // loading album cover using Glide library
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return presList.size();
    }

}
