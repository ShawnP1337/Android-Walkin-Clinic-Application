package com.example.walk_in_clinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class ManageServicesEmployee extends AppCompatActivity implements View.OnClickListener {
    private String u_name;
    private Button addService;
    private Button deleteService;
    private ArrayList<Service> services_list_emp = new ArrayList<>();
    private ArrayList<Service> services_list_admin = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_services_employee);
        addService = (Button) findViewById(R.id.AddServiceEmployeeButton);
        deleteService = (Button) findViewById(R.id.DeleteServiceEmployeeButton);
        //extracting string from previous activity
        Intent u_name_extract = getIntent();
        u_name = u_name_extract.getStringExtra("USERNAME");
        addService.setOnClickListener(this);
        deleteService.setOnClickListener(this);
        createServicesList();
    }
    private void createServicesList(){
        services_list_emp = DatabaseBrain.theDB.getServicesListEmployee(DatabaseBrain.theDB.getClinicName(u_name));
        services_list_admin = DatabaseBrain.theDB.serviceList();
        createRecyclerView();
    }

    private void createRecyclerView(){
        RecyclerView recyclerView1 = findViewById(R.id.recycler_view_available_services);
        RecyclerView recyclerView2 = findViewById(R.id.recycler_view_added_services);
        RecyclerViewServicesEmployeeAdapter adapter1 = new RecyclerViewServicesEmployeeAdapter(this,services_list_admin);
        RecyclerViewServicesEmployeeAdapter adapter2 = new RecyclerViewServicesEmployeeAdapter(this,services_list_emp);
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        if(addService.getId() == v.getId()){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.add_service_employee, null);
            final EditText new_added_service = (EditText) dialogView.findViewById(R.id.AddServiceName);
            final Button confirm_add_service = (Button) dialogView.findViewById(R.id.confirmAddService);
            dialogBuilder.setView(dialogView);
            final AlertDialog b = dialogBuilder.create();
            b.show();
            confirm_add_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!new_added_service.getText().toString().trim().equals("")){
                        if(DatabaseBrain.theDB.hasService(new_added_service.getText().toString().trim())){
                            if(!services_list_emp.contains(DatabaseBrain.theDB.getService(new_added_service.getText().toString().trim()))) {
                                boolean inserted = DatabaseBrain.theDB.addServiceEmployee(u_name, new_added_service.getText().toString().trim()) && DatabaseBrain.theDB.addClinicInformation(u_name);
                                if (inserted) {
                                    createServicesList();
                                    createRecyclerView();
                                    b.dismiss();
                                    Toast.makeText(ManageServicesEmployee.this, "Service Added!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ManageServicesEmployee.this, "Service was NOT Added!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ManageServicesEmployee.this, "Service has already been added", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ManageServicesEmployee.this,"Service Does Not Exist",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ManageServicesEmployee.this,"Please Enter a Service Name",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else if(deleteService.getId() == v.getId()){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.delete_service_employee, null);
            final EditText new_deleted_service = (EditText) dialogView.findViewById(R.id.DeleteServiceName);
            final Button confirm_deleted_service = (Button) dialogView.findViewById(R.id.confirmDeleteService);
            dialogBuilder.setView(dialogView);
            final AlertDialog b = dialogBuilder.create();
            b.show();
            confirm_deleted_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!new_deleted_service.getText().toString().trim().equals("")){
                        if(DatabaseBrain.theDB.hasService(new_deleted_service.getText().toString().trim())){
                            boolean inserted = DatabaseBrain.theDB.deleteServiceEmployee(u_name,new_deleted_service.getText().toString().trim()) && DatabaseBrain.theDB.deleteClinicService(DatabaseBrain.theDB.getClinicName(u_name),new_deleted_service.getText().toString().trim());
                            if(inserted){
                                createServicesList();
                                createRecyclerView();
                                b.dismiss();
                                Toast.makeText(ManageServicesEmployee.this,"Service Deleted!",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ManageServicesEmployee.this,"Service was NOT Deleted!",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ManageServicesEmployee.this,"Service Does Not Exist",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ManageServicesEmployee.this,"Please Enter a Service Name",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
