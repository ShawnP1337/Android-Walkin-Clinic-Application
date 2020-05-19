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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

class RecyclerViewPatientSearch extends RecyclerView.Adapter<RecyclerViewPatientSearch.ViewHolder>{
    private static final String TAG = "RecyclerPatientSearch";
    private ArrayList<Clinic> clinics = new ArrayList<>();
    private Context mContext;

    public RecyclerViewPatientSearch(Context mContext,ArrayList<Clinic> clinics){
        this.clinics = clinics;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_clinic_search, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.cName.setText(clinics.get(position).getClinicName());
        holder.cRating.setText(clinics.get(position).getRating().toString() +" / 5");
        holder.singleClinicLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClinicPage.class);
                intent.putExtra("CLINIC NAME", clinics.get(position).getClinicName());
                intent.putExtra("CLINIC RATING", clinics.get(position).getRating().toString());
                intent.putExtra("CLINIC ADDRESS", clinics.get(position).getClinicAddress());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return clinics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView cName;
        TextView cRating;
        RelativeLayout singleClinicLayout;
        public ViewHolder(View clinicView){
            super(clinicView);
            cName = clinicView.findViewById(R.id.singleClinicName);
            cName.setPaintFlags(cName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            cRating = clinicView.findViewById(R.id.clinic_rating);
            singleClinicLayout = clinicView.findViewById(R.id.singleClinicLayout);

    }
}
}
