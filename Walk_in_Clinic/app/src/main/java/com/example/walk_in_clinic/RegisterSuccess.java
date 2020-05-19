package com.example.walk_in_clinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterSuccess extends AppCompatActivity implements View.OnClickListener{

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(login.getId() == v.getId()){
            Intent intent = new Intent(getApplicationContext(), MainLoginActivity.class);
            startActivity(intent);
        }
    }
}
