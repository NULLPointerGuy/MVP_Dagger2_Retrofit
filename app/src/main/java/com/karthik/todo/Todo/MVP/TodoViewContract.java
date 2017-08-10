package com.karthik.todo.Todo.MVP;

import com.karthik.todo.DB.Models.Todo;
import com.karthik.todo.Todo.DI.TodoDashComponent;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by karthikrk on 08/08/17.
 */

public interface TodoViewContract {
    void onAddTodoClicked();
    void openAddTodoScreen();
    void hideEmptyTextAndShowTask();
    void showEmptyTextAndHideTask();
    TodoDashComponent getTodoDashComponent();
    void loadTasks(RealmResults<Todo> todo);
}
