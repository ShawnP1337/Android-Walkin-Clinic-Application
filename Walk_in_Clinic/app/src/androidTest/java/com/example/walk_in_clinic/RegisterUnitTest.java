package com.example.walk_in_clinic;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class RegisterUnitTest {
    @Rule
    public ActivityTestRule<RegisterActivity> mActivityTestRule= new ActivityTestRule<RegisterActivity>(RegisterActivity.class);
    private RegisterActivity mActivity=null;
    private TextView text;
    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkEmailAddress() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.REGISTRATION));
        text= mActivity.findViewById(R.id.email_address);
        text.setText("user1@google.ca");
        String email= text.getText().toString();
        assertNotEquals("user1@googleca.",email);
    }
}
