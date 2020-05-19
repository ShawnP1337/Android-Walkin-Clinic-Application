package com.example.walk_in_clinic;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class SingleAppointmentHourTest {
    @Rule
    public ActivityTestRule<Appointment> mActivityTestRule= new ActivityTestRule<Appointment>(Appointment.class);
    private Appointment mActivity = null;
    private TextView text;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkAppointmentDay() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.singleAppointmentLayout));
        text= mActivity.findViewById(R.id.app_time);
        text.setText("0900");
        String hour = text.getText().toString();
        assertNotEquals("1000", hour);
    }
}
