package com.karthik.todo.Screens.AddTodo;

import com.karthik.corecommon.Components.TodoComponent;
import com.karthik.corecommon.Modules.AddTodoModule;
import com.karthik.corecommon.Presenters.AddTodoPresenter;
import com.karthik.corecommon.Scopes.AddTodo;
import com.karthik.corecommon.Views.AddTodoView;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by karthikr on 8/8/17.
 */
@AddTodo
@Component(dependencies = {TodoComponent.class},modules = {AddTodoModule.class})
public interface AddTodoComponent {
    void inject(AddTodoActivity addTodoView);
}
