package com.karthik.corecommon;

import android.app.Application;

import com.karthik.corecommon.Components.DaggerTodoComponent;
import com.karthik.corecommon.Components.TodoComponent;
import com.karthik.corecommon.Modules.TodoAppModule;

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
