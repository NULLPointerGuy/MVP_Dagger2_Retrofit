package com.karthik.todo.Screens.Todo.MVP;

import com.karthik.todo.DB.Models.Todo;
import com.karthik.todo.Screens.Todo.DI.TodoDashComponent;

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
    void saveInCache(String date,String json);
    void loadImage(String url);
    boolean isCachePresent(String date);
    String getFromCache(String date);
    void setDashBoardTitle(String date);
}
