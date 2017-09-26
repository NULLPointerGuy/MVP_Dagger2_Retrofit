package com.karthik.corecommon.Modules;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import android.content.Context;
import android.support.annotation.Nullable;

import com.karthik.corecommon.APIService.ForecastAPIManager;
import com.karthik.corecommon.APIService.UnsplashAPIManager;
import com.karthik.corecommon.Db.CacheManagedView;
import com.karthik.corecommon.Db.Dbhander;
import com.karthik.corecommon.Presenters.Contracts.TodoPresenterContract;
import com.karthik.corecommon.Presenters.TodoPresenter;
import com.karthik.corecommon.R;
import com.karthik.corecommon.Views.DashBoardManagedView;
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
    private CacheManagedView cacheManagedView;
    private DashBoardManagedView dashBoardManagedView;
    private Context activityContext;


    public TodoModule(TodoView view,Context activityContext){
        this.view = view;
        this.activityContext = activityContext;
    }

    public void setCacheManagedView(CacheManagedView cacheManagedView) {
        this.cacheManagedView = cacheManagedView;
    }

    public void setDashBoardManagedView(DashBoardManagedView dashBoardManagedView) {
        this.dashBoardManagedView = dashBoardManagedView;
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
        return new TodoPresenter(viewContract,cacheManagedView, dashBoardManagedView,
                dbhander,unsplashAPIManager,
                forecastAPIManager,
                locationProviderClient,weatherString);
    }
}
