package com.karthik.todo.AddTodo.MVP;

import com.karthik.todo.DB.Dbhander;
import com.karthik.todo.DB.Models.Todo;

import javax.inject.Inject;

/**
 * Created by karthikr on 8/8/17.
 */

public class AddTodoPresenter implements AddTodoPresenterContract {
    private AddTodoViewContract view;

    @Inject
    Dbhander dbhander;

    public AddTodoPresenter(AddTodoViewContract viewContract){
        this.view = viewContract;
        this.view.getAddTodoComponent().inject(this);
    }

    @Override
    public void saveTodo() {
        if(view.isTodoValidTitle() && view.isTodoValidDetail()){
            Todo todo = new Todo();
            todo.setTodoTitle(view.getTodoTitle());
            todo.setTodoDesc(view.getTodoDesc());
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
