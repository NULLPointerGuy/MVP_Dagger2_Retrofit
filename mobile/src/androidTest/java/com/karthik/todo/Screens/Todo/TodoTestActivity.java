package com.karthik.todo.Screens.Todo;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.karthik.corecommon.Db.Dbhander;
import com.karthik.todo.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


/**
 * Created by karthikrk on 24/10/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TodoTestActivity {
    private Dbhander dbhander;
    private Realm realmdb;
    @Rule
    public ActivityTestRule<TodoActivity> mTasksActivityTestRule =
            new ActivityTestRule<>(TodoActivity.class);

    @Before
    public void setup(){
        realmdb = Realm.getDefaultInstance();
        dbhander = new Dbhander(realmdb);
    }

    @Test
    public void emptyTaskShown(){
        realmdb.beginTransaction();
        realmdb.deleteAll();
        realmdb.commitTransaction();
        relaunchActivity();
        onView(withId(R.id.empty_text)).check(matches(isDisplayed()));
        onView(withId(R.id.dashTitle)).check(matches(isDisplayed()));
    }


    @Test
    public void taskShownWhenListIsNonEmpty(){
        dbhander.saveTodo("temp",false,null);
        relaunchActivity();
        onView(withId(R.id.taskList)).check(matches(isDisplayed()));
        onView(withId(R.id.dashTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.taskList)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0,click()));
    }

    @Test
    public void checkWhetherFabIsDisplayedAndClickable(){
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
        onView(withId(R.id.fab)).check(matches(isClickable()));
    }

    private void relaunchActivity() {
        mTasksActivityTestRule.finishActivity();
        mTasksActivityTestRule.launchActivity(new Intent(Intent.ACTION_MAIN));
    }
}
