package com.example.walk_in_clinic;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class ManageServicesTest {
    @Rule
    public ActivityTestRule<ManageServicesActivity> mActivityTestRule= new ActivityTestRule<ManageServicesActivity>(ManageServicesActivity.class);
    private ManageServicesActivity mActivity=null;
    private TextView text;
    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkService() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.Services_Header));
        text= mActivity.findViewById(R.id.enter_service_name);
        text.setText("service01");
        String service= text.getText().toString();
        assertNotEquals("service02",service);
    }
}
