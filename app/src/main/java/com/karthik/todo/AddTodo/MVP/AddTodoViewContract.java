package com.karthik.todo.AddTodo.MVP;

/**
 * Created by karthikr on 8/8/17.
 */

public interface AddTodoViewContract {
    boolean isTodoValidTitle();
    boolean isTodoValidDetail();
    void showAppropriateError();
}
