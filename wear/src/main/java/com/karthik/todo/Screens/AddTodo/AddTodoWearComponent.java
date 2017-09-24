package com.karthik.todo.Screens.AddTodo;

import com.karthik.corecommon.Components.TodoComponent;
import com.karthik.corecommon.Modules.AddTodoModule;
import com.karthik.corecommon.Scopes.AddTodo;

import dagger.Component;

/**
 * Created by karthikrk on 20/09/17.
 */

@AddTodo
@Component(dependencies = {TodoComponent.class},modules = {AddTodoModule.class})
public interface AddTodoWearComponent {
    void inject(AddTodoActivity addTodoActivity);
}
