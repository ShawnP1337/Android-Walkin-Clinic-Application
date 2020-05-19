package com.example.walk_in_clinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ViewUsers extends AppCompatActivity {

    private ArrayList<User> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);
        createUserList();
    }

    private void createUserList(){
        users = DatabaseBrain.theDB.userList();
        createRecyclerView();
    }

    private void createRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_users);
        RecyclerViewUserAdapter adapter = new RecyclerViewUserAdapter(this,users);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
