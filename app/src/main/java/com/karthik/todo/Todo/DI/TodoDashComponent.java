package com.karthik.todo.Todo.DI;

import com.karthik.todo.Todo.MVP.TodoPresenter;
import com.karthik.todo.Todo.TodoActivity;

import dagger.Subcomponent;

/**
 * Created by karthikr on 8/8/17.
 */
@TodoScope
@Subcomponent (modules = {TodoModule.class})
public interface TodoDashComponent {
    void inject(TodoActivity todoActivity);
    void inject(TodoPresenter todoPresenter);
}
