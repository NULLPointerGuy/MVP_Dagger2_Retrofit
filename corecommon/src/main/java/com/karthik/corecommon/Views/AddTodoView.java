package com.karthik.corecommon.Views;

import android.os.Bundle;

import com.firebase.jobdispatcher.Job;

/**
 * Created by karthikr on 8/8/17.
 */

public interface AddTodoView {
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
    Job getJobFor(int taskId);
    void scheduleJob(Job job,int taskId);
}
