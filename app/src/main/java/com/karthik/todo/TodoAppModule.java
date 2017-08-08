package com.karthik.todo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by karthikrk on 08/08/17.
 */

@Module
public class TodoAppModule {
    private Application application;

    public TodoAppModule(Application application){
        this.application = application;
    }

    @Provides @Singleton
    public Context providesContext(){
        return application;
    }

    @Provides @Singleton
    public SharedPreferences providesSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
