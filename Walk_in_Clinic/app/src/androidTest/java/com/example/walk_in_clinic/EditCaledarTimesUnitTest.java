package com.example.walk_in_clinic;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class EditCaledarTimesUnitTest {
    @Rule
    public ActivityTestRule<ManageCalendarEmployee> mActivityTestRule= new ActivityTestRule<ManageCalendarEmployee>(ManageCalendarEmployee.class);
    private ManageCalendarEmployee mActivity=null;
    private TextView text;
    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkValidTimes() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.MANAGE_CALENDAR));
        String time;
        //Monday test
        text = mActivity.findViewById(R.id.monday_hours);
        text.setText("0900-1700");
        time = text.getText().toString();
        assertNotEquals("null",time);

        //Tuesday test
        text = mActivity.findViewById(R.id.tuesday_hours);
        text.setText("0900-1700");
        time = text.getText().toString();
        assertNotEquals("null",time);

        //Wednesday test
        text = mActivity.findViewById(R.id.wednesday_hours);
        text.setText("0900-1700");
        time = text.getText().toString();
        assertNotEquals("null",time);

        //Thursday test
        text = mActivity.findViewById(R.id.thursday_hours);
        text.setText("0900-1700");
        time = text.getText().toString();
        assertNotEquals("null",time);

        //Friday test
        text = mActivity.findViewById(R.id.friday_hours);
        text.setText("0900-1700");
        time = text.getText().toString();
        assertNotEquals("null",time);
    }
}
