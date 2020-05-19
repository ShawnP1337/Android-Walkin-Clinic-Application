package com.example.walk_in_clinic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ManageCalendarEmployee extends AppCompatActivity {

    private String u_name_str;
    private TextView currentTimeMonday;
    private TextView currentTimeTuesday;
    private TextView currentTimeWednesday;
    private TextView currentTimeThursday;
    private TextView currentTimeFriday;
    private ArrayList<String> hours = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_calender_employee);
        currentTimeMonday = (TextView) findViewById(R.id.monday_hours);
        currentTimeTuesday = (TextView) findViewById(R.id.tuesday_hours);
        currentTimeWednesday = (TextView) findViewById(R.id.wednesday_hours);
        currentTimeThursday = (TextView) findViewById(R.id.thursday_hours);
        currentTimeFriday = (TextView) findViewById(R.id.friday_hours);

        //extracting string from previous activity
        Intent u_name_extract = getIntent();
        u_name_str = u_name_extract.getStringExtra("USERNAME");

        //hours list
        ArrayList<String> clinicHours = DatabaseBrain.theDB.getClinicHours(DatabaseBrain.theDB.getClinicName(u_name_str));
        for (int i=0;i<clinicHours.size();i++){
            System.out.println(clinicHours.get(i));
        }
        System.out.println();
        System.out.println("hours list size: " + clinicHours.size());
        System.out.println("Clinic hours : "+DatabaseBrain.theDB.printClinichours(DatabaseBrain.theDB.getClinicName(u_name_str)));
        hours.add(currentTimeMonday.getText().toString().trim());
        hours.add(currentTimeTuesday.getText().toString().trim());
        hours.add(currentTimeWednesday.getText().toString().trim());
        hours.add(currentTimeThursday.getText().toString().trim());
        hours.add(currentTimeFriday.getText().toString().trim());


        //setting current clinic hours
        if(clinicHours.size()!=0) {
            if(clinicHours.size()==1) {
                System.out.println("######   monday  #######");
                currentTimeMonday.setText(clinicHours.get(0));
            }else if(clinicHours.size()==2) {
                System.out.println("######    monday,tuesday    #######");
                currentTimeMonday.setText(clinicHours.get(0));
                currentTimeTuesday.setText(clinicHours.get(1));
            } else if(clinicHours.size()==3){
                System.out.println("######    monday,tuesday,wednesday    #######");
                currentTimeMonday.setText(clinicHours.get(0));
                currentTimeTuesday.setText(clinicHours.get(1));
                currentTimeWednesday.setText(clinicHours.get(2));
            } else if(clinicHours.size()==4){
                System.out.println("######    monday,tuesday,wednesday,thursday    #######");
                currentTimeMonday.setText(clinicHours.get(0));
                currentTimeTuesday.setText(clinicHours.get(1));
                currentTimeWednesday.setText(clinicHours.get(2));
                currentTimeThursday.setText(clinicHours.get(3));
            } else if (clinicHours.size()==5){
                System.out.println("######    monday,tuesday,wednesday,thursday,friday    #######");
                currentTimeMonday.setText(clinicHours.get(0));
                currentTimeTuesday.setText(clinicHours.get(1));
                currentTimeWednesday.setText(clinicHours.get(2));
                currentTimeThursday.setText(clinicHours.get(3));
                currentTimeFriday.setText(clinicHours.get(4));
            }
        }

    }

    public void editMonday(View v){
        TextView newTime = (TextView) findViewById(R.id.monday_edit);
        if ((!newTime.getText().toString().trim().equals("") && Pattern.matches("^[0-2][0-9][0-9][0-9]-[0-2][0-9][0-9][0-9]$", newTime.getText().toString().trim())) || newTime.getText().toString().trim().toLowerCase().equals("closed")){
            currentTimeMonday.setText(newTime.getText());
            newTime.setText("");
            updateClinicHours(hours);
        }
        else{
            Toast.makeText(ManageCalendarEmployee.this, "Invalid Time", Toast.LENGTH_SHORT).show();
        }

    }

    public void editTuesday(View v){
        TextView currentTime = (TextView) findViewById(R.id.tuesday_hours);
        TextView newTime = (TextView) findViewById(R.id.tuesday_edit);
        if ((!newTime.getText().toString().trim().equals("") && Pattern.matches("^[0-2][0-9][0-9][0-9]-[0-2][0-9][0-9][0-9]$", newTime.getText().toString().trim())) || newTime.getText().toString().trim().toLowerCase().equals("closed")){
            currentTimeTuesday.setText(newTime.getText());
            newTime.setText("");
            updateClinicHours(hours);
        }
        else{
            Toast.makeText(ManageCalendarEmployee.this, "Invalid Time", Toast.LENGTH_SHORT).show();
        }
    }

    public void editWednesday(View v){
        TextView currentTime = (TextView) findViewById(R.id.wednesday_hours);
        TextView newTime = (TextView) findViewById(R.id.wednesday_edit);
        if ((!newTime.getText().toString().trim().equals("") && Pattern.matches("^[0-2][0-9][0-9][0-9]-[0-2][0-9][0-9][0-9]$", newTime.getText().toString().trim())) || newTime.getText().toString().trim().toLowerCase().equals("closed")){
            currentTimeWednesday.setText(newTime.getText());
            newTime.setText("");
            updateClinicHours(hours);
        }
        else{
            Toast.makeText(ManageCalendarEmployee.this, "Invalid Time", Toast.LENGTH_SHORT).show();
        }
    }

    public void editThursday(View v){
        TextView currentTime = (TextView) findViewById(R.id.thursday_hours);
        TextView newTime = (TextView) findViewById(R.id.thursday_edit);
        if ((!newTime.getText().toString().trim().equals("") && Pattern.matches("^[0-2][0-9][0-9][0-9]-[0-2][0-9][0-9][0-9]$", newTime.getText().toString().trim())) || newTime.getText().toString().trim().toLowerCase().equals("closed")){
            currentTimeThursday.setText(newTime.getText());
            newTime.setText("");
            updateClinicHours(hours);
        }
        else{
            Toast.makeText(ManageCalendarEmployee.this, "Invalid Time", Toast.LENGTH_SHORT).show();
        }
    }

    public void editFriday(View v){
        TextView currentTime = (TextView) findViewById(R.id.friday_hours);
        TextView newTime = (TextView) findViewById(R.id.friday_edit);
        if ((!newTime.getText().toString().trim().equals("") && Pattern.matches("^[0-2][0-9][0-9][0-9]-[0-2][0-9][0-9][0-9]$", newTime.getText().toString().trim())) || newTime.getText().toString().trim().toLowerCase().equals("closed")){
            currentTimeFriday.setText(newTime.getText());
            newTime.setText("");
            updateClinicHours(hours);
            DatabaseBrain.theDB.addAvailableAppointments(DatabaseBrain.theDB.getClinicName(u_name_str),permutateAppointments(DatabaseBrain.theDB.getClinicName(u_name_str)));
        }
        else{
            Toast.makeText(ManageCalendarEmployee.this, "Invalid Time", Toast.LENGTH_SHORT).show();
        }
    }
    public void updateClinicHours(ArrayList<String> workHours){
        if(!currentTimeMonday.getText().toString().trim().equals("") && !currentTimeTuesday.getText().toString().trim().equals("") && !currentTimeWednesday.getText().toString().trim().equals("") && !currentTimeThursday.getText().toString().trim().equals("") && !currentTimeFriday.getText().toString().trim().equals("")) {
            workHours.set(0,currentTimeMonday.getText().toString().trim().toLowerCase());
            workHours.set(1,currentTimeTuesday.getText().toString().trim().toLowerCase());
            workHours.set(2,currentTimeWednesday.getText().toString().trim().toLowerCase());
            workHours.set(3,currentTimeThursday.getText().toString().trim().toLowerCase());
            workHours.set(4,currentTimeFriday.getText().toString().trim().toLowerCase());
            System.out.println(currentTimeMonday.getText().toString().trim() + "," + currentTimeTuesday.getText().toString().trim() + "," + currentTimeWednesday.getText().toString().trim() + "," + currentTimeThursday.getText().toString().trim() + "," + currentTimeFriday.getText().toString().trim());
            System.out.println(DatabaseBrain.theDB.addClinicHours(DatabaseBrain.theDB.getClinicName(u_name_str), workHours));
        }else {
            Toast.makeText(ManageCalendarEmployee.this, "Please fill all time slots", Toast.LENGTH_SHORT).show();
        }
    }
    private ArrayList<Appointment> permutateAppointments(String c_name){
        ArrayList<Appointment> available_apps = new ArrayList<>();
        ArrayList<String> hours = DatabaseBrain.theDB.getClinicHours(c_name);
        String[] times = {"0000","00015","0030","0045","0100","0115","0130","0145","0200","0215","0230","0245","0300","0315","0330","0345",
                "0400","0415","0430","0445","0500","0515","0530","0545","0600","0615","0630","0645","0700","0715","0730","0745","0800","0815","0830","0845",
                "0900","0915","0930","0945","1000","1015","1030","1045","1100","1115","1130","1145","1200","1300","1315","1330","1345","1400","1415","1430",
                "1445","1500","1515","1530","1545","1600","1615","1630","1645","1700","1715","1730","1745","1800","1815","1830","1845","1900","1915","1930",
                "1945","2000","2015","2030","2045","2100","2115","2130","2145","2200","2215","2230","2245","2300","2315","2330","2345"};
        if(hours.size()!=0){
            for (int i=0; i<hours.size()-1;i++) {
                String workingHours = hours.get(i);
                if(i==0 && !workingHours.toLowerCase().equals("closed")){
                    String day = "Monday";
                    String startTime = workingHours.substring(0,4);
                    String endTime = workingHours.substring(5,9);

                    System.out.println("start: "+startTime);
                    System.out.println("end: "+endTime);

                    String temp = startTime;
                    for(int k = 0; k<times.length-1;k++){
                        if(temp.equals(times[k])){
                            for (int j=k;j<times.length-1;j++) {
                                if (!temp.equals(endTime)) {
                                    available_apps.add(new Appointment(day, temp));
                                    System.out.println("Monday"+temp);
                                    temp = times[j];
                                }
                            }
                        }
                    }
                }
                else if(i==1 && !workingHours.toLowerCase().equals("closed")){
                    String day = "Tuesday";
                    String startTime = workingHours.substring(0,4);
                    String endTime = workingHours.substring(5,9);
                    String temp = startTime;
                    for(int k = 0; k<times.length-1;k++){
                        if(temp.equals(times[k])){
                            for (int j=k;j<times.length-1;j++) {
                                if (!temp.equals(endTime)) {
                                    available_apps.add(new Appointment(day, temp));
                                    System.out.println("tuesday"+temp);
                                    temp = times[j];
                                }
                            }
                        }
                    }
                }
                else if(i==2 && !workingHours.toLowerCase().equals("closed")){
                    String day = "Wednesday";
                    String startTime = workingHours.substring(0,4);
                    String endTime = workingHours.substring(5,9);
                    String temp = startTime;
                    for(int k = 0; k<times.length-1;k++){
                        if(temp.equals(times[k])){
                            for (int j=k;j<times.length-1;j++) {
                                if (!temp.equals(endTime)) {
                                    available_apps.add(new Appointment(day, temp));
                                    temp = times[j];
                                }
                            }
                        }
                    }
                }
                else if(i==3 && !workingHours.toLowerCase().equals("closed")){
                    String day = "Thursday";
                    String startTime = workingHours.substring(0,4);
                    String endTime = workingHours.substring(5,9);
                    String temp = startTime;
                    for(int k = 0; k<times.length-1;k++){
                        if(temp.equals(times[k])){
                            for (int j=k;j<times.length-1;j++) {
                                if (!temp.equals(endTime)) {
                                    available_apps.add(new Appointment(day, temp));
                                    temp = times[j];
                                }
                            }
                        }
                    }
                }
                else if(i==4 && !workingHours.toLowerCase().equals("closed")){
                    String day = "Friday";
                    String startTime = workingHours.substring(0,4);
                    String endTime = workingHours.substring(5,9);
                    String temp = startTime;
                    for(int k = 0; k<times.length-1;k++){
                        if(temp.equals(times[k])){
                            for (int j=k;j<times.length-1;j++) {
                                if (!temp.equals(endTime)) {
                                    available_apps.add(new Appointment(day, temp));
                                    temp = times[j];
                                }
                            }
                        }
                    }
                }
            }
        }else{
            System.out.println("about to return an empty list");
            return available_apps;
        }
        return available_apps;
    }
}
