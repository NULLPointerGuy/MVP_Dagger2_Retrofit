package com.karthik.corecommon.APIService;

import com.karthik.corecommon.Models.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by karthikrk on 12/08/17.
 */

public interface ForecastService {
    @GET("forecast/{apikey}/{lat},{long}")
    Call<Forecast> getForecastInfo(@Path("apikey") String apikey,
                                   @Path("lat") double lat,
                                   @Path("long") double log);
}
