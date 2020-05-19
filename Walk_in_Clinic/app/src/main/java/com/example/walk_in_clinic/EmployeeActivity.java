package com.example.walk_in_clinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button viewProfile;
    private Button manageServices;
    private Button manageCalendar;
    private Button logout;
    private TextView welcome_view;
    private TextView u_name;
    private String u_name_str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        //finding button views
        viewProfile = (Button) findViewById(R.id.ViewProfileEmployee);
        manageServices = (Button) findViewById(R.id.ManageServiceEmployee);
        manageCalendar = (Button) findViewById(R.id.ManageCalenderEmployeeButton);
        logout = (Button) findViewById(R.id.logout);

        //extracting string from previous activity
        Intent u_name_extract = getIntent();
        u_name_str = u_name_extract.getStringExtra("USERNAME");

        //finding text views
        welcome_view = (TextView) findViewById(R.id.welcome_message);
        welcome_view.setText(String.format("Welcome! %s", u_name_str));

        //setting on click listeners
        viewProfile.setOnClickListener(this);
        manageServices.setOnClickListener(this);
        manageCalendar.setOnClickListener(this);
        logout.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if (viewProfile.getId() == v.getId()){
            Intent intent = new Intent(getApplicationContext(),ViewProfileEmployee.class);
            intent.putExtra("USERNAME", u_name_str);
            startActivity(intent);
        }
        else if (manageServices.getId()==v.getId()){
            Intent intent = new Intent(getApplicationContext(), ManageServicesEmployee.class);
            intent.putExtra("USERNAME", u_name_str);
            startActivity(intent);
        }
        else if(logout.getId()==v.getId()){
            Intent intent = new Intent(getApplicationContext(), MainLoginActivity.class);
            startActivity(intent);
        }
        else if (manageCalendar.getId()==v.getId()){
            Intent intent = new Intent(getApplicationContext(), ManageCalendarEmployee.class);
            intent.putExtra("USERNAME", u_name_str);
            startActivity(intent);
        }
    }
}
