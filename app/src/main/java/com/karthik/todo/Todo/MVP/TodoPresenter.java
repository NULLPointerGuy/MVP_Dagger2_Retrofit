package com.karthik.todo.Todo.MVP;

import android.util.Log;

import com.karthik.todo.APIService.UnsplashAPIManager;
import com.karthik.todo.DB.Dbhander;
import com.karthik.todo.Pojo.Unsplash;

import javax.inject.Inject;

/**
 * Created by karthikrk on 08/08/17.
 */

public class TodoPresenter implements TodoPresenterContract,UnsplashAPIManager.UnsplashAPICallback{
    private TodoViewContract view;

    @Inject
    Dbhander dbhander;
    @Inject
    UnsplashAPIManager unsplashAPIManager;

    public TodoPresenter(TodoViewContract view){
        this.view = view;
        this.view.getTodoDashComponent().inject(this);
        unsplashAPIManager.setUnsplashAPICallbacks(this);
    }

    @Override
    public void onAddTodoClicked() {
        view.openAddTodoScreen();
    }

    @Override
    public void loadTasks() {
        if(dbhander.isDbEmpty()){
            view.showEmptyTextAndHideTask();
            return;
        }
        view.hideEmptyTextAndShowTask();
        view.loadTasks(dbhander.getAllTodo());
    }

    @Override
    public void getUnsplashImages() {
        unsplashAPIManager.getPhotosList("thunder");
    }

    @Override
    public void onSuccess(Unsplash unsplash) {
        Log.e("RESPONSE:",unsplash.toString());
    }

    @Override
    public void onFailure(String message) {
        Log.e("FAILED",message);
    }
}
