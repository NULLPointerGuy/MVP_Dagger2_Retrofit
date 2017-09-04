package com.karthik.todo.Screens.AddTodo.MVP;



import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.karthik.todo.DB.Dbhander;
import com.karthik.todo.Services.BackgroundJobService;

import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by karthikr on 8/8/17.
 */

public class AddTodoPresenter implements AddTodoPresenterContract {
    private AddTodoViewContract view;
    private Dbhander dbhander;
    private FirebaseJobDispatcher jobDispatcher;

    @Inject
    public AddTodoPresenter(AddTodoViewContract viewContract, Dbhander dbhander, FirebaseJobDispatcher jobDispatcher){
        this.view = viewContract;
        this.dbhander = dbhander;
        this.jobDispatcher = jobDispatcher;
    }

    @Override
    public void saveTodo() {
        if(view.isTodoValidTitle()){
            dbhander.saveTodo(view.getTodoTitle(),view.isReminderSet(),view.getComposedReminderTime());
            if(view.isReminderSet()){
                jobDispatcher.schedule(getJobFor(dbhander.getRecentTodoId()));
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
        return (int) (getDiffTime()/1000);
    }

    private Job getJobFor(int recentTodoId) {

       int sec = getTimeInSec(getDiffTime());
       return jobDispatcher.newJobBuilder()
                .setService(BackgroundJobService.class)
                .setTag(String.valueOf(recentTodoId))
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(sec,sec))
                .setReplaceCurrent(false)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setExtras(view.getBundleForJob(view.getTodoTitle(),recentTodoId,view.getComposedReminderTime()))
                .build();
    }
}
