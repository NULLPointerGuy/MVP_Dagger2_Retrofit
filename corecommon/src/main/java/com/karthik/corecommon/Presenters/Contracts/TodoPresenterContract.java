package com.karthik.corecommon.Presenters.Contracts;

/**
 * Created by karthikrk on 08/08/17.
 */

public interface TodoPresenterContract {
    void onAddTodoClicked();
    void setDashTitle();
    void loadTasks();
    void getUnsplashImages(String genre);
    void getLocation();
}
