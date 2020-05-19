package com.example.walk_in_clinic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class RecyclerViewServicesEmployeeAdapter extends RecyclerView.Adapter<RecyclerViewServicesEmployeeAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewUserAdapter";
    private ArrayList<Service> services = new ArrayList<>();
    private Context mContext;

    public RecyclerViewServicesEmployeeAdapter(Context mContext,ArrayList<Service> services){
        this.services = services;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_service_view_employee, null,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.sName.setText(services.get(position).getServiceName());
        holder.sRole.setText(services.get(position).getOfferedBy());

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView sName;
        TextView sRole;
        RelativeLayout singleUserLayout;
        public ViewHolder(View serviceView){
            super(serviceView);
            sName = serviceView.findViewById(R.id.singleServiceNameEmployee);
            sRole = serviceView.findViewById(R.id.singleServiceRoleEmployee);
            singleUserLayout = serviceView.findViewById(R.id.singleServiceEmployeeLayout);
        }
    }
}
