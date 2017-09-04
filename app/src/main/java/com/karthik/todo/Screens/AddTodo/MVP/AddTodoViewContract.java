package com.karthik.todo.Screens.AddTodo.MVP;

import android.os.Bundle;

import com.firebase.jobdispatcher.Job;
import com.karthik.todo.Screens.AddTodo.DI.AddTodoComponent;

import java.util.Calendar;

/**
 * Created by karthikr on 8/8/17.
 */

public interface AddTodoViewContract {
    boolean isTodoValidTitle();
    void showAppropriateError();
    String getTodoTitle();
    void showSaveSuccessMessage();
    void showDatePickerDialog();
    void showTimePickerDialog();
    void setDefaultDateAndTime(String formattedDate, String formattedTime);
    boolean isReminderSet();
    String getComposedReminderTime();
    Bundle getBundleForJob(String title,int id,String composedTitle);
    long getSetTimeInMilli();
}
