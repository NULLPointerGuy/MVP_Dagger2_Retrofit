package com.karthik.corecommon.APIService;




import com.karthik.corecommon.BuildConfig;
import com.karthik.corecommon.Models.Unsplash;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by karthikr on 10/8/17.
 */

public class UnsplashAPIManager {
    private UnsplashService service;
    private UnsplashAPICallback callback;


    //consumer has to implement this interface.
    public interface UnsplashAPICallback{
        void onSuccess(Unsplash unsplash);
        void onFailure(String message);
    }

    public UnsplashAPIManager(UnsplashService service){
        this.service = service;
    }

    public void setUnsplashAPICallbacks(UnsplashAPICallback unsplashAPICallback){
        if (unsplashAPICallback==null)
            throw new IllegalArgumentException("callback cannot be null");
        this.callback = unsplashAPICallback;
    }

    public void getPhotosList(String type){
       Call<Unsplash> unsplashCall =  this.service
               .getSearchedUnsplashPhotos(BuildConfig.UNSPLASH_API_KEY,type);

       unsplashCall.enqueue(new Callback<Unsplash>() {
           @Override
           public void onResponse(Call<Unsplash> call, Response<Unsplash> response) {
               if(response.isSuccessful())
                   callback.onSuccess(response.body());
               else
                   callback.onFailure(null);
           }

           @Override
           public void onFailure(Call<Unsplash> call, Throwable t) {
               callback.onFailure(t.getMessage());
           }
       });
    }
}
