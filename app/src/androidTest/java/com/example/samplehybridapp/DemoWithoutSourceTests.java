package com.example.samplehybridapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.findElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.getText;
import static androidx.test.espresso.web.webdriver.DriverAtoms.webClick;
import static androidx.test.espresso.web.webdriver.DriverAtoms.webKeys;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DemoWithoutSourceTests {

    private static final String ACTIVITY_TO_BE_TESTED = "com.example.samplehybridapp.MainActivity";

    @Before
    public void setup() {
        System.out.println("start setup...................");
        Class activityClass = null;
        try {
            activityClass = Class.forName(ACTIVITY_TO_BE_TESTED);
        } catch (ClassNotFoundException e) {
            System.out.println("error...................");
            e.printStackTrace();
            return;
        }

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.setInTouchMode(true);

        final String targetPackage = instrumentation.getTargetContext().getPackageName();
        Intent startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.setClassName(targetPackage, activityClass.getName());
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        instrumentation.startActivitySync(startIntent);
        instrumentation.waitForIdleSync();
    }

    @Test
    public void test1() throws InterruptedException {
        onView(withId(R.id.navigation_dashboard)).perform(click());
        onView(withText("This is dashboard page")).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_notifications)).perform(click());
        onView(withText("This is notifications page")).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_home))
                .check(matches(isEnabled()))
                .perform(click());

        onWebView(withId(R.id.home_webview)).forceJavascriptEnabled();
        onWebView()
                .withElement(findElement(Locator.NAME, "username"))
                .perform(webKeys("test"))
                .withElement(findElement(Locator.NAME, "password"))
                .perform(webKeys("password"))
                .withElement(findElement(Locator.XPATH, "//input[@value=\"login\"]"))
                .perform(webClick());

        onWebView()
                .withElement(findElement(Locator.XPATH, "//h1"))
                .check(webMatches(getText(), equalTo("Example Domain")));
        onWebView()
                .withElement(findElement(Locator.XPATH, "//a"))
                .check(webMatches(getText(), containsString("More information")));
        Thread.sleep(5000);
    }

    @After
    public void tearDown() {
        // TBD
    }
}