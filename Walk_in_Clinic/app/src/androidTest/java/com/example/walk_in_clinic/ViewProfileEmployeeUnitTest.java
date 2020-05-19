package com.example.walk_in_clinic;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class ViewProfileEmployeeUnitTest {
    @Rule
    public ActivityTestRule<ViewProfileEmployee> mActivityTestRule= new ActivityTestRule<ViewProfileEmployee>(ViewProfileEmployee.class);
    private ViewProfileEmployee mActivity=null;
    private TextView text;
    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkValidInfo() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.address_label));
        String info;
        //Address test
        text = mActivity.findViewById(R.id.address_text);
        text.setText("123 Test Road");
        info = text.getText().toString();
        assertNotEquals("null",info);

        //Phone test
        text = mActivity.findViewById(R.id.phone_text);
        text.setText("1234567890");
        info = text.getText().toString();
        assertNotEquals("null",info);

        //Clinic Name test
        text = mActivity.findViewById(R.id.clinic_name_text);
        text.setText("Clinic Name");
        info = text.getText().toString();
        assertNotEquals("null",info);

        //Insurance test
        text = mActivity.findViewById(R.id.accepted_insurance_text);
        text.setText("0123 0123 0123");
        info = text.getText().toString();
        assertNotEquals("null",info);

        //Payment Method test
        text = mActivity.findViewById(R.id.accepted_payment_text);
        text.setText("Mastercard: 9999 9999 9999 9999");
        info = text.getText().toString();
        assertNotEquals("null",info);
    }
}
