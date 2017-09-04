package com.karthik.todo;

import com.karthik.todo.Services.APIService.UnsplashAPIManager;
import com.karthik.todo.Services.APIService.UnsplashService;
import com.karthik.todo.Pojo.Unsplash;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by karthikrk on 21/08/17.
 */

public class UnsplashAPITestManager {
    @Mock
    private UnsplashService service;
    @Mock
    private UnsplashAPIManager.UnsplashAPICallback callback;
    @Mock
    private Call<Unsplash> unsplashCall;
    @Mock
    private Unsplash unsplash;
    @Captor
    private ArgumentCaptor<Callback<Unsplash>> unSplashCallback;

    private UnsplashAPIManager unsplashAPIManager;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        unsplashAPIManager = new UnsplashAPIManager(service);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenTheCallBackisNullThrowException(){
        unsplashAPIManager.setUnsplashAPICallbacks(null);
    }

    @Test
    public void whenGetPhotoListIsSuccessFul(){
        when(service.getSearchedUnsplashPhotos("","")).thenReturn(unsplashCall);
        Response<Unsplash> unsplashResponse = Response.success(unsplash);

        unsplashAPIManager.setUnsplashAPICallbacks(callback);
        unsplashAPIManager.getPhotosList("");

        verify(unsplashCall).enqueue(unSplashCallback.capture());
        unSplashCallback.getValue().onResponse(null,unsplashResponse);

        verify(callback).onSuccess(any(Unsplash.class));
    }

    @Test
    public void whenGetPhotoListIsUnSuccessFul(){
        when(service.getSearchedUnsplashPhotos("","")).thenReturn(unsplashCall);

        unsplashAPIManager.setUnsplashAPICallbacks(callback);
        unsplashAPIManager.getPhotosList("");

        verify(unsplashCall).enqueue(unSplashCallback.capture());
        unSplashCallback.getValue().onFailure(null,new Throwable("internal server error"));

        verify(callback).onFailure(any(String.class));
    }

}
