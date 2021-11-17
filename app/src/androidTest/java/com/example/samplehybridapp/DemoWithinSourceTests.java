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

import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

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
public class DemoWithinSourceTests {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        onWebView(withId(R.id.home_webview)).forceJavascriptEnabled();
    }

    @Test
    public void test1() {
        onView(withId(R.id.navigation_dashboard)).perform(click());
        onView(withText("This is dashboard page")).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_notifications)).perform(click());
        onView(withText("This is notifications page")).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_home))
                .check(matches(isEnabled()))
                .perform(click());
    }

    @Test
    public void test2() throws InterruptedException {
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