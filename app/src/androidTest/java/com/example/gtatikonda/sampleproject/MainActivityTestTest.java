package com.example.gtatikonda.sampleproject;

import android.app.DatePickerDialog;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.StringStartsWith;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.*;


/**
 * Created by gtatikonda on 1/24/2019.
 */
public class MainActivityTestTest {
    MainActivity mainActivity;
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule=new ActivityTestRule<MainActivity>(MainActivity.class);
    @Before
    public void setUp() throws Exception {
        mainActivity=activityActivityTestRule.getActivity();
    }

    @Test
     public void testDate(){
         onView(withText(startsWith("Copy"))).check(matches(isDisplayed()));
         onView(withId(R.id.datePickerTextView)).perform(click());
         //onView(withId(R.id.datePickerTextView)).check(matches(withError("error"))); --Pass
        onView(withClassName(Matchers.equalTo(DatePickerDialog.class.getName()))).perform(PickerActions.setDate(2019, 1, 25));

         //onView(withText(startsWith("clicked"))).                                                            --Pass
        // inRoot(withDecorView(not(activityActivityTestRule.getActivity().getWindow().getDecorView()))).
        // check(matches(ViewMatchers.isDisplayed()));

    }


    @After
    public void tearDown() throws Exception {
        mainActivity=null;
    }

    public Matcher<View> withError(final String error){
         return new TypeSafeMatcher<View>() {
             @Override
             protected boolean matchesSafely(View item) {
                 return ((TextView)item).getError().toString().equals(error);
             }

             @Override
             public void describeTo(Description description) {

             }
         };
    }

}