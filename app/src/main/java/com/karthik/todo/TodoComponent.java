package com.karthik.todo;

import com.karthik.todo.Todo.TodoActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by karthikrk on 08/08/17.
 */
@Singleton
@Component (modules = {TodoAppModule.class})
public interface TodoComponent {
    void inject(TodoActivity activity);
}
