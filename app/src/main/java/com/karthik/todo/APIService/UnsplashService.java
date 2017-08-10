package com.karthik.todo.APIService;

import com.karthik.todo.Pojo.Unsplash;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by karthikr on 10/8/17.
 */

public interface UnsplashService {
    @GET("search/photos/")
    Call<Unsplash> getSearchedUnsplashPhotos(
       @Query("client_id") String clientId,
       @Query("query") String query
    );

}
