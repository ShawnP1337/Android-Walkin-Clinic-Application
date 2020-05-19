package com.example.walk_in_clinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class BookAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Appointment> hours = new ArrayList<>();
    private String clinicName;
    private Button refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        refresh = (Button)findViewById(R.id.refresh);

        //get clinic name
        Intent intent = getIntent();
        clinicName = intent.getStringExtra("CLINIC_NAME");
        createAppointmentPermutation();

        refresh.setOnClickListener(this);
    }

    private void createAppointmentPermutation(){
        hours = DatabaseBrain.theDB.getAppointmentsList(clinicName);
        createRecyclerView();
    }

    private void createRecyclerView(){
        RecyclerView recyclerView1 = findViewById(R.id.recycler_view_available_appointments);
        RecyclerViewAvailableAppointments adapter1 = new RecyclerViewAvailableAppointments(this,hours,clinicName);
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        if(refresh.getId()==v.getId()){
            createAppointmentPermutation();
        }
    }
}
