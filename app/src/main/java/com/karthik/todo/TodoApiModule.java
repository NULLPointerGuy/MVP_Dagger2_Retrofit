package com.karthik.todo;

import com.karthik.todo.APIService.UnsplashAPIManager;
import com.karthik.todo.APIService.UnsplashService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by karthikrk on 10/08/17.
 */

@Module
public class TodoApiModule {

    @Singleton
    @Provides
    @Named("UNSPLASHBASEURL")
    String providesUnsplashBaseUrl(){
        return BuildConfig.UNSPLASH_URL;
    }

    @Singleton
    @Provides
    OkHttpClient providesOkhttp(){
        //Todo:provide connection read write timeouts!!
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG)
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        else
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Singleton
    @Provides
    @Named("UNSPLASHRETROFIT")
    Retrofit providesRetrofit(OkHttpClient okHttpClient,@Named("UNSPLASHBASEURL") String unsplashbaseurl){
        return new Retrofit.Builder()
                .baseUrl(unsplashbaseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    UnsplashService providesUnsplashService(@Named("UNSPLASHRETROFIT") Retrofit retrofit){
        return retrofit.create(UnsplashService.class);
    }

    @Provides
    UnsplashAPIManager providesUnsplashAPIManager(UnsplashService unsplashService){
        return new UnsplashAPIManager(unsplashService);
    }
}
