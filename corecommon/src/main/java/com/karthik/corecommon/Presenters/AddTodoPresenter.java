package com.karthik.corecommon.Presenters;



import android.os.Bundle;


import com.firebase.jobdispatcher.Job;
import com.karthik.corecommon.Db.Dbhander;
import com.karthik.corecommon.Presenters.Contracts.AddTodoPresenterContract;
import com.karthik.corecommon.Views.AddTodoView;


import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by karthikr on 8/8/17.
 */

public class AddTodoPresenter implements AddTodoPresenterContract {
    private AddTodoView view;
    private Dbhander dbhander;

    @Inject
    public AddTodoPresenter(AddTodoView viewContract, Dbhander dbhander){
        this.view = viewContract;
        this.dbhander = dbhander;
    }

    @Override
    public void saveTodo() {
        if(view.isTodoValidTitle()){
            dbhander.saveTodo(view.getTodoTitle(),view.isReminderSet(),view.getComposedReminderTime());
            if(view.isReminderSet()){
                int recentId = dbhander.getRecentTodoId();
                view.scheduleJob(view.getJobFor(recentId),recentId);
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
        view.showDatePickerDialog();
    }

    @Override
    public void showTimeSelection() {
        view.showTimePickerDialog();
    }

    @Override
    public void setComposedDateAndTime(Calendar cal) {
       String formattedDate = cal.get(Calendar.DAY_OF_MONTH)+" "
               +cal.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault())
               +" "+cal.get(Calendar.YEAR);
       String formattedTime = cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);
       view.setDefaultDateAndTime(formattedDate,formattedTime);
    }

    @Override
    public long getDiffTime() {
        long diff  = view.getSetTimeInMilli()-Calendar.getInstance().getTimeInMillis();
        return diff>0? (int) diff :0;
    }

    @Override
    public int getTimeInSec(long milli) {
        return (int) (milli/1000);
    }

    @Override
    public Bundle getBundleForJob(int taskId) {
        return view.getBundleForJob(view.getTodoTitle(),taskId,view.getComposedReminderTime());
    }

    @Override
    public Job getJobForTask(int taskId) {
        return view.getJobFor(taskId);
    }
}
