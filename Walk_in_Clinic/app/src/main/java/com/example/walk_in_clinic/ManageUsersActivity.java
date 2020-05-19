package com.example.walk_in_clinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class ManageUsersActivity extends AppCompatActivity implements View.OnClickListener {

    //Buttons
    private Button add_b;
    private Button edit_b;
    private Button delete_b;
    private RadioGroup role_group;
    private RadioButton employee_rb;
    private RadioButton patient_rb;
    private Button viewUsers;

    //EditTexts
    private EditText username;
    private EditText password;
    private EditText confirm_password;
    private EditText e_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);
        add_b = (Button) findViewById(R.id.add);
        edit_b = (Button) findViewById(R.id.edit);
        delete_b = (Button) findViewById(R.id.delete);
        viewUsers = (Button) findViewById(R.id.ViewUsers);
        role_group = (RadioGroup) findViewById(R.id.radio_group_userrole);
        employee_rb = (RadioButton) findViewById(R.id.manage_employee);
        patient_rb = (RadioButton) findViewById(R.id.manage_patient);
        username = (EditText) findViewById(R.id.manage_username);
        password = (EditText) findViewById(R.id.manage_password);
        confirm_password = (EditText) findViewById(R.id.manage_confirm_password);
        e_address = (EditText) findViewById(R.id.manage_email);

        //setting OnClick Listeners
        add_b.setOnClickListener(this);
        edit_b.setOnClickListener(this);
        delete_b.setOnClickListener(this);
        viewUsers.setOnClickListener(this);
    }
    public boolean checkFields(){
        if(username.getText().toString().trim().equals("")){
            Toast.makeText(ManageUsersActivity.this, "Please enter a Username",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(e_address.getText().toString().trim().equals("")){
            Toast.makeText(ManageUsersActivity.this, "Please enter an email address",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(password.getText().toString().trim().equals("")){
            Toast.makeText(ManageUsersActivity.this, "Please enter a Password",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(confirm_password.getText().toString().trim().equals("")){
            Toast.makeText(ManageUsersActivity.this, "Please confirm your Password",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!confirm_password.getText().toString().equals(password.getText().toString())){
            Toast.makeText(ManageUsersActivity.this, "Passwords do not match",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(role_group.getCheckedRadioButtonId() == -1){
            Toast.makeText(ManageUsersActivity.this, "Please choose a roll",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean emailVerification(String email){
        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(email)) {
            return false;
        }
        return true;
    }

    private void resetFields(){
        username.setText("");
        username.setHint("Username");
        e_address.setText("");
        e_address.setHint("Email Address");
        password.setText("");
        password.setHint("Password");
        confirm_password.setText("");
        confirm_password.setHint("Confirm Password");
        role_group.clearCheck();
    }
    private void editUser(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_user, null);
        final  EditText new_username = (EditText) dialogView.findViewById(R.id.editUserName);
        final EditText new_email = (EditText) dialogView.findViewById(R.id.editUserEmail);
        final EditText new_password = (EditText) dialogView.findViewById(R.id.edit_new_password);
        final RadioGroup new_role_group = (RadioGroup) dialogView.findViewById(R.id.editRole);
        final RadioButton new_role_employee = (RadioButton) dialogView.findViewById(R.id.editRoleEmployee);
        final RadioButton new_role_patient = (RadioButton) dialogView.findViewById(R.id.editRolePatient);
        final Button edit = (Button) dialogView.findViewById(R.id.confirmEditUser);

        if(!username.getText().toString().trim().equals("") && DatabaseBrain.theDB.hasAc(username.getText().toString().trim())) {
            dialogBuilder.setView(dialogView);
            final AlertDialog b = dialogBuilder.create();
            b.show();
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!new_username.getText().toString().trim().equals("") && !new_email.getText().toString().trim().equals("") && !new_password.getText().toString().trim().equals("")) {
                        if (new_username.getText().toString().trim().equals(username.getText().toString().trim()) || !DatabaseBrain.theDB.hasAc(new_username.getText().toString().trim())) {
                            if(emailVerification(new_email.getText().toString().trim())) {
                                if (!DatabaseBrain.theDB.hasAc_email(new_email.getText().toString().trim()) || new_email.getText().toString().trim().equals(DatabaseBrain.theDB.findEmail(username.getText().toString().trim()))) {
                                    if (new_role_group.getCheckedRadioButtonId() != -1) {
                                        if (new_role_employee.getId() == new_role_group.getCheckedRadioButtonId()) {
                                            DatabaseBrain.theDB.updateAccount(username.getText().toString().trim(), new_username.getText().toString().trim(), new_email.getText().toString().trim(), new_password.getText().toString().trim(), "Employee");
                                            b.dismiss();
                                            Toast.makeText(ManageUsersActivity.this, "Account Updated!", Toast.LENGTH_SHORT).show();
                                            resetFields();
                                        } else {
                                            DatabaseBrain.theDB.updateAccount(username.getText().toString().trim(), new_username.getText().toString().trim(), new_email.getText().toString().trim(), new_password.getText().toString().trim(), "Patient");
                                            b.dismiss();
                                            Toast.makeText(ManageUsersActivity.this, "Account Updated!", Toast.LENGTH_SHORT).show();
                                            resetFields();
                                        }
                                    } else {
                                        Toast.makeText(ManageUsersActivity.this, "Please choose a role", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(ManageUsersActivity.this, "This email already exists", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(ManageUsersActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ManageUsersActivity.this, "This username already exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ManageUsersActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else if(username.getText().toString().trim().equals("")){
                Toast.makeText(ManageUsersActivity.this,"Enter a Username",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ManageUsersActivity.this,"User Does NOT Exist",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
        boolean inserted = false;
        if(add_b.getId()==v.getId() && checkFields()){
            if(emailVerification(e_address.getText().toString().trim())) {
                if (!DatabaseBrain.theDB.hasAc_email(e_address.getText().toString().trim()) && employee_rb.getId()==role_group.getCheckedRadioButtonId()) {
                    try {
                        inserted = DatabaseBrain.theDB.insertData(username.getText().toString().trim(), e_address.getText().toString().trim(), DigestUtils.sha256Hex(password.getText().toString()), "Employee","NOT COMPLETED","NOT COMPLETED","NOT COMPLETED","NOT COMPLETED","NOT COMPLETED","NO SERVICES");
                    } catch (Exception e) {
                        System.out.println("ERROR WITH ENCODING PASSWORD");
                    }
                    if(inserted){
                        Toast.makeText(ManageUsersActivity.this, "Employee Account Added", Toast.LENGTH_SHORT).show();
                        resetFields();
                    }
                } else if(!DatabaseBrain.theDB.hasAc_email(e_address.getText().toString().trim()) && patient_rb.getId()==role_group.getCheckedRadioButtonId()){
                    try {
                        inserted = DatabaseBrain.theDB.insertData(username.getText().toString().trim(), e_address.getText().toString().trim(), DigestUtils.sha256Hex(password.getText().toString()), "Patient","NOT COMPLETED","NOT COMPLETED","NOT COMPLETED","NOT COMPLETED","NOT COMPLETED","NO SERVICES");
                    } catch (Exception e) {
                        System.out.println("ERROR WITH ENCODING PASSWORD");
                    }
                    if(inserted){
                        Toast.makeText(ManageUsersActivity.this, "Patient Account Added", Toast.LENGTH_SHORT).show();
                        resetFields();
                    }
                }
                else {
                    Toast.makeText(ManageUsersActivity.this, "This email already exists", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ManageUsersActivity.this,"This email is invalid", Toast.LENGTH_SHORT).show();
            }
        }
        else if(edit_b.getId()==v.getId()){
            if(username.getText().toString().trim().equals("admin")){
                Toast.makeText(ManageUsersActivity.this,"Admin account cannot be edited", Toast.LENGTH_SHORT).show();
            }else{
                editUser();
            }

        }
        else if(delete_b.getId()==v.getId()){
            if(username.getText().toString().trim().equals("admin")){
                Toast.makeText(ManageUsersActivity.this,"Admin account cannot be edited", Toast.LENGTH_SHORT).show();
            }
            else if(DatabaseBrain.theDB.deleteAc(username.getText().toString().trim())){
                Toast.makeText(ManageUsersActivity.this,"Account Deleted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(ManageUsersActivity.this,"Account Does Not Exist", Toast.LENGTH_SHORT).show();
            }
        }
        else if(viewUsers.getId()==v.getId()){
            Intent intent = new Intent(getApplicationContext(), ViewUsers.class);
            startActivity(intent);
        }
    }
}
