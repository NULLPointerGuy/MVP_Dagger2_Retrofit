package com.karthik.todo.Screens.AddTodo.MVP;

import com.karthik.todo.DB.Dbhander;
import com.karthik.todo.DB.Models.Todo;

import javax.inject.Inject;

/**
 * Created by karthikr on 8/8/17.
 */

public class AddTodoPresenter implements AddTodoPresenterContract {
    private AddTodoViewContract view;
    private Dbhander dbhander;

    @Inject
    public AddTodoPresenter(AddTodoViewContract viewContract,Dbhander dbhander){
        this.view = viewContract;
        this.dbhander = dbhander;
    }

    @Override
    public void saveTodo() {
        if(view.isTodoValidTitle()){
            Todo todo = new Todo();
            todo.setTodoTitle(view.getTodoTitle());
            dbhander.saveTodo(todo);
            view.showSaveSuccessMessage();
            return;
        }
        view.showAppropriateError();
    }

    @Override
    public void closeDb() {
        dbhander.closeDb();
    }
}
