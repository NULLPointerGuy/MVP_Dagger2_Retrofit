package com.karthik.todo;



import com.karthik.corecommon.APIService.ForecastAPIManager;
import com.karthik.corecommon.APIService.ForecastService;
import com.karthik.corecommon.Models.Currently;
import com.karthik.corecommon.Models.Forecast;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    @Mock
    private ForecastService forecastService;
    @Mock
    private ForecastAPIManager.ForecastCallback mockCallBack;
    @Mock
    private Call<Forecast> forecastCall;
    @Captor
    private ArgumentCaptor<Callback<Forecast>> forecastCallback;
    @Mock
    private Forecast response;

    private ForecastAPIManager forecastAPIManager;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        forecastAPIManager = new ForecastAPIManager(forecastService);
    }

    @Test
    public void verifyForecastInfoIsCalled(){
        when(forecastService.getForecastInfo("",12,12)).thenReturn(forecastCall);

        forecastAPIManager.getForecast(12,12);
        verify(forecastCall,times(1)).enqueue(any(Callback.class));
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

    @Test
    public void whenTheResponseIsSuccessful(){
        Currently currently = mock(Currently.class);
        when(forecastService.getForecastInfo("",12,12)).thenReturn(forecastCall);
        when(response.getCurrently()).thenReturn(currently);
        when(currently.getIcon()).thenReturn("https://smallicon.pnf");
        Response<Forecast> res = Response.success(response);

        forecastAPIManager.setForecastAPICallbacks(mockCallBack);
        forecastAPIManager.getForecast(12,12);

        verify(forecastCall).enqueue(forecastCallback.capture());
        forecastCallback.getValue().onResponse(null,res);
        verify(mockCallBack,times(1)).success(any(Forecast.class));
    }


    @Test
    public void whenTheResponseFailed(){
        when(forecastService.getForecastInfo("",12,12)).thenReturn(forecastCall);

        forecastAPIManager.setForecastAPICallbacks(mockCallBack);
        forecastAPIManager.getForecast(12,12);

        verify(forecastCall).enqueue(forecastCallback.capture());
        forecastCallback.getValue().onFailure(null,new Throwable("Empty body"));

        verify(mockCallBack,times(1)).failure(any(String.class));

    }

}
