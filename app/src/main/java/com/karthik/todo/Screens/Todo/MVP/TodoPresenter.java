package com.karthik.todo.Screens.Todo.MVP;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;

import com.karthik.todo.Services.APIService.ForecastAPIManager;
import com.karthik.todo.Services.APIService.UnsplashAPIManager;
import com.karthik.todo.DB.Dbhander;
import com.karthik.todo.Pojo.Forecast;
import com.karthik.todo.Pojo.Unsplash;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by karthikrk on 08/08/17.
 */

public class TodoPresenter implements TodoPresenterContract,
        UnsplashAPIManager.UnsplashAPICallback,
        ForecastAPIManager.ForecastCallback{

    private TodoViewContract view;
    private Dbhander dbhander;
    private UnsplashAPIManager unsplashAPIManager;
    private ForecastAPIManager forecastAPIManager;
    private FusedLocationProviderClient locationClient;
    private String weatherString;

    @Inject
    public TodoPresenter(TodoViewContract view,
                         Dbhander dbhander,
                         UnsplashAPIManager unsplashAPIManager,
                         ForecastAPIManager forecastAPIManager,
                         FusedLocationProviderClient providerClient,
                         @Named("WEATHERSTRING") String weatherString) {
        this.view = view;
        this.dbhander = dbhander;
        this.unsplashAPIManager = unsplashAPIManager;
        this.forecastAPIManager = forecastAPIManager;
        this.locationClient = providerClient;
        this.weatherString =  weatherString;
        intializeAPICallbacks();
    }

    private void intializeAPICallbacks() {
        unsplashAPIManager.setUnsplashAPICallbacks(this);
        forecastAPIManager.setForecastAPICallbacks(this);
    }

    @Override
    public void onAddTodoClicked() {
        view.openAddTodoScreen();
    }

    @Override
    public void setDashTitle() {
        Calendar cal = Calendar.getInstance();
        String formatedDateString = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
                + ", " + cal.get(Calendar.DAY_OF_MONTH)
                + " " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " ";
        view.setDashBoardTitle(formatedDateString);
    }

    @Override
    public void loadTasks() {
        if (dbhander.isDbEmpty()) {
            view.showEmptyTextAndHideTask();
            return;
        }
        view.hideEmptyTextAndShowTask();
        view.loadTasks(dbhander.getAllTodo());
    }

    @Override
    public void getUnsplashImages(String genre) {
        if (view.isCachePresent(getStringValueOfToday())) {
            Unsplash unsplash = new Gson()
                    .fromJson(view.getFromCache(getStringValueOfToday()), Unsplash.class);
            loadRandomImage(unsplash);
        } else {
            unsplashAPIManager.getPhotosList(genre);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void getLocation() {
        if(view.isLocationPermGranted()){
            locationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    getForecastInfo(location);
                }
            });
        }else{
            view.askLocationPermission();
        }
    }

    @Override
    public void onSuccess(Unsplash unsplash) {
        if(unsplash==null)
            return;
        view.saveInCache(getStringValueOfToday(),new Gson().toJson(unsplash));
        loadRandomImage(unsplash);
    }

    @Override
    public void onFailure(String message) {
        Log.e("FAILED","UNSPLASH API FAILED");
    }

    @Override
    public void success(Forecast forecast) {
        if(forecast==null || forecast.getCurrently()==null||forecast.getCurrently().getIcon()==null){
            getUnsplashImages("nature");
            return;
        }
        getUnsplashImages(forecast.getCurrently().getIcon());
        view.setForeCastInfo(String.format(weatherString,
                forecast.getCurrently().getSummary()));
    }

    @Override
    public void failure(String message) {
        Log.e("FAILED","FORECAST API FAILED");
    }

    private void getForecastInfo(Location location){
        if(location==null) {
            getUnsplashImages("Nature");
            return;
        }
        forecastAPIManager.getForecast(location.getLatitude(),
                location.getLongitude());
    }

    private String getStringValueOfToday(){
        return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    private void loadRandomImage(Unsplash unsplash) {
        if(unsplash.getResults().size()==0)
            return;
        int random = new Random().nextInt(unsplash.getResults().size());
        view.loadImage(unsplash.getResults().get(random).getUrls().getSmall());
    }
}
