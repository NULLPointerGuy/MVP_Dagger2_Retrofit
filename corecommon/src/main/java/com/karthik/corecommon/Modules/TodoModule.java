package com.karthik.corecommon.Modules;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import android.content.Context;

import com.karthik.corecommon.APIService.ForecastAPIManager;
import com.karthik.corecommon.APIService.UnsplashAPIManager;
import com.karthik.corecommon.Db.Dbhander;
import com.karthik.corecommon.Presenters.Contracts.TodoPresenterContract;
import com.karthik.corecommon.Presenters.TodoPresenter;
import com.karthik.corecommon.R;
import com.karthik.corecommon.Views.TodoView;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by karthikr on 8/8/17.
 */
@Module
public class TodoModule {
    private TodoView view;
    private Context activityContext;

    public TodoModule(TodoView view,Context activityContext){
        this.view = view;
        this.activityContext = activityContext;
    }

    @Provides
    TodoView providesTodoView(){
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
    @Named("WEATHERSTRING")
    String providesWeatherString(){
        return activityContext.getString(R.string.weather_info);
    }

    @Provides
    TodoPresenterContract providesPresenter(TodoView viewContract,
                                            Dbhander dbhander,
                                            UnsplashAPIManager unsplashAPIManager,
                                            ForecastAPIManager forecastAPIManager,
                                            FusedLocationProviderClient locationProviderClient,
                                            @Named("WEATHERSTRING") String weatherString){
        return new TodoPresenter(viewContract,dbhander,unsplashAPIManager,
                forecastAPIManager,locationProviderClient,weatherString);
    }
}
