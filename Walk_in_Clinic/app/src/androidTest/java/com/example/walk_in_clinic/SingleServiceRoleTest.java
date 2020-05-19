package com.example.walk_in_clinic;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class SingleServiceRoleTest {
    @Rule
    public ActivityTestRule<Service> mActivityTestRule= new ActivityTestRule<Service>(Service.class);
    private Service mActivity = null;
    private TextView text;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkAddress() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.singleServiceLayout));
        text= mActivity.findViewById(R.id.singleServiceRole);
        text.setText("employee");
        String role = text.getText().toString();
        assertNotEquals("admin", role);
    }
}
