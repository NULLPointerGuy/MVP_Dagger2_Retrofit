package com.karthik.corecommon.Views;

import com.karthik.corecommon.Models.Todo;

import io.realm.RealmResults;

/**
 * Created by karthikrk on 08/08/17.
 */

public interface TodoView {
    void onAddTodoClicked();
    void openAddTodoScreen();
    void hideEmptyTextAndShowTask();
    void showEmptyTextAndHideTask();
    void loadTasks(RealmResults<Todo> todo);
}
