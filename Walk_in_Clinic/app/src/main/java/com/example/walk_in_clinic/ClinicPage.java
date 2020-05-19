package com.example.walk_in_clinic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ClinicPage extends AppCompatActivity implements View.OnClickListener {

    private TextView clinicName;
    private TextView clinicRating;
    private TextView clinicAddress;
    private String cName;
    private String cRating;
    private String cAddress;
    private Button bookAppointment;
    private Button rateClinic;
    private Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_page);

        //finding Text Views
        clinicName = (TextView) findViewById(R.id.name_text);
        clinicRating = (TextView) findViewById(R.id.rating_text);
        clinicAddress = (TextView) findViewById(R.id.address_text);

        //finding buttons
        bookAppointment = (Button) findViewById(R.id.appointmentButton);
        rateClinic = (Button) findViewById(R.id.rate_clinic);
        exit = (Button) findViewById(R.id.back);

        //getting intent and extras
        Intent intent = getIntent();
        if(intent.hasExtra("CLINIC NAME") && intent.hasExtra("CLINIC RATING") && intent.hasExtra("CLINIC ADDRESS")){
            cName = intent.getStringExtra("CLINIC NAME");
            cRating = intent.getStringExtra("CLINIC RATING");
            cAddress = intent.getStringExtra("CLINIC ADDRESS");
            clinicName.setText(cName);
            clinicRating.setText(cRating);
            clinicAddress.setText(cAddress);
        }
        //setting onClickListeners
        bookAppointment.setOnClickListener(this);
        rateClinic.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (bookAppointment.getId()==v.getId()){
            Intent intent1 = new Intent(getApplicationContext(),BookAppointmentActivity.class);
            intent1.putExtra("CLINIC_NAME",cName);
            startActivity(intent1);
        }
        else if (rateClinic.getId()==v.getId()){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.rate_clinic_layout, null);
            final RadioGroup rating_group = (RadioGroup) dialogView.findViewById(R.id.rating);
            final RadioButton one = (RadioButton) dialogView.findViewById(R.id.one);
            final RadioButton two = (RadioButton) dialogView.findViewById(R.id.two);
            final RadioButton three = (RadioButton) dialogView.findViewById(R.id.three);
            final RadioButton four = (RadioButton) dialogView.findViewById(R.id.four);
            final RadioButton five = (RadioButton) dialogView.findViewById(R.id.five);
            final Button confirmRating = (Button) dialogView.findViewById(R.id.confirmRateButton);
            dialogBuilder.setView(dialogView);
            final AlertDialog b = dialogBuilder.create();
            b.show();
            confirmRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(confirmRating.getId()==v.getId()){
                        if(rating_group.getCheckedRadioButtonId()!=-1){
                            if (rating_group.getCheckedRadioButtonId()==one.getId()){
                                DatabaseBrain.theDB.addClinicRating(cName,"1.0");
                                b.dismiss();
                            }
                            else if(rating_group.getCheckedRadioButtonId()==two.getId()){
                                DatabaseBrain.theDB.addClinicRating(cName,"2.0");
                                b.dismiss();
                            }
                            else if(rating_group.getCheckedRadioButtonId()==three.getId()){
                                DatabaseBrain.theDB.addClinicRating(cName,"3.0");
                                b.dismiss();
                            }
                            else if(rating_group.getCheckedRadioButtonId()==four.getId()){
                                DatabaseBrain.theDB.addClinicRating(cName,"4.0");
                                b.dismiss();
                            }
                            else if(rating_group.getCheckedRadioButtonId()==five.getId()){
                                DatabaseBrain.theDB.addClinicRating(cName,"5.0");
                                b.dismiss();
                            }
                        }else{
                            Toast.makeText(ClinicPage.this,"Please select a rating",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
        else if(exit.getId()==v.getId()){
            Intent intent = new Intent(getApplicationContext(),PatientSearchActivity.class);
            startActivity(intent);
        }
    }
}
