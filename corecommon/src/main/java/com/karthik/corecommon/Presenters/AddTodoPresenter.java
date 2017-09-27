package com.karthik.corecommon.Presenters;



import android.os.Bundle;


import com.firebase.jobdispatcher.Job;
import com.karthik.corecommon.Db.Dbhander;
import com.karthik.corecommon.Presenters.Contracts.AddTodoPresenterContract;
import com.karthik.corecommon.Views.AddTodoView;
import com.karthik.corecommon.Views.DateTimeSelectionView;
import com.karthik.corecommon.Views.ReminderManagedView;


import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by karthikr on 8/8/17.
 */

public class AddTodoPresenter implements AddTodoPresenterContract {
    private AddTodoView view;
    private Dbhander dbhander;
    private DateTimeSelectionView dateTimeSelectionView;
    private ReminderManagedView reminderManagedView;

    @Inject
    public AddTodoPresenter(AddTodoView viewContract,
                            DateTimeSelectionView dateTimeSelectionView,
                            ReminderManagedView reminderManagedView,
                            Dbhander dbhander){
        this.view = viewContract;
        this.dbhander = dbhander;
        this.dateTimeSelectionView = dateTimeSelectionView;
        this.reminderManagedView = reminderManagedView;
    }

    @Override
    public void saveTodo() {
        if(view.isTodoValidTitle()){
            boolean isReminderSet = reminderManagedView==null
                    ?false:reminderManagedView.isReminderSet();
            String reminderTime = reminderManagedView==null
                    ?null:reminderManagedView.getComposedReminderTime();

            dbhander.saveTodo(view.getTodoTitle(),isReminderSet,reminderTime);
            if(isReminderSet){
                int recentId = dbhander.getRecentTodoId();
                reminderManagedView.scheduleJob(reminderManagedView.getJobFor(recentId),recentId);
            }
            view.showSaveSuccessMessage();
            return;
        }
        view.showAppropriateError();
    }

    @Override
    public void closeDb() {
        dbhander.closeDb();
    }

    @Override
    public void showDateSelection() {
        dateTimeSelectionView.showDatePickerDialog();
    }

    @Override
    public void showTimeSelection() {
        dateTimeSelectionView.showTimePickerDialog();
    }

    @Override
    public void setComposedDateAndTime(Calendar cal) {
       String formattedDate = cal.get(Calendar.DAY_OF_MONTH)+" "
               +cal.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault())
               +" "+cal.get(Calendar.YEAR);
       String formattedTime = cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);
       dateTimeSelectionView.setDefaultDateAndTime(formattedDate,formattedTime);
    }

    @Override
    public long getDiffTime() {
        long diff  = dateTimeSelectionView.getSetTimeInMilli()-Calendar.getInstance().getTimeInMillis();
        return diff>0? (int) diff :0;
    }

    @Override
    public int getTimeInSec(long milli) {
        return (int) (milli/1000);
    }

    @Override
    public Bundle getBundleForJob(int taskId) {
        return reminderManagedView.getBundleForJob(view.getTodoTitle(),taskId,reminderManagedView.getComposedReminderTime());
    }

    @Override
    public Job getJobForTask(int taskId) {
        return reminderManagedView.getJobFor(taskId);
    }
}
