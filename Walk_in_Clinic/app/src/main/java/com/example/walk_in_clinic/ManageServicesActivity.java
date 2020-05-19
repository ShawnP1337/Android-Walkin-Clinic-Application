package com.example.walk_in_clinic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ManageServicesActivity extends AppCompatActivity implements View.OnClickListener {

    //Buttons
    public Button add_b;
    public Button edit_b;
    public Button delete_b;
    public Button viewServices;
    public RadioGroup role_group;
    public RadioButton doctor_rb;
    public RadioButton nurse_rb;
    public RadioButton staff_rb;

    //EditTexts
    public EditText service_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_services);
        add_b = (Button) findViewById(R.id.add);
        edit_b = (Button) findViewById(R.id.edit);
        delete_b = (Button) findViewById(R.id.delete);
        viewServices = (Button) findViewById(R.id.ViewServices);
        role_group = (RadioGroup) findViewById(R.id.radio_group_role);
        doctor_rb = (RadioButton) findViewById(R.id.doctor);
        nurse_rb = (RadioButton) findViewById(R.id.nurse);
        staff_rb = (RadioButton) findViewById(R.id.staff);
        service_name = (EditText) findViewById(R.id.enter_service_name);


        //setting OnClick Listeners
        add_b.setOnClickListener(this);
        edit_b.setOnClickListener(this);
        delete_b.setOnClickListener(this);
        viewServices.setOnClickListener(this);

    }
    public boolean checkFields(){
        if(service_name.getText().toString().trim().equals("")){
            Toast.makeText(ManageServicesActivity.this, "Please enter a Service name",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(role_group.getCheckedRadioButtonId()==-1){
            Toast.makeText(ManageServicesActivity.this, "Please choose a role",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private void resetFields(){
        service_name.setText("");
        service_name.setHint("Service Name");
        role_group.clearCheck();
    }
    private void editService(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_service, null);
        final  EditText new_sname = (EditText) dialogView.findViewById(R.id.editServiceName);
        final RadioGroup new_role_group = (RadioGroup) dialogView.findViewById(R.id.editRoleService);
        final RadioButton new_role_doctor = (RadioButton) dialogView.findViewById(R.id.doctor);
        final RadioButton new_role_nurse = (RadioButton) dialogView.findViewById(R.id.nurse);
        final RadioButton new_role_staff = (RadioButton) dialogView.findViewById(R.id.staff);
        final Button edit = (Button) dialogView.findViewById(R.id.confirmEditService);

        if(!service_name.getText().toString().trim().equals("") && DatabaseBrain.theDB.hasService(service_name.getText().toString().trim())){
            dialogBuilder.setView(dialogView);
            final AlertDialog b = dialogBuilder.create();
            b.show();
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!new_sname.getText().toString().trim().equals("") && new_role_group.getCheckedRadioButtonId()!=-1){
                        if(service_name.getText().toString().trim().equals(new_sname.getText().toString().trim()) || !DatabaseBrain.theDB.hasService(new_sname.getText().toString().trim())){
                            if(new_role_doctor.getId()==new_role_group.getCheckedRadioButtonId()){
                                DatabaseBrain.theDB.updateService(service_name.getText().toString().trim(),new_sname.getText().toString().trim(),"Doctor");
                                b.dismiss();
                                Toast.makeText(ManageServicesActivity.this, "Service Updated!",Toast.LENGTH_LONG).show();
                                resetFields();
                            }
                            else if(new_role_nurse.getId()==new_role_group.getCheckedRadioButtonId()){
                                DatabaseBrain.theDB.updateService(service_name.getText().toString().trim(),new_sname.getText().toString().trim(),"Nurse");
                                b.dismiss();
                                Toast.makeText(ManageServicesActivity.this, "Service Updated!",Toast.LENGTH_LONG).show();
                                resetFields();
                            }
                            else{
                                DatabaseBrain.theDB.updateService(service_name.getText().toString().trim(),new_sname.getText().toString().trim(),"Staff");
                                b.dismiss();
                                Toast.makeText(ManageServicesActivity.this, "Service Updated!",Toast.LENGTH_LONG).show();
                                resetFields();
                            }
                        }else{
                            Toast.makeText(ManageServicesActivity.this, "Service name already exists",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(ManageServicesActivity.this, "Please fill all fields",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else if(service_name.getText().toString().trim().equals("")){
            Toast.makeText(ManageServicesActivity.this, "Please enter a Service name",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(ManageServicesActivity.this, "Service does NOT exist",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View v) {
        boolean added=false;
        if(add_b.getId()==v.getId() && checkFields()){
            if(!DatabaseBrain.theDB.hasService(service_name.getText().toString().trim())){
                if(doctor_rb.getId()==role_group.getCheckedRadioButtonId()) {
                    try {
                        added = DatabaseBrain.theDB.addService(service_name.getText().toString().trim(), "Doctor");
                    }catch(Exception e){
                        Log.d("ERROR", "ERROR WITH ADDING SERVICE ");
                    }
                    if(added){
                        Toast.makeText(ManageServicesActivity.this, "Doctor Service Added!",Toast.LENGTH_LONG).show();
                        resetFields();
                    }
                }
                else if(nurse_rb.getId()==role_group.getCheckedRadioButtonId()) {
                    try {
                        added = DatabaseBrain.theDB.addService(service_name.getText().toString().trim(), "Nurse");
                    }catch(Exception e){
                        Log.d("ERROR", "ERROR WITH ADDING SERVICE ");
                    }
                    if(added){
                        Toast.makeText(ManageServicesActivity.this, "Nurse Service Added!",Toast.LENGTH_LONG).show();
                        resetFields();
                    }                }
                else if(staff_rb.getId()==role_group.getCheckedRadioButtonId()) {
                    try {
                        added = DatabaseBrain.theDB.addService(service_name.getText().toString().trim(), "Staff");
                    }catch(Exception e){
                        Log.d("ERROR", "ERROR WITH ADDING SERVICE ");
                    }
                    if(added){
                        Toast.makeText(ManageServicesActivity.this, "Staff Service Added!",Toast.LENGTH_LONG).show();
                        resetFields();
                    }                }
            } else {
                Toast.makeText(ManageServicesActivity.this, "Service already exists",Toast.LENGTH_LONG).show();
            }
        }
        else if(edit_b.getId()==v.getId()){
            editService();
        }
        else if(delete_b.getId()==v.getId()){
            if(DatabaseBrain.theDB.deleteService(service_name.getText().toString().trim())){
                Toast.makeText(ManageServicesActivity.this,"Service Deleted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(ManageServicesActivity.this,"Service Does Not Exist", Toast.LENGTH_SHORT).show();
            }
        }
        else if(viewServices.getId()==v.getId()){
            Intent intent = new Intent(getApplicationContext(),ViewServices.class);
            startActivity(intent);
        }
    }
}
