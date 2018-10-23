package com.mytaxi.android_demo;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.AdapterView;

import com.mytaxi.android_demo.activities.AuthenticationActivity;
import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class EspressoTests {

    private static final String userName = "crazydog335";
    private static final String password = "venture";
    private static String driverName = "sa";

    @Rule
    public ActivityTestRule<AuthenticationActivity> authenticationActivityRule = new ActivityTestRule<>(AuthenticationActivity.class, false, true);
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, false, true);
    UiController uiController;
    private MainActivity mActivity = null;

    //@AfterClass  Won't work now as need to press back button to come back to app.
    public static void tearDown() {
        try {

            onView(withContentDescription("Open navigation drawer")).perform(click());
            onView(withText("Logout")).perform(click());

        }
        catch (NoMatchingViewException e) {
            Log.e("Logout", "Logout Button Not found exception.");
        }
        catch (Exception e) {

            Log.e("Unknown Exception", "Unknown Expcetion found in teardown.");

        }
    }

    @Before
    public void setActivity() {
        mActivity = mActivityRule.getActivity();
        // IdlingRegistry.getInstance().register(ForIdlingRegistry.getResource());
    }

    @Test
    public void myTaxiTest() throws InterruptedException {

//        try {

            onView(withId(R.id.edt_username))
                    .perform(typeText(userName), closeSoftKeyboard());
            onView(withId(R.id.edt_password))
                    .perform(typeText(password), closeSoftKeyboard());
            onView(withId(R.id.btn_login))
                    .perform(click());

            // uiController.loopMainThreadForAtLeast(500);   Throwing unknown error.

            Thread.sleep(2000); //Adding it as application was not able to sync in time.

            onView(withId(R.id.textSearch)).check(matches(isDisplayed()));

            onView(withId(R.id.textSearch))
                    .perform(typeText(driverName))
                    .check(matches(isDisplayed()));

            Thread.sleep(2000);    

            onView(withText("Sarah Scott"))
                    .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                    .perform(click());

            /*
                Test is Flaky sometimes it works sometimes it doesn't. Laptop slowness issue maybe?
            onData(anything()).inAdapterView(isAssignableFrom(AdapterView.class)).atPosition(2).perform(click());
             */

         /*   onView(withText("Sarah Scott"))
                    .perform(click())
                    .check(matches(isDisplayed()));*/

            onView(withText("Sarah Scott"))
                    .check(matches(isDisplayed()));
            onView(withId(R.id.fab))
                    .perform(click());


/*        } catch (NoMatchingViewException e) {

            Log.e("Error in Login Test", "No matching View found. Make sure app is on Login Screen.");

        } catch (Exception e) {

            Log.e("Unknown Error", "Unknown Error found in Login Test.");

        }*/

    }

}
