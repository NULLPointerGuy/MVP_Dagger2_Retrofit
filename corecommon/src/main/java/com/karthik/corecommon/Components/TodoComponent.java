package com.karthik.corecommon.Components;

import android.content.Context;
import android.content.SharedPreferences;

import com.karthik.corecommon.APIService.ForecastAPIManager;
import com.karthik.corecommon.APIService.UnsplashAPIManager;
import com.karthik.corecommon.Modules.AddTodoModule;
import com.karthik.corecommon.Modules.TodoApiModule;
import com.karthik.corecommon.Modules.TodoAppModule;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by karthikrk on 08/08/17.
 */
@Singleton
@Component (modules = {TodoAppModule.class,TodoApiModule.class})
public interface TodoComponent {
    Context context();
    UnsplashAPIManager UnsplashAPIManager();
    ForecastAPIManager ForecastAPIManager();
    SharedPreferences  SharedPreferences();
    Picasso Picasso();
}
