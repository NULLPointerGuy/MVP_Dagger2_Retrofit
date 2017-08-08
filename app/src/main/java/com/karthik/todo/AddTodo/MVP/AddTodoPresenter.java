package com.karthik.todo.AddTodo.MVP;

/**
 * Created by karthikr on 8/8/17.
 */

public class AddTodoPresenter implements AddTodoPresenterContract {
    private AddTodoViewContract view;

    public AddTodoPresenter(AddTodoViewContract viewContract){
        this.view = viewContract;
    }

    @Override
    public void saveTodo() {
        if(view.isTodoValidTitle() && view.isTodoValidDetail()){
            //save in local db
            return;
        }
        view.showAppropriateError();
    }
}
