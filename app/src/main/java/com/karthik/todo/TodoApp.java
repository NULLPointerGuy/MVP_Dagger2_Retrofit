package com.karthik.todo;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by karthikrk on 08/08/17.
 */

public class TodoApp extends Application{
    private TodoComponent component;
    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerTodoComponent.builder()
                .todoAppModule(new TodoAppModule(this))
                .build();
        Realm.init(this);
    }

    public TodoComponent getComponent(){
        return component;
    }
}
