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

class RecyclerViewUserAdapter extends RecyclerView.Adapter<RecyclerViewUserAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewUserAdapter";
    private ArrayList<User> users = new ArrayList<>();
    private Context mContext;

    public RecyclerViewUserAdapter(Context mContext,ArrayList<User> users){
        this.users = users;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int i=0;
        Log.d(TAG, "onCreateViewHolder: called" + i);
        i++;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_view, null,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.userName.setText(users.get(position).getUsername());
        holder.userRole.setText(users.get(position).getRole());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView userRole;
        RelativeLayout singleUserLayout;
        public ViewHolder(View userView){
            super(userView);
            userName = userView.findViewById(R.id.singleUserName);
            userRole = userView.findViewById(R.id.singleUserRole);
            singleUserLayout = userView.findViewById(R.id.singleUserLayout);
        }
    }
}
