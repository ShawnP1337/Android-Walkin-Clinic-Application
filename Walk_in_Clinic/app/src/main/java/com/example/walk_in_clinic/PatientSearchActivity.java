package com.example.walk_in_clinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class PatientSearchActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText search_address_text;
    private EditText search_working_hours;
    private EditText search_service;
    private Button search;
    private ArrayList<Clinic> clinics;
    ArrayList<Clinic> searchedClinics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_search);
        search_address_text = (EditText) findViewById(R.id.address_search);
        search_working_hours = (EditText) findViewById(R.id.hours_search);
        search_service = (EditText) findViewById(R.id.service_search);
        search = (Button) findViewById(R.id.search_button);
        search.setOnClickListener(this);
        clinics = DatabaseBrain.theDB.getClinicList();
        searchedClinics = new ArrayList<>();
        createRecyclerView();
    }

    @Override
    public void onClick(View v) {
        if(search.getId()==v.getId()){
            if(!search_address_text.getText().toString().trim().equals("") && !search_service.getText().toString().trim().equals("") && !search_working_hours.getText().toString().trim().equals("")){
                Toast.makeText(PatientSearchActivity.this,"Please fill only one search field",Toast.LENGTH_LONG).show();
            }
            else if(!search_address_text.getText().toString().trim().equals("")){
                String address_text = search_address_text.getText().toString().trim();
                searchedClinics=new ArrayList<>();
                for (Clinic c: clinics){
                    if(address_text.equals(c.getClinicAddress().toLowerCase())){
                        searchedClinics.add(c);
                    }
                }
                createRecyclerView();
            }
            else if(!search_working_hours.getText().toString().trim().equals("")){
                String hours_text = search_working_hours.getText().toString().trim();
                searchedClinics = new ArrayList<>();
                System.out.println(clinics.get(0).getWorkingHours());
                for (Clinic c: clinics){
                    ArrayList<String> hours = c.getWorkingHours();
                    for (String h: hours){
                        if(hours_text.equals(h) && !searchedClinics.contains(c)){
                            searchedClinics.add(c);
                        }
                    }
                }
                createRecyclerView();
            }
            else if(!search_service.getText().toString().trim().equals("")){
                String service = search_service.getText().toString().trim();
                Service searchedService = null;
                if(DatabaseBrain.theDB.getService(service.toLowerCase())!=null) {
                    searchedService = DatabaseBrain.theDB.getService(service.toLowerCase());
                    searchedClinics = new ArrayList<>();
                    for (Clinic c : clinics) {
                        ArrayList<Service> services = c.getClinicServices();
                        System.out.println(services);
                        for (Service s : services) {
                            if (searchedService.equals(s) && !searchedClinics.contains(c)) {
                                searchedClinics.add(c);
                            }
                        }
                    }
                }else{
                    Toast.makeText(PatientSearchActivity.this,"Service is not being offered by any clinic",Toast.LENGTH_LONG).show();
                }
                createRecyclerView();
            }
            else if(search_address_text.getText().toString().trim().equals("") && search_service.getText().toString().trim().equals("") && search_working_hours.getText().toString().trim().equals("")){
                Toast.makeText(PatientSearchActivity.this,"Please fill one search field",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void createRecyclerView(){
        RecyclerView recyclerView1 = findViewById(R.id.recycler_view_clinic_search);
        RecyclerViewPatientSearch adapter1 = new RecyclerViewPatientSearch(this,searchedClinics);
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
    }
}
