package com.example.walk_in_clinic;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.codec.digest.DigestUtils;

public class DatabaseBrain extends AppCompatActivity {
    public static DatabaseHelper theDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theDB = new DatabaseHelper(this);
        try {
            if(!theDB.hasAc("admin")) {
                theDB.insertData("admin", "admin@admin.com", DigestUtils.sha256Hex("5T5ptQ"), "Admin","","","","","","");
            }
        } catch (Exception e) {
            System.out.println("ERROR WITH ENCODING PASSWORD");
        }
        Intent intent = new Intent(getApplicationContext(),MainLoginActivity.class);
        startActivity(intent);
    }
}
