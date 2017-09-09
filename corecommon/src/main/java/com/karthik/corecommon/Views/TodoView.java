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
    void saveInCache(String date,String json);
    void loadImage(String url);
    boolean isCachePresent(String date);
    String getFromCache(String date);
    void setDashBoardTitle(String date);
    void setForeCastInfo(String foreCastInfo);
    boolean isLocationPermGranted();
    void askLocationPermission();
}
