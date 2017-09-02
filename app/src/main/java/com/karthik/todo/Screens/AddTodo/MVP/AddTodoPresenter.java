package com.karthik.todo.Screens.AddTodo.MVP;

import com.karthik.todo.DB.Dbhander;
import com.karthik.todo.DB.Models.Todo;

import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by karthikr on 8/8/17.
 */

public class AddTodoPresenter implements AddTodoPresenterContract {
    private AddTodoViewContract view;
    private Dbhander dbhander;

    @Inject
    public AddTodoPresenter(AddTodoViewContract viewContract,Dbhander dbhander){
        this.view = viewContract;
        this.dbhander = dbhander;
    }

    @Override
    public void saveTodo() {
        if(view.isTodoValidTitle()){
            Todo todo = new Todo();
            todo.setTodoTitle(view.getTodoTitle());
            if(view.isReminderSet()){
                todo.setReminderSet(true);
                todo.setNotifyTime(view.getComposedReminderTime());
            }
            dbhander.saveTodo(todo);
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
}
