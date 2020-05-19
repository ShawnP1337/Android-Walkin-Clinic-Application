package com.example.walk_in_clinic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static  final String DATABASE_NAME = "clients.db";

    //table clients
    private static  final String TABLE_Users = "TABLE_USERS";
    private static  final String COL_UId = "USER_ID";
    private static  final String COL_UName = "USER_NAME";
    private static  final String COL_UEmail = "USER_EMAIL";
    private static  final String COL_UPass = "USER_PASSWORD";
    private static  final String COL_URole = "USER_ROLE";
    private static  final String COL_UPhone = "USER_PHONE";
    private static  final String COL_UAddress = "USER_ADDRESS";
    private static  final String COL_UClinic = "USER_CLINIC";
    private static  final String COL_UInsurance = "USER_INSURANCE";
    private static  final String COL_UPayment = "USER_PAYMENT";
    private static  final String COL_UServices = "USER_SERVICES_SERVICES";

    //table services
    private static final String TABLE_Services = "TABLE_SERVICES";
    private static final String COL_SId = "SERVICE_ID";
    private static final String COL_SName = "SERVICE_NAME";
    private static final String COL_SRole = "SERVICE_SERVICE_ROLE";

    //table clinics
    private static final String TABLE_Clinics = "TABLE_CLINICS";
    private static final String COL_CId = "CLINIC_ID";
    private static final String COL_CName = "CLINIC_NAME";
    private static final String COL_CAddress = "CLINIC_ADDRESS";
    private static final String COL_CPhone = "CLINIC_PHONE";
    private static final String COL_CServices = "CLINIC_SERVICES";
    private static final String COL_CHours = "CLINIC_HOURS";
    private static final String COL_AVAILABLE_APPS = "CLINIC_AVAILABLE_APPOINTMENTS";
    private static final String COL_CAPPOINTMENTS_MONDAY = "CLINIC_APP_MONDAY";
    private static final String COL_CAPPOINTMENTS_TUESDAY = "CLINIC_APP_TUESDAY";
    private static final String COL_CAPPOINTMENTS_WEDNESDAY = "CLINIC_APP_WEDNESDAY";
    private static final String COL_CAPPOINTMENTS_THURSDAY = "CLINIC_APP_THURSDAY";
    private static final String COL_CAPPOINTMENTS_FRIDAY = "CLINIC_APP_FRIDAY";
    private static final String COL_CRatings = "CLINIC_RATING";



    DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       //table users
        String CREATE_USER_TABLE = "CREATE TABLE " +
                TABLE_Users + "(" + COL_UId + " INTEGER PRIMARY KEY,"+ COL_UName+" TEXT,"+ COL_UEmail + " TEXT," + COL_UPass + " TEXT," + COL_URole + " TEXT,"+ COL_UPhone +" TEXT,"+ COL_UAddress + " TEXT,"+ COL_UClinic + " TEXT,"+ COL_UInsurance + " TEXT,"+ COL_UPayment + " TEXT,"+ COL_UServices + " TEXT"+")";
        db.execSQL(CREATE_USER_TABLE);

        // table services
        String CREATE_SERVICE_TABLE = "CREATE TABLE " +
                TABLE_Services + "(" + COL_SId + " INTEGER PRIMARY KEY,"+ COL_SName+" TEXT,"+COL_SRole + " TEXT"+")";
        db.execSQL(CREATE_SERVICE_TABLE);

        String CREATE_CLINIC_TABLE = "CREATE TABLE " +
                TABLE_Clinics + "(" + COL_CId + " INTEGER PRIMARY KEY,"+ COL_CName +" TEXT,"+ COL_CAddress+" TEXT,"+ COL_CPhone+" TEXT," +COL_CServices +" TEXT," + COL_CHours + " TEXT,"+ COL_AVAILABLE_APPS + " TEXT," + COL_CAPPOINTMENTS_MONDAY + " TEXT,"+ COL_CAPPOINTMENTS_TUESDAY + " TEXT,"+ COL_CAPPOINTMENTS_WEDNESDAY + " TEXT,"+COL_CAPPOINTMENTS_THURSDAY + " TEXT," +COL_CAPPOINTMENTS_FRIDAY + " TEXT," +COL_CRatings+" TEXT"+")";
        db.execSQL(CREATE_CLINIC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Services);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Clinics);
        onCreate(db);
    }


    boolean insertData(String name, String email, String password, String role, String phone, String address, String clinic, String insurance, String payment, String services){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_UName, name);
        contentValues.put(COL_UEmail, email);
        contentValues.put(COL_UPass, password);
        contentValues.put(COL_URole, role);
        contentValues.put(COL_UPhone, phone);
        contentValues.put(COL_UAddress, address);
        contentValues.put(COL_UClinic, clinic);
        contentValues.put(COL_UInsurance, insurance);
        contentValues.put(COL_UPayment, payment);
        contentValues.put(COL_UServices, services);
        
        long result = db.insert(TABLE_Users,null , contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    void addClinicRating(String c_name, String rating){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_Clinics + " WHERE " + COL_CId;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            try {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_CName));
                if(name.equals(c_name)){
                    String current_rating = cursor.getString(cursor.getColumnIndexOrThrow(COL_CRatings));
                    if(current_rating.equals("NO RATING")){
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(COL_CRatings, rating);
                        db.update(TABLE_Clinics,contentValues,"CLINIC_NAME=?",new String[]{name});
                        cursor.close();
                        db.close();
                        return;
                    }else{
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(COL_CRatings,current_rating+","+rating);
                        db.update(TABLE_Clinics,contentValues,"CLINIC_NAME=?",new String[]{name});
                        cursor.close();
                        db.close();
                        return;
                    }
                }
            }catch (Exception e){
                Log.d("ERROR!", "addClinicRating: column is null ");
            }
        }
    }
    ArrayList<Clinic> getClinicList(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_Clinics + " WHERE " + COL_CId;
        Cursor cursor = db.rawQuery(query,null);
        ArrayList<Clinic> temp = new ArrayList<>();
        while(cursor.moveToNext()){
            ArrayList<String> tempHours = new ArrayList<>();
            ArrayList<Service> tempServices = new ArrayList<>();
            try {
                String cName = cursor.getString(cursor.getColumnIndexOrThrow(COL_CName));
                String cHours = cursor.getString(cursor.getColumnIndexOrThrow(COL_CHours));
                System.out.println("##############################################" + cHours);
                String cRating = cursor.getString(cursor.getColumnIndexOrThrow(COL_CRatings));
                System.out.println(cRating);
                String cServices = cursor.getString(cursor.getColumnIndexOrThrow(COL_CServices));
                System.out.println(cServices);
                String cAddress = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAddress));
                System.out.println(Arrays.asList(cHours.split(",")));
                tempHours.addAll(Arrays.asList(cHours.split(",")));
                for (String ser:cServices.split(",")){
                    tempServices.add(getService(ser));
                }
                if(!cRating.equals("NO RATING")) {
                    double sum = 0.0;
                    for (String rating : cRating.split(",")) {
                        sum += Double.parseDouble(rating);
                    }
                    Double clinic_rating = sum / cRating.split(",").length;
                    Clinic newClinic = new Clinic(cName, cAddress, clinic_rating, tempHours, tempServices);
                    temp.add(newClinic);
                }else{
                    Clinic newClinic = new Clinic(cName, cAddress, 0.0, tempHours, tempServices);
                    temp.add(newClinic);
                }
            }catch (Exception e){
                Log.d("ERROR", "getClinicList: error building clinic list");
            }
        }
        cursor.close();
        db.close();
        return temp;

    }
    boolean addClinicInformation(String u_name){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryUser = "Select * FROM " + TABLE_Users + " WHERE " + COL_UId;
        String queryClinic = "Select * FROM " + TABLE_Clinics + " WHERE " + COL_CId;
        Cursor cursorUser = db.rawQuery(queryUser,null);
        Cursor cursorClinic = db.rawQuery(queryClinic,null);
        boolean b =false;
        while(cursorUser.moveToNext()){
            try{
                String user = cursorUser.getString(cursorUser.getColumnIndexOrThrow(COL_UName));
                if(u_name.equals(user)) {
                    String empClinic = cursorUser.getString(cursorUser.getColumnIndexOrThrow(COL_UClinic));
                    while (cursorClinic.moveToNext()) {
                        try {
                            String c_name = cursorClinic.getString(cursorClinic.getColumnIndexOrThrow(COL_CName));
                            System.out.println("Clinic name is: "+c_name);
                            if (empClinic.equals(c_name)) {
                                System.out.println("Im inside the second loop, i know there is a clinic with this name");
                                System.out.println();
                                System.out.println("user services is: "+cursorUser.getString(cursorUser.getColumnIndex(COL_UServices)));
                                String[] user_services = cursorUser.getString(cursorUser.getColumnIndex(COL_UServices)).split(",");
                                for (String ser : user_services) {
                                    System.out.println("clinic services is: "+cursorClinic.getString(cursorClinic.getColumnIndex(COL_CServices)));
                                    String clinic_ser = cursorClinic.getString(cursorClinic.getColumnIndex(COL_CServices));
                                    if(clinic_ser.equals("NO SERVICES")){
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put(COL_CServices,ser);
                                        db.update(TABLE_Clinics, contentValues, "CLINIC_NAME=?", new String[]{c_name});
                                    }
                                    else if (!clinic_ser.contains(ser)) {
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put(COL_CServices, clinic_ser + "," + ser);
                                        System.out.println(clinic_ser + "," + ser);
                                        db.update(TABLE_Clinics, contentValues, "CLINIC_NAME=?", new String[]{c_name});

                                    }
                                }
                                cursorClinic.close();
                                cursorUser.close();
                                db.close();
                                System.out.println("im about to return inside the second loop");
                                return true;
                            }
                        } catch (Exception e) {
                            Log.d("ERROR!", "ERROR COULD NOT FIND CLINIC COLUMN - addClinicInformation");
                            cursorClinic.close();
                            cursorUser.close();
                            db.close();
                            System.out.println("im about to return inside the second loop");
                            return true;
                        }
                    }
                    String c_name = cursorUser.getString(cursorUser.getColumnIndexOrThrow(COL_UClinic));
                    String c_address = cursorUser.getString(cursorUser.getColumnIndexOrThrow(COL_UAddress));
                    String c_phone = cursorUser.getString(cursorUser.getColumnIndexOrThrow(COL_UPhone));
                    String c_services = cursorUser.getString(cursorUser.getColumnIndexOrThrow(COL_UServices));
                    ContentValues contentValuesNew = new ContentValues();
                    contentValuesNew.put(COL_CName,c_name);
                    contentValuesNew.put(COL_CAddress,c_address);
                    contentValuesNew.put(COL_CPhone,c_phone);
                    contentValuesNew.put(COL_CServices, c_services);
                    contentValuesNew.put(COL_CHours,"NO HOURS");
                    contentValuesNew.put(COL_CRatings,"NO RATING");
                    contentValuesNew.put(COL_AVAILABLE_APPS,"NO APPOINTMENTS");
                    contentValuesNew.put(COL_CAPPOINTMENTS_MONDAY,"NO APPOINTMENTS");
                    contentValuesNew.put(COL_CAPPOINTMENTS_TUESDAY,"NO APPOINTMENTS");
                    contentValuesNew.put(COL_CAPPOINTMENTS_WEDNESDAY,"NO APPOINTMENTS");
                    contentValuesNew.put(COL_CAPPOINTMENTS_THURSDAY,"NO APPOINTMENTS");
                    contentValuesNew.put(COL_CAPPOINTMENTS_FRIDAY,"NO APPOINTMENTS");


                    db.insert(TABLE_Clinics,null,contentValuesNew);
                    b=true;
                }
            } catch (Exception e){
                Log.d("ERROR!", "ERROR COULD NOT FIND USER COLUMN - addClinicInformation");
            }
        }
        cursorClinic.close();
        cursorUser.close();
        db.close();
        System.out.println("im about to return inside the first loop");
        return b;
    }
    boolean deleteClinicService(String c_name, String ser) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_Clinics + " WHERE " + COL_CId;
        Cursor cursor = db.rawQuery(query, null);
        boolean b = false;
        ContentValues contentValues = new ContentValues();
        while (cursor.moveToNext()) {
            try {
                String cName = cursor.getString(cursor.getColumnIndexOrThrow(COL_CName));
                if (c_name.equals(cName)) {
                    try {
                        int index_check = cursor.getColumnIndexOrThrow(COL_CServices);
                        String current_services = cursor.getString(index_check);
                        if (!current_services.equals("")) {
                            b = true;
                            String new_str = current_services.replace(ser, "");
                            if (new_str.startsWith(",")) {
                                new_str = new_str.replaceFirst(",", "");
                                contentValues.put(COL_CServices, new_str);
                                db.update("TABLE_Clinics", contentValues, "CLINIC_NAME=?", new String[]{c_name});
                            } else if (new_str.endsWith(",")) {
                                new_str = new_str.substring(0, new_str.length() - 1);
                                contentValues.put(COL_CServices, new_str);
                                db.update("TABLE_Clinics", contentValues, "CLINIC_NAME=?", new String[]{c_name});
                            } else if (!new_str.startsWith(",") && !new_str.endsWith(",") && !new_str.equals("")) {
                                String new_str2 = current_services.replace("," + ser, "");
                                contentValues.put(COL_CServices, new_str2);
                                db.update("TABLE_Clinics", contentValues, "CLINIC_NAME=?", new String[]{c_name});
                            } else {
                                contentValues.put(COL_CServices, "NO SERVICES");
                                db.update("TABLE_Clinics", contentValues, "CLINIC_NAME=?", new String[]{c_name});
                            }
                        }
                    } catch (Exception e) {
                        Log.d("ERROR!", "deleteClinicService: COULD NOT FIND CLINIC SERVICES!");
                    }
                }
            } catch (Exception e) {
                Log.d("ERROR!", "deleteClinicService: COULD NOT FIND CLINIC NAME! ");
            }
        }
        cursor.close();
        db.close();
        return b;
    }
    String printClinichours(String c_name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_Clinics + " WHERE " +COL_CId;
        Cursor cursor = db.rawQuery(query,null);
        String hours=null;
        while(cursor.moveToNext()){
            if(c_name.equals(cursor.getString(cursor.getColumnIndexOrThrow(COL_CName)))) {
                hours = cursor.getString(cursor.getColumnIndexOrThrow(COL_CHours));
                System.out.println(cursor.getString(cursor.getColumnIndexOrThrow(COL_CName)) +" "+cursor.getString(cursor.getColumnIndexOrThrow(COL_CAddress))+" "+cursor.getString(cursor.getColumnIndexOrThrow(COL_CPhone))+" hours: "+cursor.getString(cursor.getColumnIndexOrThrow(COL_CHours))+"services: " +cursor.getString(cursor.getColumnIndexOrThrow(COL_CServices)));            }
        }
        cursor.close();
        db.close();
        return hours;
    }
    boolean addAvailableAppointments(String c_name,ArrayList<Appointment> av_apps){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_Clinics + " WHERE " +COL_CId;
        Cursor cursor = db.rawQuery(query,null);
        boolean b=false;
        while (cursor.moveToNext()){
            try{
                String cName = cursor.getString(cursor.getColumnIndexOrThrow(COL_CName));
                if(c_name.equals(cName)){
                    ContentValues contentValues = new ContentValues();
                    try {
                        System.out.println(av_apps);
                        String str=av_apps.get(0).getWeekDay() + "-" + av_apps.get(0).getAppTime();
                        for (Appointment a : av_apps) {
                            if(!a.equals(av_apps.get(0))) {
                                str=str+","+a.getWeekDay() + "-" + a.getAppTime();
                            }
                        }
                        if(str.contains("NO APPOINTMENTS")){
                            str=str.replaceFirst("NO APPOINTMENTS,","");
                            if(str.startsWith(",")){
                                str=str.replaceFirst(",","");
                            }
                        }
                        ContentValues contentValues1 = new ContentValues();
                        System.out.println(str);
                        System.out.println(cursor.getString(cursor.getColumnIndexOrThrow(COL_AVAILABLE_APPS)));
                        contentValues1.put(COL_AVAILABLE_APPS, str);
                        contentValues1.put(COL_CAPPOINTMENTS_MONDAY,"NO APPOINTMENTS");
                        contentValues1.put(COL_CAPPOINTMENTS_TUESDAY,"NO APPOINTMENTS");
                        contentValues1.put(COL_CAPPOINTMENTS_WEDNESDAY,"NO APPOINTMENTS");
                        contentValues1.put(COL_CAPPOINTMENTS_THURSDAY,"NO APPOINTMENTS");
                        contentValues1.put(COL_CAPPOINTMENTS_FRIDAY,"NO APPOINTMENTS");
                        db.update(TABLE_Clinics, contentValues1, "CLINIC_NAME=?", new String[]{c_name});
                        b = true;

                    }catch (Exception e){
                        Log.d("ERROR", "addAvailableAppointments: no available apps column ");
                    }
                }
            }catch (Exception e){
                Log.d("ERROR!", "addAvailableAppointments: NAME COLUMN IS NULL ");
            }
        }
        cursor.close();
        db.close();
        return b;
    }
    boolean updateAppointments(String c_name, String weekDay, String appTime){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_Clinics + " WHERE " +COL_CId;
        Cursor cursor = db.rawQuery(query,null);
        boolean b=false;
        while(cursor.moveToNext()){
            try{
                String cName = cursor.getString(cursor.getColumnIndexOrThrow(COL_CName));
                if(c_name.equals(cName)){
                    try{
                        String appointments = cursor.getString(cursor.getColumnIndexOrThrow(COL_AVAILABLE_APPS));
                        String new_appointments = appointments.replace(weekDay+"-"+appTime,"");
                        if(new_appointments.startsWith(",")){
                            new_appointments=new_appointments.replaceFirst(",","");
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(COL_AVAILABLE_APPS,new_appointments);
                        }
                        else if(new_appointments.endsWith(",")){
                            new_appointments=new_appointments.substring(0,new_appointments.length()-1);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(COL_AVAILABLE_APPS,new_appointments);
                            db.update(TABLE_Clinics,contentValues,"CLINIC_NAME=?",new String[]{c_name});
                        }
                        else if(!new_appointments.startsWith(",") && !new_appointments.endsWith(",") && !new_appointments.equals("")){
                            String new_appointments2=appointments.replaceFirst(weekDay+"-"+appTime+",","");
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(COL_AVAILABLE_APPS,new_appointments2);
                            db.update(TABLE_Clinics,contentValues,"CLINIC_NAME=?",new String[]{c_name});
                            System.out.println("NEWEST APPS:" + new_appointments2);
                        }
                        else{
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(COL_AVAILABLE_APPS,"NO APPOINTMENTS");
                            db.update(TABLE_Clinics,contentValues,"CLINIC_NAME=?",new String[]{c_name});
                        }
                        ContentValues contentValues1 = new ContentValues();
                        if(weekDay.equals("Monday")){
                            try {
                                String apps = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAPPOINTMENTS_MONDAY));
                                if(apps.equals("NO APPOINTMENTS")) {
                                    contentValues1.put(COL_CAPPOINTMENTS_MONDAY,weekDay+"-"+appTime);
                                }else {
                                    contentValues1.put(COL_CAPPOINTMENTS_MONDAY,apps+","+weekDay+"-"+appTime);
                                }
                                db.update(TABLE_Clinics,contentValues1,"CLINIC_NAME=?",new String[]{c_name});
                                b=true;
                            }catch (Exception e){
                                Log.d("ERROR", "updateAppointments: couldn't find monday appointments ");
                            }
                        }
                        else if(weekDay.equals("Tuesday")){
                            try {
                                String apps = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAPPOINTMENTS_TUESDAY));
                                if(apps.equals("NO APPOINTMENTS")) {
                                    contentValues1.put(COL_CAPPOINTMENTS_TUESDAY,weekDay+"-"+appTime);
                                }else {
                                    contentValues1.put(COL_CAPPOINTMENTS_TUESDAY,apps+","+weekDay+"-"+appTime);
                                }
                                db.update(TABLE_Clinics,contentValues1,"CLINIC_NAME=?",new String[]{c_name});
                                b=true;
                            }catch (Exception e){
                                Log.d("ERROR", "updateAppointments: couldn't find tuesday appointments ");
                            }
                        }
                        else if(weekDay.equals("Wednesday")){
                            try {
                                String apps = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAPPOINTMENTS_WEDNESDAY));
                                System.out.println(apps);

                                if(apps.equals("NO APPOINTMENTS")) {
                                    contentValues1.put(COL_CAPPOINTMENTS_WEDNESDAY,weekDay+"-"+appTime);
                                }else {
                                    contentValues1.put(COL_CAPPOINTMENTS_WEDNESDAY,apps+","+weekDay+"-"+appTime);
                                }
                                db.update(TABLE_Clinics,contentValues1,"CLINIC_NAME=?",new String[]{c_name});
                                b=true;
                            }catch (Exception e){
                                Log.d("ERROR", "updateAppointments: couldn't find wednesday appointments ");
                            }
                        }
                        else if(weekDay.equals("Thursday")){
                            try {
                                String apps = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAPPOINTMENTS_THURSDAY));
                                System.out.println(apps);

                                if(apps.equals("NO APPOINTMENTS")) {
                                    contentValues1.put(COL_CAPPOINTMENTS_THURSDAY,weekDay+"-"+appTime);
                                }else {
                                    contentValues1.put(COL_CAPPOINTMENTS_THURSDAY,apps+","+weekDay+"-"+appTime);
                                }
                                db.update(TABLE_Clinics,contentValues1,"CLINIC_NAME=?",new String[]{c_name});
                                b=true;
                            }catch (Exception e){
                                Log.d("ERROR", "updateAppointments: couldn't find thursday appointments ");
                            }
                        }
                        else if(weekDay.equals("Friday")){
                            try {
                                String apps = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAPPOINTMENTS_FRIDAY));
                                if(apps.equals("NO APPOINTMENTS")) {
                                    contentValues1.put(COL_CAPPOINTMENTS_FRIDAY,weekDay+"-"+appTime);
                                }else {
                                    contentValues1.put(COL_CAPPOINTMENTS_FRIDAY,apps+","+weekDay+"-"+appTime);
                                }
                                db.update(TABLE_Clinics,contentValues1,"CLINIC_NAME=?",new String[]{c_name});
                                b=true;
                            }catch (Exception e){
                                Log.d("ERROR", "updateAppointments: couldn't find friday appointments ");
                            }
                        }
                    }catch (Exception e){
                        Log.d("ERROR", "updateAppointments: could not find any appointments ");
                    }
                }
            } catch (Exception e){
                Log.d("ERROR", "updateAppointments: - clinic name could not be found ");
            }
        }
        cursor.close();
        db.close();
        return b;
    }
    ArrayList<Appointment> getAppointmentsList(String c_name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Clinics + " WHERE " +COL_CId;
        Cursor cursor = db.rawQuery(query,null);
        ArrayList<Appointment> temp_list = new ArrayList<>();
        while (cursor.moveToNext()){
            try{
                String cName = cursor.getString(cursor.getColumnIndexOrThrow(COL_CName));
                if (c_name.equals(cName)){
                    try {
                        String appointments = cursor.getString(cursor.getColumnIndexOrThrow(COL_AVAILABLE_APPS));
                        System.out.println(appointments);
                        String[] app_arr = appointments.split(",");
                        for (String a: app_arr){
                            String[] temp = a.split("-");
                            System.out.println(Arrays.toString(temp));
                            temp_list.add(new Appointment(temp[0],temp[1]));
                        }
                    }catch (Exception e){
                        Log.d("ERROR", "getAppointmentsList: could not get available appointments");
                    }
                }
            }
            catch (Exception e){
                Log.d("ERROR", "getAppointmentsList: could not find clinic name column ");
            }
        }
        cursor.close();
        db.close();
        return temp_list;
    }
    boolean addClinicHours(String c_name, ArrayList<String> clinicHours){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_Clinics + " WHERE " +COL_CId;
        Cursor cursor = db.rawQuery(query,null);
        boolean b = false;
        while (cursor.moveToNext()) {
            try {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_CName));
                System.out.println("this is the clinic name i found: "+name);
                if(name.equals(c_name)){
                    ContentValues contentValues = new ContentValues();
                    String hours="";
                    for (int i=0; i<clinicHours.size();i++) {
                        if(hours.equals("")){
                            hours+=clinicHours.get(0)+",";
                        }else if(i==clinicHours.size()-1) {
                            hours+=clinicHours.get(i);
                        }else{
                            hours+=clinicHours.get(i)+",";
                        }
                    }
                    contentValues.put(COL_CHours, hours);
                    db.update("TABLE_Clinics",contentValues,"CLINIC_NAME=?",new String[]{c_name});
                    b=true;
                    System.out.println(cursor.getString(cursor.getColumnIndexOrThrow(COL_CName)) +" "+cursor.getString(cursor.getColumnIndexOrThrow(COL_CAddress))+" "+cursor.getString(cursor.getColumnIndexOrThrow(COL_CPhone))+" hours: "+cursor.getString(cursor.getColumnIndexOrThrow(COL_CHours))+"services: " +cursor.getString(cursor.getColumnIndexOrThrow(COL_CServices)));
                }
            } catch (Exception e){
                Log.d("ERROR!", "ERROR ADDING CLINIC WORK HOURS! - addClinicHours");
            }
        }
        cursor.close();
        db.close();
        return b;
    }
    ArrayList<String> getClinicHours(String c_name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Clinics  +" WHERE " + COL_CId;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> temp = new ArrayList<>();
        while (cursor.moveToNext()){
            try{
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_CName));
                if(name.equals(c_name)){
                    String hours = cursor.getString(cursor.getColumnIndexOrThrow(COL_CHours));
                    System.out.println("these are the clinic hours: " + hours);
                    String[] hours_list = hours.split(",");
                    temp.addAll(Arrays.asList(hours_list));
                }
            }catch (Exception e){
                Log.d("ERROR!", "ERROR BUILDING CLINIC HOURS LIST! - getClinicHours");
            }
        }
        cursor.close();
        db.close();
        return temp;
    }

    ArrayList<User> userList(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UId;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<User> temp = new ArrayList<>();
        while(cursor.moveToNext()){
            try {
                String u_name = cursor.getString(cursor.getColumnIndexOrThrow("USER_NAME"));
                String e_mail = cursor.getString(cursor.getColumnIndexOrThrow("USER_EMAIL"));
                String p_word = cursor.getString(cursor.getColumnIndexOrThrow("USER_PASSWORD"));
                String role = cursor.getString(cursor.getColumnIndexOrThrow("USER_ROLE"));
                User user = new User(u_name,e_mail,p_word,role);
                temp.add(user);

            } catch(Exception e){
                Log.d("ERROR!", "ERROR BUILDING USER LIST!");
            }
        }
        cursor.close();
        db.close();
        return temp;
    }
    ArrayList<com.example.walk_in_clinic.Service> serviceList(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Services  +" WHERE " + COL_SId;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<com.example.walk_in_clinic.Service> temp = new ArrayList<>();
        while(cursor.moveToNext()){
            try {
                String s_name = cursor.getString(cursor.getColumnIndexOrThrow("SERVICE_NAME"));
                String role = cursor.getString(cursor.getColumnIndexOrThrow("SERVICE_SERVICE_ROLE"));
                com.example.walk_in_clinic.Service service = new com.example.walk_in_clinic.Service(s_name,role);
                temp.add(service);

            } catch(Exception e){
                Log.d("ERROR!", "ERROR BUILDING SERVICE LIST!");
            }
        }
        cursor.close();
        db.close();
        return temp;
    }
    boolean hasAc(String name){
        boolean b = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UName + " = \"" + name + "\"";
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()) {
            try {
                int index = cursor.getColumnIndexOrThrow("USER_NAME");
                String u_name = cursor.getString(index);
                if (u_name.equals(name)) {
                    b = true;
                }

            } catch (Exception e) {
                Log.d("ERROR", "USERNAME WAS NOT FOUND!");
            }
        }
        cursor.close();
        db.close();
        return b;
    }

    boolean hasAc_email(String email){
        boolean b = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UId;
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()) {
            try {
                int index = cursor.getColumnIndexOrThrow("USER_EMAIL");
                String e_mail = cursor.getString(index);
                if (e_mail.equals(email)) {
                    b = true;
                }

            } catch (Exception e) {
                Log.d("ERROR", "ERROR!");
            }
        }
        cursor.close();
        db.close();
        return b;
    }

    boolean updateAccount(String u_name_old, String u_name_new, String e_address, String p_word, String role){
        boolean b = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UId;
        Cursor cursor = db.rawQuery(query, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_UName, u_name_new);
        contentValues.put(COL_UEmail, e_address);
        contentValues.put(COL_UPass, DigestUtils.sha256Hex(p_word));
        contentValues.put(COL_URole, role);
        while(cursor.moveToNext()) {
            try {
                int index = cursor.getColumnIndexOrThrow("USER_NAME");
                String username = cursor.getString(index);
                if (u_name_old.equals(username)) {
                    b = true;
                    db.update("TABLE_Users",contentValues,"USER_NAME=?",new String[]{u_name_old});
                }

            } catch (Exception e) {
                Log.d("ERROR", "ACCOUNT COULD NOT BE UPDATED");
            }
        }
        cursor.close();
        db.close();
        return b;
    }
    boolean updateProfile(String u_name, String address, String phone_number, String clinic_name, String insurance, String payment){
        boolean b = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UId;
        Cursor cursor = db.rawQuery(query, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_UAddress, address);
        contentValues.put(COL_UPhone, phone_number);
        contentValues.put(COL_UClinic, clinic_name);
        contentValues.put(COL_UInsurance, insurance);
        contentValues.put(COL_UPayment, payment);
        while(cursor.moveToNext()) {
            try {
                int index = cursor.getColumnIndexOrThrow("USER_NAME");
                String username = cursor.getString(index);
                if (u_name.equals(username)) {
                    b = true;
                    db.update("TABLE_Users",contentValues,"USER_NAME=?",new String[]{u_name});
                }

            } catch (Exception e) {
                Log.d("ERROR", "ACCOUNT COULD NOT BE UPDATED");
            }
        }
        cursor.close();
        db.close();
        return b;
    }
    String getAddress(String u_name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UId;
        Cursor cursor = db.rawQuery(query, null);
        String address=null;
        if(u_name!=null) {
            while (cursor.moveToNext()) {
                try {
                    int index = cursor.getColumnIndexOrThrow("USER_NAME");
                    String username = cursor.getString(index);
                    if (u_name.equals(username)) {
                        System.out.println("Ive found the account");
                        int index_a = cursor.getColumnIndexOrThrow("USER_ADDRESS");
                        address = cursor.getString(index_a);
                    }

                } catch (Exception e) {
                    Log.d("ERROR", "ACCOUNT COULD NOT BE LOCATED- address");
                }
            }
            cursor.close();
            db.close();
        } else {
            return "NOT COMPLETED";
        }
        return address;
    }
    String getPhone(String u_name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UId;
        Cursor cursor = db.rawQuery(query, null);
        String phone_number=null;
        if(u_name!=null) {
            while (cursor.moveToNext()) {
                try {
                    int index = cursor.getColumnIndexOrThrow("USER_NAME");
                    String username = cursor.getString(index);
                    if (u_name.equals(username)) {
                        int index_p = cursor.getColumnIndexOrThrow("USER_PHONE");
                        phone_number = cursor.getString(index_p);
                    }

                } catch (Exception e) {
                    Log.d("ERROR", "ACCOUNT COULD NOT BE LOCATED-phone");
                }
            }
            cursor.close();
            db.close();
        }else{
            return null;
        }
        return phone_number;
    }
    String getClinicName(String u_name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UId;
        Cursor cursor = db.rawQuery(query, null);
        String clinic_name=null;
        if(u_name!=null) {
            while (cursor.moveToNext()) {
                try {
                    int index = cursor.getColumnIndexOrThrow("USER_NAME");
                    String username = cursor.getString(index);
                    if (u_name.equals(username)) {
                        int index_a = cursor.getColumnIndexOrThrow("USER_CLINIC");
                        clinic_name = cursor.getString(index_a);
                    }

                } catch (Exception e) {
                    Log.d("ERROR", "ACCOUNT COULD NOT BE LOCATED-clinicname");
                }
            }
            cursor.close();
            db.close();
        } else {
            return null;
        }
        return clinic_name;
    }
    String getInsurance(String u_name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UId;
        Cursor cursor = db.rawQuery(query, null);
        String insurance=null;
        if(u_name!=null) {
            while (cursor.moveToNext()) {
                try {
                    int index = cursor.getColumnIndexOrThrow("USER_NAME");
                    String username = cursor.getString(index);
                    if (u_name.equals(username)) {
                        int index_a = cursor.getColumnIndexOrThrow("USER_INSURANCE");
                        insurance = cursor.getString(index_a);
                    }

                } catch (Exception e) {
                    Log.d("ERROR", "ACCOUNT COULD NOT BE LOCATED-insurance");
                }
            }
            cursor.close();
            db.close();
        } else {
            return null;
        }
        return insurance;
    }
    String getPayment(String u_name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UId;
        Cursor cursor = db.rawQuery(query, null);
        String payment=null;
        if(u_name!=null) {
            while (cursor.moveToNext()) {
                try {
                    int index = cursor.getColumnIndexOrThrow("USER_NAME");
                    String username = cursor.getString(index);
                    if (u_name.equals(username)) {
                        int index_a = cursor.getColumnIndexOrThrow("USER_PAYMENT");
                        payment = cursor.getString(index_a);
                    }

                } catch (Exception e) {
                    Log.d("ERROR", "ACCOUNT COULD NOT BE LOCATED-payment");
                }
            }
            cursor.close();
            db.close();
        }else {
            return null;
        }
        return payment;
    }


    public boolean deleteAc(String u_name) {
        boolean b = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UId;
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()) {
            try {
                int index = cursor.getColumnIndexOrThrow("USER_NAME");
                String username = cursor.getString(index);
                if (u_name.equals(username)) {
                    b = true;
                    db.delete("TABLE_Users","USER_NAME=?",new String[]{u_name});
                }

            } catch (Exception e) {
                Log.d("ERROR", "ACCOUNT COULD NOT BE DELETED");
            }
        }
        cursor.close();
        db.close();
        return b;
    }

    boolean confirmPassword(String username,String password){
        boolean b = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UName + " = \"" + username + "\"";
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()) {
            try {
                int index = cursor.getColumnIndexOrThrow("USER_NAME");
                String u_name = cursor.getString(index);
                System.out.println(u_name);
                if (u_name.equals(username)) {
                    try {
                        int index_p = cursor.getColumnIndexOrThrow("USER_PASSWORD");
                        String p_word = cursor.getString(index_p);
                        if (DigestUtils.sha256Hex(password).equals(p_word)){
                            b = true;
                        }
                    } catch (Exception e) {
                        Log.d("ERROR!", "PASSWORD WAS NOT FOUND!");
                    }
                }

            } catch (Exception e) {
                Log.d("ERROR", "USERNAME WAS NOT FOUND!");
            }
        }
        cursor.close();
        db.close();
        return b;
    }


    String findRole(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UName + " = \"" + username + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String role = "Z";

        while(cursor.moveToNext()) {
            try {
                int index = cursor.getColumnIndexOrThrow("USER_NAME");
                String u_name = cursor.getString(index);
                if (u_name.equals(username)) {
                    try {
                        int index_r = cursor.getColumnIndexOrThrow("USER_ROLE");
                        role = cursor.getString(index_r);
                    } catch (Exception e) {
                        Log.d("ERROR!", "ROLE WAS NOT FOUND!");
                    }
                }

            } catch (Exception e) {
                Log.d("ERROR", "USERNAME WAS NOT FOUND!");
            }
        }
        cursor.close();
        db.close();
        return role;
    }
    String findEmail(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UName + " = \"" + username + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String email = "X";

        while(cursor.moveToNext()) {
            try {
                int index = cursor.getColumnIndexOrThrow("USER_NAME");
                String u_name = cursor.getString(index);
                if (u_name.equals(username)) {
                    try {
                        int index_e = cursor.getColumnIndexOrThrow("USER_EMAIL");
                        email = cursor.getString(index_e);
                    } catch (Exception e) {
                        Log.d("ERROR!", "ROLE WAS NOT FOUND!");
                    }
                }

            } catch (Exception e) {
                Log.d("ERROR", "USERNAME WAS NOT FOUND!");
            }
        }
        cursor.close();
        db.close();
        return email;
    }

    boolean addService(String name, String role){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SName, name);
        contentValues.put(COL_SRole, role);
        long result = db.insert(TABLE_Services,null , contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean hasService(String sname) {
        boolean b = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Services  +" WHERE " + COL_SId;
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()) {
            try {
                int index = cursor.getColumnIndexOrThrow("SERVICE_NAME");
                String service_n = cursor.getString(index);
                if (service_n.equals(sname)) {
                    b = true;
                }

            } catch (Exception e) {
                Log.d("ERROR", "SERVICE COULD NOT BE FOUND");
            }
        }
        cursor.close();
        db.close();
        return b;
    }
    public boolean deleteService(String sname) {
        boolean b = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_Services  +" WHERE " + COL_SId;
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()) {
            try {
                int index = cursor.getColumnIndexOrThrow("SERVICE_NAME");
                String service_n = cursor.getString(index);
                if (service_n.equals(sname)) {
                    b = true;
                    db.delete("TABLE_Services","SERVICE_NAME=?",new String[]{service_n});
                }

            } catch (Exception e) {
                Log.d("ERROR", "SERVICE COULD NOT BE DELETED");
            }
        }
        cursor.close();
        db.close();
        return b;
    }

    boolean updateService(String s_name_old, String s_name_new, String role){
        boolean b = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_Services  +" WHERE " + COL_SId;
        Cursor cursor = db.rawQuery(query, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SName, s_name_new);
        contentValues.put(COL_SRole, role);
        while(cursor.moveToNext()) {
            try {
                int index = cursor.getColumnIndexOrThrow("SERVICE_NAME");
                String sname = cursor.getString(index);
                if (s_name_old.equals(sname)) {
                    b = true;
                    db.update("TABLE_Services",contentValues,"SERVICE_NAME=?",new String[]{s_name_old});
                }

            } catch (Exception e) {
                Log.d("ERROR", "SERVICE COULD NOT BE UPDATED");
            }
        }
        cursor.close();
        db.close();
        return b;
    }
    Service getService(String s_name){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Services  +" WHERE " + COL_SId;
        Cursor cursor = db.rawQuery(query, null);
        Service service = null;
        while(cursor.moveToNext()){
            try{
                int index_n = cursor.getColumnIndexOrThrow(COL_SName);
                String service_name = cursor.getString(index_n);
                int index_r = cursor.getColumnIndexOrThrow("SERVICE_SERVICE_ROLE");
                String service_role = cursor.getString(index_r);
                if(s_name.equals(service_name)){
                    service = new Service(service_name,service_role);
                }

            } catch(Exception e) {
                Log.d("ERROR", "SERVICE COULD NOT BE FOUND");
            }
        }
        cursor.close();
        db.close();
        return service;
    }
    ArrayList<Service> getServicesListEmployee(String c_name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_Clinics  +" WHERE " + COL_CId;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Service> services_list = new ArrayList<>();
        while(cursor.moveToNext()){
            try{
                int index = cursor.getColumnIndexOrThrow(COL_CName);
                String cname = cursor.getString(index);
                if (c_name.equals(cname)) {
                    try {
                        int index_s = cursor.getColumnIndexOrThrow(COL_CServices);
                        String services = cursor.getString(index_s);
                        System.out.println("#############   " + services+"   ############");
                        if(!services.equals("NO SERVICES") && !services.equals("")) {
                            if(services.contains(",")) {
                                String[] str_arr = services.split(",");
                                for (int i = 0; i < str_arr.length; i++) {
                                    services_list.add(getService(str_arr[i]));
                                }
                            }else {
                                services_list.add(getService(services));
                            }
                        }
                    } catch (Exception e){
                        Log.d("ERROR", "USER SERVICES COLUMN DOES NOT EXIST - GET SERVICES LIST");
                    }
                }

            } catch (Exception e){
                Log.d("ERROR", "USER DOES NOT EXIST - GET SERVICES LIST");
            }
        }
        cursor.close();
        db.close();
        return services_list;
    }
    boolean addServiceEmployee(String u_name,String new_service){
        boolean b = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UId;
        Cursor cursor = db.rawQuery(query, null);
        ContentValues contentValues = new ContentValues();
        while(cursor.moveToNext()) {
            try {
                int index = cursor.getColumnIndexOrThrow("USER_NAME");
                String username = cursor.getString(index);
                if (u_name.equals(username)) {
                    try {
                        int index_check = cursor.getColumnIndexOrThrow("USER_SERVICES_SERVICES");
                        String current_services = cursor.getString(index_check);
                        if(!current_services.equals("NO SERVICES") && !current_services.equals("")) {
                            b = true;
                            contentValues.put(COL_UServices, current_services + "," + new_service);
                            db.update("TABLE_Users", contentValues, "USER_NAME=?", new String[]{u_name});
                        } else {
                            b = true;
                            contentValues.put(COL_UServices, new_service);
                            db.update("TABLE_Users", contentValues, "USER_NAME=?", new String[]{u_name});
                        }
                    } catch(Exception e){
                        Log.d("ERROR", "USER SERVICES COLUMN DOES NOT EXIST");
                    }
                }

            } catch (Exception e) {
                Log.d("ERROR", "USER COULD NOT BE FOUND");
            }
        }
        cursor.close();
        db.close();
        return b;
    }
    boolean deleteServiceEmployee(String u_name,String new_service){
        boolean b = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_Users  +" WHERE " + COL_UId;
        Cursor cursor = db.rawQuery(query, null);
        ContentValues contentValues = new ContentValues();
        while(cursor.moveToNext()) {
            try {
                int index = cursor.getColumnIndexOrThrow("USER_NAME");
                String username = cursor.getString(index);
                if (u_name.equals(username)) {
                    try {
                        int index_check = cursor.getColumnIndexOrThrow("USER_SERVICES_SERVICES");
                        String current_services = cursor.getString(index_check);
                        if(!current_services.equals("NO SERVICES") && !current_services.equals("")) {
                            b = true;
                            String new_str = current_services.replace(new_service,"");
                            if(new_str.startsWith(",")){
                                new_str=new_str.replaceFirst(",","");
                                contentValues.put(COL_UServices, new_str);
                                db.update("TABLE_Users", contentValues, "USER_NAME=?", new String[]{u_name});
                            }
                            else if(new_str.endsWith(",")){
                                new_str=new_str.substring(0,new_str.length()-1);
                                contentValues.put(COL_UServices, new_str);
                                db.update("TABLE_Users", contentValues, "USER_NAME=?", new String[]{u_name});
                            }
                            else if(!new_str.startsWith(",") && !new_str.endsWith(",") && !new_str.equals("")){
                                String new_str2=current_services.replace(","+new_service,"");
                                contentValues.put(COL_UServices, new_str2);
                                db.update("TABLE_Users", contentValues, "USER_NAME=?", new String[]{u_name});
                            }
                            else{
                                contentValues.put(COL_UServices, "NO SERVICES");
                                db.update("TABLE_Users", contentValues, "USER_NAME=?", new String[]{u_name});
                            }
                            System.out.println(new_str);
                        }
                    } catch(Exception e){
                        Log.d("ERROR", "USER SERVICES COLUMN DOES NOT EXIST");
                    }
                }

            } catch (Exception e) {
                Log.d("ERROR", "USER COULD NOT BE FOUND");
            }
        }
        cursor.close();
        db.close();
        return b;
    }

}
