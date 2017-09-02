package com.karthik.todo.Screens.AddTodo.MVP;

import java.util.Calendar;

/**
 * Created by karthikr on 8/8/17.
 */

public interface AddTodoPresenterContract {
    void saveTodo();
    void closeDb();
    void showDateSelection();
    void showTimeSelection();
    void setComposedDateAndTime(Calendar calendar);
}
