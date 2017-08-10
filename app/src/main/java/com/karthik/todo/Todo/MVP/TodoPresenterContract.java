package com.karthik.todo.Todo.MVP;

/**
 * Created by karthikrk on 08/08/17.
 */

public interface TodoPresenterContract {
    void onAddTodoClicked();
    void loadTasks();
}
