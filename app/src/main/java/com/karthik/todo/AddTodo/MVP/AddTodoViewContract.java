package com.karthik.todo.AddTodo.MVP;

import com.karthik.todo.AddTodo.DI.AddTodoComponent;

/**
 * Created by karthikr on 8/8/17.
 */

public interface AddTodoViewContract {
    boolean isTodoValidTitle();
    boolean isTodoValidDetail();
    void showAppropriateError();
    AddTodoComponent getAddTodoComponent();
    String getTodoTitle();
    String getTodoDesc();
    void showSaveSuccessMessage();
}
