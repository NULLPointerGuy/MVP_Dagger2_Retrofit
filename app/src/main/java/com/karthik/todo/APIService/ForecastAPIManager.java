package com.karthik.todo.APIService;

import com.karthik.todo.BuildConfig;
import com.karthik.todo.Pojo.Forecast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by karthikrk on 12/08/17.
 */

public class ForecastAPIManager {
    private ForecastService forecastService;
    private ForecastCallback callBack;

   public interface ForecastCallback{
        void success(Forecast forecast);
        void failure(String message);
    }

    public void setForecastAPICallbacks(ForecastCallback callBack) {
        this.callBack = callBack;
    }


    public ForecastAPIManager(ForecastService forecastService){
        this.forecastService = forecastService;
    }

    public void getForecast(double lat,double log){
        Call<Forecast> forecastCall =  this.forecastService
                .getForecastInfo(BuildConfig.FORECAST_API_KEY,lat,log);

        forecastCall.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                callBack.success(response.body());
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                callBack.failure(t.getMessage());
            }
        });
    }
}