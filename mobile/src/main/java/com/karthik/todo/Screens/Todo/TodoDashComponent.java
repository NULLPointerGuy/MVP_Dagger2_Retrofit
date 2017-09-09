package com.karthik.todo.Screens.Todo;

import com.karthik.corecommon.Components.TodoComponent;
import com.karthik.corecommon.Modules.TodoModule;
import com.karthik.corecommon.Presenters.TodoPresenter;
import com.karthik.corecommon.Scopes.TodoScope;

import dagger.Component;

/**
 * Created by karthikr on 8/8/17.
 */
@TodoScope
@Component(dependencies={TodoComponent.class}, modules={TodoModule.class})
public interface TodoDashComponent {
    void inject(TodoActivity todoActivity);
}
