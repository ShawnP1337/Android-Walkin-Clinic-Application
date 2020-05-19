package com.example.walk_in_clinic;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class ClinicNameUnitTest {
    @Rule
    public ActivityTestRule<ClinicPage> mActivityTestRule= new ActivityTestRule<ClinicPage>(ClinicPage.class);
    private ClinicPage mActivity = null;
    private TextView text;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkClinicName() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.Header));
        text= mActivity.findViewById(R.id.name_text);
        text.setText("Clinic Test Name");
        String name = text.getText().toString();
        assertNotEquals("cl te na", name);
    }
}
