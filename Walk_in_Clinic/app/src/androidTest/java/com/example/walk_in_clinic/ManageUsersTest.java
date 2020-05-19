package com.example.walk_in_clinic;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class ManageUsersTest {
    @Rule
    public ActivityTestRule<ManageUsersActivity> mActivityTestRule= new ActivityTestRule<ManageUsersActivity>(ManageUsersActivity.class);
    private ManageUsersActivity mActivity=null;
    private TextView text;
    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkEmailAddress() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.Users_Header));
        text= mActivity.findViewById(R.id.manage_username);
        text.setText("user01");
        String user= text.getText().toString();
        assertNotEquals("user02",user);
    }
}
