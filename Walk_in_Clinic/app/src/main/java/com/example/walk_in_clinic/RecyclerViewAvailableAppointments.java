package com.example.walk_in_clinic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

class RecyclerViewAvailableAppointments extends RecyclerView.Adapter<RecyclerViewAvailableAppointments.ViewHolder>{
    private static final String TAG = "RecyclerPatientSearch";
    private ArrayList<Appointment> avAppointments = new ArrayList<>();
    private Context mContext;
    private String cName;

    public RecyclerViewAvailableAppointments(Context mContext,ArrayList<Appointment> avAppointments,String cName){
        this.avAppointments = avAppointments;
        this.mContext = mContext;
        this.cName=cName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_appointment, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.appDay.setText(avAppointments.get(position).getWeekDay());
        holder.appTime.setText(avAppointments.get(position).getAppTime());
        holder.singleAppointmentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseBrain.theDB.updateAppointments(cName,avAppointments.get(position).getWeekDay(),avAppointments.get(position).getAppTime());
                Toast.makeText(mContext,"Appointment has been booked",Toast.LENGTH_LONG).show();
                notifyItemRemoved(position);
                notifyItemRangeChanged(0,avAppointments.size());

            }
        });

    }

    @Override
    public int getItemCount() {
        return avAppointments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView appDay;
        TextView appTime;
        RelativeLayout singleAppointmentLayout;
        public ViewHolder(View clinicView){
            super(clinicView);
            appDay = clinicView.findViewById(R.id.app_day);
            appDay.setPaintFlags(appDay.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            appTime = clinicView.findViewById(R.id.app_time);
            singleAppointmentLayout = clinicView.findViewById(R.id.singleAppointmentLayout);

        }
    }
}
