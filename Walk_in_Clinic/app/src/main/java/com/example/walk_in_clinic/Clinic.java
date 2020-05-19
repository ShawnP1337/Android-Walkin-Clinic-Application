package com.example.walk_in_clinic;

import java.util.ArrayList;

class Clinic {
    private String clinicName;
    private String address;
    private Double rating;
    private ArrayList<String> workingHours;
    private ArrayList<Service> clinicServices;

    public Clinic(String clinicName, String address, Double rating, ArrayList<String> workingHours, ArrayList<Service> clinicServices){
        this.clinicName=clinicName;
        this.address=address;
        this.rating=rating;
        this.workingHours=workingHours;
        this.clinicServices=clinicServices;
    }
    String getClinicName(){ return clinicName; }
    String getClinicAddress(){ return address; }
    Double getRating(){ return rating; }
    ArrayList<String> getWorkingHours(){ return workingHours; }
    ArrayList<Service> getClinicServices(){ return clinicServices; }
}
