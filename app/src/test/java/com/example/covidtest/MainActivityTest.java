package com.example.covidtest;


import android.view.View;

import androidx.test.rule.ActivityTestRule;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityRule.getActivity();
    }

    @Test
    public void validSpinner(){
        View view = mainActivity.findViewById(R.id.SPSort);
        assertNotNull(view);
    }
    @Test
    public void validEditText(){
        View view = mainActivity.findViewById(R.id.ETSearch);
        assertNotNull(view);
    }
    @Test
    public void validButton(){
        View view = mainActivity.findViewById(R.id.btSearch);
        assertNotNull(view);
    }
    @Test
    public void validRecyclerView(){
        View view = mainActivity.findViewById(R.id.recyclerViewCountries);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}