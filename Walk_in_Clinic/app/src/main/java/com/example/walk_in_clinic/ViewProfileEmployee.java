package com.example.walk_in_clinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

import org.w3c.dom.Text;

public class ViewProfileEmployee extends AppCompatActivity  implements View.OnClickListener {

    //Initializing Button
    private Button editProfile;

    //Initializing TextViews
    private TextView addressText;
    private TextView phoneNumber;
    private TextView clinicName;
    private TextView acceptedInsuranceText;
    private TextView acceptedPaymentMethod;
    private String u_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_employee);

        //finding Button
        editProfile = (Button) findViewById(R.id.edit_profile_employee);
        //finding TextViews
        addressText = (TextView) findViewById(R.id.address_text);
        phoneNumber = (TextView) findViewById(R.id.phone_text);
        clinicName = (TextView) findViewById(R.id.clinic_name_text);
        acceptedInsuranceText = (TextView) findViewById(R.id.accepted_insurance_text);
        acceptedPaymentMethod = (TextView) findViewById(R.id.accepted_payment_text);

        //extracting string from previous activity
        Intent u_name_extract = getIntent();
        u_name = u_name_extract.getStringExtra("USERNAME");
        System.out.println(u_name);

        //setting text views to user info
        addressText.setText(DatabaseBrain.theDB.getAddress(u_name));
        phoneNumber.setText(DatabaseBrain.theDB.getPhone(u_name));
        clinicName.setText(DatabaseBrain.theDB.getClinicName(u_name));
        acceptedPaymentMethod.setText(DatabaseBrain.theDB.getPayment(u_name));
        acceptedInsuranceText.setText(DatabaseBrain.theDB.getInsurance(u_name));
        //setting OnClick Listeners
        editProfile.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(editProfile.getId()==v.getId()){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.edit_profile_employee, null);
            final EditText new_address = (EditText) dialogView.findViewById(R.id.editAddress);
            final EditText new_phone = (EditText) dialogView.findViewById(R.id.editPhoneNumber);
            final EditText new_clinic_name = (EditText) dialogView.findViewById(R.id.editClinicName);
            final EditText new_accepted_insurance = (EditText) dialogView.findViewById(R.id.editAcceptedInsurance);
            final EditText new_payment_methods = (EditText) dialogView.findViewById(R.id.editAcceptedPaymentMethods);
            final Button confirmEdit = (Button) dialogView.findViewById(R.id.confirmEditProfile);
            dialogBuilder.setView(dialogView);
            final AlertDialog b = dialogBuilder.create();
            b.show();
            confirmEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!new_address.getText().toString().trim().equals("") && !new_phone.getText().toString().trim().equals("") && !new_clinic_name.getText().toString().trim().equals("") && !new_accepted_insurance.getText().toString().trim().equals("") && !new_payment_methods.getText().toString().equals("")){
                        if(Pattern.matches("^[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9]$",new_phone.getText().toString().trim())) {
                            System.out.println(DatabaseBrain.theDB.updateProfile(u_name, new_address.getText().toString().trim(), new_phone.getText().toString().trim(), new_clinic_name.getText().toString().trim(), new_accepted_insurance.getText().toString().trim(), new_payment_methods.getText().toString().trim()));
                            System.out.println("This is the new methods: "+DatabaseBrain.theDB.addClinicInformation(u_name));
                            addressText.setText(DatabaseBrain.theDB.getAddress(u_name));
                            phoneNumber.setText(DatabaseBrain.theDB.getPhone(u_name));
                            clinicName.setText(DatabaseBrain.theDB.getClinicName(u_name));
                            acceptedInsuranceText.setText(DatabaseBrain.theDB.getInsurance(u_name));
                            acceptedPaymentMethod.setText(DatabaseBrain.theDB.getPayment(u_name));
                            b.dismiss();
                        }else{
                            Toast.makeText(ViewProfileEmployee.this,"Please enter a valid phone number (hint:###-###-###)",Toast.LENGTH_LONG).show();
                        }
                    } else{
                        Toast.makeText(ViewProfileEmployee.this,"Please complete all fields",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
