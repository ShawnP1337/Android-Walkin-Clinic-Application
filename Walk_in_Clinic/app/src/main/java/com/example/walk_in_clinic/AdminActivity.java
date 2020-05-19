package com.example.walk_in_clinic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    //Buttons
    private Button logout;
    private Button manage_s;
    private Button manage_u;
    //TextViews
    private TextView welcome_view;
    private TextView u_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        logout = (Button) findViewById(R.id.logout);
        manage_s = (Button) findViewById(R.id.manage_services);
        manage_u = (Button) findViewById(R.id.manage_users);
        Intent u_name_extract = getIntent();
        String u_name = u_name_extract.getStringExtra("USERNAME");
        welcome_view = (TextView) findViewById(R.id.welcome_message);
        welcome_view.setText(String.format("Welcome! %s", u_name));
        logout.setOnClickListener(this);
        manage_s.setOnClickListener(this);
        manage_u.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(logout.getId()==v.getId()){
            Intent intent = new Intent(getApplicationContext(), MainLoginActivity.class);
            startActivity(intent);
        }
        else if(manage_s.getId()==v.getId()){
            Intent intent = new Intent(getApplicationContext(), ManageServicesActivity.class);
            startActivity(intent);
        }
        else if(manage_u.getId()==v.getId()){
            Intent intent = new Intent(getApplicationContext(), ManageUsersActivity.class);
            startActivity(intent);
        }

    }
}
