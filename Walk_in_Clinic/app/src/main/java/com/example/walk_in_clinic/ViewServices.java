package com.example.walk_in_clinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ViewServices extends AppCompatActivity {

    private ArrayList<Service> services = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_services);
        createServicesList();
    }

    private void createServicesList(){
        services = DatabaseBrain.theDB.serviceList();
        createRecyclerView();
    }

    private void createRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_services);
        RecyclerViewServicesAdapter adapter = new RecyclerViewServicesAdapter(this,services);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

