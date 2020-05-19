package com.example.walk_in_clinic;

import androidx.annotation.Nullable;

class Appointment extends android.app.Activity{
    private String weekDay;
    private String appTime;

    public Appointment(String weekDay, String appTime){
        this.weekDay = weekDay;
        this.appTime = appTime;
    }
    public String getWeekDay(){return weekDay;}
    public String getAppTime(){return appTime;}
    public boolean equals(@Nullable Object obj) {
        if(obj==null){
            throw new NullPointerException("Argument cannot be null");
        }
        else if(this.getClass() != obj.getClass()){
            throw new IllegalArgumentException("Argument is not of type 'Appointment'");
        }else{
            Appointment other = (Appointment)obj;
            if(!weekDay.equals(other.weekDay)){
                return false;
            }
            else return appTime.equals(other.appTime);
        }
    }
}
