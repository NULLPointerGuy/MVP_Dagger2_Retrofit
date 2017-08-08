package com.karthik.todo;

import com.karthik.todo.AddTodo.DI.AddTodoComponent;
import com.karthik.todo.AddTodo.DI.AddTodoModule;
import com.karthik.todo.Todo.DI.TodoDashComponent;
import com.karthik.todo.Todo.DI.TodoModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by karthikrk on 08/08/17.
 */
@Singleton
@Component (modules = {TodoAppModule.class})
public interface TodoComponent {
    TodoDashComponent plus(TodoModule todoModule);
    AddTodoComponent plus(AddTodoModule addTodoModule);
}
