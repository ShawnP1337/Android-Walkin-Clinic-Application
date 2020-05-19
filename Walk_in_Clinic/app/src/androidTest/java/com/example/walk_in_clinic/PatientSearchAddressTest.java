package com.example.walk_in_clinic;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class PatientSearchAddressTest {
    @Rule
    public ActivityTestRule<PatientSearchActivity> mActivityTestRule= new ActivityTestRule<PatientSearchActivity>(PatientSearchActivity.class);
    private PatientSearchActivity mActivity = null;
    private TextView text;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkAddress() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.Header));
        text= mActivity.findViewById(R.id.address_search);
        text.setText("123 Street st.");
        String add = text.getText().toString();
        assertNotEquals("234 Guantanamo Rd.", add);
    }
}
