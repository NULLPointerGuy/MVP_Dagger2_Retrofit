package com.karthik.todo.Todo.MVP;

import com.karthik.todo.DB.Dbhander;

import javax.inject.Inject;

/**
 * Created by karthikrk on 08/08/17.
 */

public class TodoPresenter implements TodoPresenterContract{
    private TodoViewContract view;

    @Inject
    Dbhander dbhander;

    public TodoPresenter(TodoViewContract view){
        this.view = view;
        this.view.getTodoDashComponent().inject(this);
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
}
