package com.karthik.todo;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
        initRealmConfig();
    }

    private void initRealmConfig(){
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public TodoComponent getComponent(){
        return component;
    }
}
