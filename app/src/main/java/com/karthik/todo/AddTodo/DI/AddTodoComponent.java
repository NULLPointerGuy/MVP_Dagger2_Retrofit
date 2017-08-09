package com.karthik.todo.AddTodo.DI;

import com.karthik.todo.AddTodo.AddTodoActivity;
import com.karthik.todo.AddTodo.MVP.AddTodoPresenter;

import dagger.Subcomponent;

/**
 * Created by karthikr on 8/8/17.
 */
@AddTodo
@Subcomponent (modules = {AddTodoModule.class})
public interface AddTodoComponent{
    void inject(AddTodoActivity addTodoActivity);
    void inject(AddTodoPresenter addTodoPresenter);
}
