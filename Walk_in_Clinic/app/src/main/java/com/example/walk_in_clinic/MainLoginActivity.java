package com.example.walk_in_clinic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button sign_up;
    private EditText u_name;
    private EditText pass_w;
    private Button sign_in;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign_up = (Button) findViewById(R.id.sign_up);
        sign_in = (Button) findViewById(R.id.login);
        u_name = (EditText) findViewById(R.id.username);
        pass_w = (EditText) findViewById(R.id.password);
        sign_up.setOnClickListener(this);
        sign_in.setOnClickListener(this);
    }

    public void onClick(View view){
        if(sign_up.getId()==view.getId()){
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        }
        else if(sign_in.getId() == view.getId()){
            if(checkInput()){
                if(DatabaseBrain.theDB.hasAc(u_name.getText().toString().trim()) &&  DatabaseBrain.theDB.confirmPassword(u_name.getText().toString().trim(),pass_w.getText().toString().trim())){
                    System.out.println("this is passed");
                    String role = DatabaseBrain.theDB.findRole(u_name.getText().toString().trim());
                    if ("Employee".equals(role)) {
                        Intent intent_E = new Intent(getApplicationContext(), EmployeeActivity.class);
                        intent_E.putExtra("USERNAME", u_name.getText().toString().trim());
                        startActivity(intent_E);
                    } else if ("Patient".equals(role)) {
                        Intent intent_P = new Intent(getApplicationContext(), PatientActivity.class);
                        intent_P.putExtra("USERNAME", u_name.getText().toString().trim());
                        startActivity(intent_P);
                    } else if ("Admin".equals(role)) {
                        Intent intent_A = new Intent(getApplicationContext(), AdminActivity.class);
                        intent_A.putExtra("USERNAME", u_name.getText().toString().trim());
                        startActivity(intent_A);
                    }
                }else{
                    Toast.makeText(MainLoginActivity.this,"User does not exist", Toast.LENGTH_SHORT).show();
                }
                System.out.println("none of these passed");
            }
        }
    }

    private boolean checkInput(){
        if(u_name.getText().toString().trim().equals("")) {
            Toast.makeText(MainLoginActivity.this, "Please enter a Username",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(pass_w.getText().toString().equals("")){
            Toast.makeText(MainLoginActivity.this, "Please enter a Password",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
