package com.karthik.myapplication.Screens.Todo;

import com.karthik.corecommon.Components.TodoComponent;
import com.karthik.corecommon.Modules.TodoModule;
import com.karthik.corecommon.Scopes.TodoScope;

import dagger.Component;

/**
 * Created by karthikrk on 18/09/17.
 */

@TodoScope
@Component(dependencies={TodoComponent.class}, modules={TodoModule.class})
public interface TodoDashWearComponent {
    void inject(TodoActivity todoActivity);
}
