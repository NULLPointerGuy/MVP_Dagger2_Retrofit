package com.karthik.todo.Todo.MVP;

/**
 * Created by karthikrk on 08/08/17.
 */

public class TodoPresenter implements TodoPresenterContract{
    private TodoViewContract view;

    public TodoPresenter(TodoViewContract view){
        this.view = view;
    }

    @Override
    public void onAddTodoClicked() {
        view.openAddTodoScreen();
    }
}
