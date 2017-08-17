package com.karthik.todo;

import com.google.android.gms.tasks.OnSuccessListener;

import android.support.v4.util.Preconditions;

import com.karthik.todo.APIService.ForecastAPIManager;
import com.karthik.todo.APIService.ForecastService;
import com.karthik.todo.Pojo.Forecast;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by karthikrk on 17/08/17.
 */

public class ForecastAPITestManager {
    private ForecastService forecastService;
    private ForecastAPIManager.ForecastCallback mockCallBack;
    private ForecastAPIManager forecastAPIManager;

    @Before
    public void setup(){
        forecastService = mock(ForecastService.class);
        mockCallBack = mock(ForecastAPIManager.ForecastCallback.class);
        forecastAPIManager = new ForecastAPIManager(forecastService);
    }

    @Test
    public void verifyForecastInfoIsCalled(){
        Call<Forecast> call = mock(Call.class);
        when(forecastService.getForecastInfo("",12,12)).thenReturn(call);

        forecastAPIManager.getForecast(12,12);
        verify(call,times(1)).enqueue(any(Callback.class));
    }

    @Test(expected = IllegalStateException.class)
    public void setCallBackNullVerification(){
        forecastAPIManager.setForecastAPICallbacks(null);
    }
    @Test
    public void setCallBackNonNullVerification(){
        forecastAPIManager.setForecastAPICallbacks(mockCallBack);
        Assert.assertEquals(true,Objects.nonNull(forecastAPIManager.getCallBack()));
    }

}
