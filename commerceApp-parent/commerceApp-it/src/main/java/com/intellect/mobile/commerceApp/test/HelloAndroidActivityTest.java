package com.intellect.mobile.commerceApp.test;

import android.test.ActivityInstrumentationTestCase2;
import com.intellect.mobile.commerceApp.*;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<HelloAndroidActivity> {

    public HelloAndroidActivityTest() {
        super(HelloAndroidActivity.class); 
    }

    public void testActivity() {
        HelloAndroidActivity activity = getActivity();
        assertNotNull(activity);
    }
}

