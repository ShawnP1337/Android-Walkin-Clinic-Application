package com.example.walk_in_clinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    // Initializing all variables
    //public DatabaseHelper theDB;

    private EditText e_address;
    private EditText u_name;
    private EditText p_word;
    private EditText c_p_word;
    private EditText e_number;
    private RadioGroup radio_group;
    private RadioButton radio_btn_patient;
    private RadioButton radio_btn_employee;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Radio Group & Radio Buttons
        radio_group = (RadioGroup) findViewById(R.id.employee_patient);
        radio_btn_employee = (RadioButton) findViewById(R.id.employee);
        radio_btn_patient = (RadioButton) findViewById(R.id.patient);

        //EditText TextViews
        e_address = (EditText) findViewById(R.id.email_address);
        u_name = (EditText) findViewById(R.id.new_username);
        p_word = (EditText) findViewById(R.id.new_password);
        c_p_word = (EditText) findViewById(R.id.confirm_new_password);
        e_number= (EditText) findViewById(R.id.employee_id);
        e_number.setVisibility(View.GONE);


        //Register Button
        register = (Button) findViewById(R.id.register_button);
        //Setting OnClickListeners
        register.setOnClickListener(this);
        radio_group.setOnCheckedChangeListener(this);



    }
    public boolean checkFields(){
        if(e_address.getText().toString().trim().equals("")){
            Toast.makeText(RegisterActivity.this, "Please enter an Email address",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(u_name.getText().toString().trim().equals("")){
            Toast.makeText(RegisterActivity.this, "Please enter a Username",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(p_word.getText().toString().trim().equals("")){
            Toast.makeText(RegisterActivity.this, "Please enter a Password",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(c_p_word.getText().toString().trim().equals("")){
            Toast.makeText(RegisterActivity.this, "Please confirm your Password",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!c_p_word.getText().toString().equals(p_word.getText().toString())){
            Toast.makeText(RegisterActivity.this, "Passwords do not match",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(radio_group.getCheckedRadioButtonId() == -1){
            Toast.makeText(RegisterActivity.this, "Please choose a roll",Toast.LENGTH_LONG).show();
        }
        else if(e_number.getText().toString().trim().equals("") && (radio_group.getCheckedRadioButtonId()==radio_btn_employee.getId())){
            Toast.makeText(RegisterActivity.this, "Please enter your employee ID",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!e_number.getText().toString().trim().equals("12345")&& (radio_group.getCheckedRadioButtonId()==radio_btn_employee.getId())){
            Toast.makeText(RegisterActivity.this, "Enter a valid Employee ID",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean emailVerification(String email){
        EmailValidator  validator = EmailValidator.getInstance();
        if (!validator.isValid(email)) {
            return false;
        }
        return true;
    }

    public void onClick(View view) {
        boolean inserted = false;

        if(checkFields() && radio_btn_employee.getId()==radio_group.getCheckedRadioButtonId()) {
            if (!DatabaseBrain.theDB.hasAc(u_name.getText().toString().trim())){
                if(emailVerification(e_address.getText().toString().trim())) {
                    if (!DatabaseBrain.theDB.hasAc_email(e_address.getText().toString().trim())) {
                        try {
                            inserted = DatabaseBrain.theDB.insertData(u_name.getText().toString().trim(), e_address.getText().toString().trim(), DigestUtils.sha256Hex(p_word.getText().toString()), "Employee","NOT COMPLETED","NOT COMPLETED","NOT COMPLETED","NOT COMPLETED","NOT COMPLETED","NO SERVICES");
                        } catch (Exception e) {
                            System.out.println("ERROR WITH ENCODING PASSWORD");
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "This email already exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this,"This email is invalid", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this,"This username already exists", Toast.LENGTH_SHORT).show();
            }
        }
        else if(checkFields() && radio_btn_patient.getId() == radio_group.getCheckedRadioButtonId()){
            if (!DatabaseBrain.theDB.hasAc(u_name.getText().toString().trim())){
                if(emailVerification(e_address.getText().toString().trim())) {
                    if (!DatabaseBrain.theDB.hasAc_email(e_address.getText().toString().trim())) {
                        try {
                            inserted = DatabaseBrain.theDB.insertData(u_name.getText().toString().trim(), e_address.getText().toString().trim(), DigestUtils.sha256Hex(p_word.getText().toString()), "Patient","NOT COMPLETED","NOT COMPLETED","NOT COMPLETED","NOT COMPLETED","NOT COMPLETED","NO SERVICES");
                        } catch (Exception e) {
                            System.out.println("ERROR WITH ENCODING PASSWORD");
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "This email already exists", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this,"This email is invalid", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this,"This username already exists", Toast.LENGTH_SHORT).show();
            }
        }

        if (inserted){
            Toast.makeText(RegisterActivity.this, "Account created", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), RegisterSuccess.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId==R.id.employee){
            e_number.setVisibility(View.VISIBLE);
        } else{
            e_number.setVisibility(View.GONE);
        }
    }
}
