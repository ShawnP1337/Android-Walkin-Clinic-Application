package com.example.walk_in_clinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PatientActivity extends AppCompatActivity implements View.OnClickListener {

    private Button logout;
    private Button search;
    private TextView welcome_view;
    private TextView u_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        logout = (Button) findViewById(R.id.logout);
        search = (Button) findViewById(R.id.patient_search);
        Intent u_name_extract = getIntent();
        String u_name = u_name_extract.getStringExtra("USERNAME");
        welcome_view = (TextView) findViewById(R.id.welcome_message);
        welcome_view.setText(String.format("Welcome! %s", u_name));
        logout.setOnClickListener(this);
        search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(logout.getId()==v.getId()){
            Intent intent = new Intent(getApplicationContext(), MainLoginActivity.class);
            startActivity(intent);
        }
        else if(search.getId()==v.getId()){
            Intent intent = new Intent(getApplicationContext(), PatientSearchActivity.class);
            startActivity(intent);
        }
    }
}
