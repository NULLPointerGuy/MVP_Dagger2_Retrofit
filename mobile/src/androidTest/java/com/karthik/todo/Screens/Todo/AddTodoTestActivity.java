package com.karthik.todo.Screens.Todo;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.karthik.todo.R;
import com.karthik.todo.Screens.AddTodo.AddTodoActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Created by karthikrk on 25/10/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddTodoTestActivity {
    @Rule
    public ActivityTestRule<AddTodoActivity> tasksActivityTestRule =
            new ActivityTestRule<>(AddTodoActivity.class);


    @Test
    public void tryToSaveEmptyText(){
        onView(withId(R.id.save_fab)).perform(click());
        onView(withId(R.id.todotitle)).check(matches(hasErrorText("Please enter valid todo title")));
    }

    @Test
    public void reminderShouldBeIntiallyNotChecked(){
        onView(withId(R.id.reminder)).check(matches(not(isChecked())));
        onView(withId(R.id.reminder)).perform(click());
        onView(withId(R.id.reminder)).check(matches(isChecked()));
    }

    @Test
    public void checkIfDateAndTimeDisplayedOnReminderCheck(){
        onView(withId(R.id.reminder)).perform(click());
        onView(withId(R.id.choosedate)).check(matches(isDisplayed()));
        onView(withId(R.id.choosetime)).check(matches(isDisplayed()));
    }

    @Test
    public void timeDialogDisplayed(){
        onView(withId(R.id.reminder)).perform(click());
        onView(withId(R.id.choosetime)).perform(click());
        onView(withText("OK")).check(matches(isDisplayed()));
    }

    @Test
    public void dateDialogDisplayed(){
        onView(withId(R.id.reminder)).perform(click());
        onView(withId(R.id.choosedate)).perform(click());
        onView(withText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR))))
                .check(matches(isDisplayed()));
        onView(withText("OK")).check(matches(isDisplayed()));
    }
}
