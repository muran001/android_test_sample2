package android_sample.muran001.com.android_test_sample.uitest;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import android_sample.muran001.com.android_test_sample.MainActivity;
import android_sample.muran001.com.android_test_sample.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.google.android.apps.common.testing.deps.guava.base.Preconditions.checkNotNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest { //extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(
            MainActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False so we can customize the intent per test method

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void init() {
        activityRule.launchActivity(new Intent());
        checkNotNull(activityRule.getActivity());
        activityRule.getActivity().finish();
    }

    @Test
    public void onCreate() {
        activityRule.launchActivity(new Intent());
        onView(ViewMatchers.withId(R.id.textView)).check(matches(withText("Hello world!")));
        onView(withId(R.id.button)).check(matches(withText("button1")));
        //getActivity().finish();
    }

    @Test
    public void onCreateWithIntent() {
        Intent intent = new Intent();
        intent.putExtra("text", "Good Night!");
        activityRule.launchActivity(intent);
        onView(withId(R.id.textView)).check(matches(withText("Good Night!")));
        //getActivity().finish();
    }

}
