package com.karthik.corecommon.Views;

import android.os.Bundle;

import com.firebase.jobdispatcher.Job;

/**
 * Created by karthikrk on 27/09/17.
 */

public interface ReminderManagedView {
    boolean isReminderSet();
    String getComposedReminderTime();
    Bundle getBundleForJob(String title, int id, String composedTitle);
    Job getJobFor(int taskId);
    void scheduleJob(Job job,int taskId);
}
