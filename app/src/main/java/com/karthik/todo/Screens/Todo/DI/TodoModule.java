package com.karthik.todo.Screens.Todo.DI;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import android.content.Context;

import com.karthik.todo.APIService.ForecastAPIManager;
import com.karthik.todo.APIService.UnsplashAPIManager;
import com.karthik.todo.DB.Dbhander;
import com.karthik.todo.R;
import com.karthik.todo.Screens.Todo.MVP.TodoPresenter;
import com.karthik.todo.Screens.Todo.MVP.TodoPresenterContract;
import com.karthik.todo.Screens.Todo.MVP.TodoViewContract;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by karthikr on 8/8/17.
 */
@Module
public class TodoModule {
    private TodoViewContract view;
    private Context activityContext;

    public TodoModule(TodoViewContract view,Context activityContext){
        this.view = view;
        this.activityContext = activityContext;
    }

    @Provides
    TodoViewContract providesTodoView(){
        return view;
    }

    @Provides
    public Realm providesRealmDb(){
        return Realm.getDefaultInstance();
    }

    @Provides
    Dbhander providesDbHandler(Realm realm){
      return new Dbhander(realm);
    }

    @Provides
    FusedLocationProviderClient providesLocationClient(){
        return LocationServices.getFusedLocationProviderClient(activityContext);
    }

    @Provides
    TodoPresenterContract providesPresenter(TodoViewContract viewContract,
                                            Dbhander dbhander,
                                            UnsplashAPIManager unsplashAPIManager,
                                            ForecastAPIManager forecastAPIManager,
                                            FusedLocationProviderClient locationProviderClient,
                                            @Named("WEATHERSTRING") String weatherString){
        return new TodoPresenter(viewContract,dbhander,unsplashAPIManager,
                forecastAPIManager,locationProviderClient,weatherString);
    }

    @Provides
    @Named("WEATHERSTRING")
    String providesWeatherString(){
        return activityContext.getString(R.string.weather_info);
    }
}
