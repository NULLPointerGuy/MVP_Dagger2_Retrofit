package com.karthik.corecommon;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.location.Location;

import com.karthik.corecommon.APIService.ForecastAPIManager;
import com.karthik.corecommon.APIService.UnsplashAPIManager;
import com.karthik.corecommon.Db.Dbhander;
import com.karthik.corecommon.Models.Currently;
import com.karthik.corecommon.Models.Forecast;
import com.karthik.corecommon.Models.Result;
import com.karthik.corecommon.Models.Unsplash;
import com.karthik.corecommon.Models.Urls;
import com.karthik.corecommon.Presenters.TodoPresenter;
import com.karthik.corecommon.Views.TodoAppView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by karthikr on 14/8/17.
 */

public class TodoTestPresenter {
    @Mock
    Dbhander dbhander;
    @Mock
    UnsplashAPIManager unsplashAPIManager;
    @Mock
    ForecastAPIManager forecastAPIManager;
    @Mock
    FusedLocationProviderClient locationClient;
    @Mock
    TodoAppView mockView;
    private TodoPresenter mockPresenter;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockPresenter = new TodoPresenter(mockView,dbhander,unsplashAPIManager,
                forecastAPIManager,locationClient,"its gonna be");
    }

    @Test
    public void WhenTaskIsEmptyShouldEmptyTextAndHideTaskList(){
        when(dbhander.isDbEmpty()).thenReturn(true);
        mockPresenter.loadTasks();
        verify(mockView,times(1)).showEmptyTextAndHideTask();
    }

    @Test
    public void whenTaskIsNotEmptyHideEmptyTextAndShowTaskList(){
        when(dbhander.isDbEmpty()).thenReturn(false);
        mockPresenter.loadTasks();
        verify(mockView,times(1)).hideEmptyTextAndShowTask();
        verify(mockView,times(1)).loadTasks(dbhander.getAllTodo());
    }

    @Test
    public void whenAddTodoClickedOpenTodoScreen(){
        mockPresenter.onAddTodoClicked();
        verify(mockView,times(1)).openAddTodoScreen();
    }

    @Test
    public void verifySetDashTitle(){
        Calendar cal = Calendar.getInstance();
        String formatedDateString = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
                + ", " + cal.get(Calendar.DAY_OF_MONTH)
                + " " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " ";
        mockPresenter.setDashTitle();
        Assert.assertNotEquals(null,formatedDateString);
        Assert.assertNotEquals("",formatedDateString);
        verify(mockView,times(1)).setDashBoardTitle(formatedDateString);
    }

    @Test
    public void testStringValueOfToday(){
        Assert.assertNotEquals(null,String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
        Assert.assertNotEquals("",String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
    }

    @Test
    public void loadUnsplashFromNetworkIfCacheNotPresent(){
        when(mockView.isCachePresent(String
                .valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH))))
                .thenReturn(false);
        mockPresenter.getUnsplashImages("random");
        verify(unsplashAPIManager,times(1)).getPhotosList("random");
    }

    @Test
    public void askLocationPermissionIfNotGranted(){
        when(mockView.isLocationPermGranted()).thenReturn(false);
        mockPresenter.getLocation();
        verify(mockView,times(1)).askLocationPermission();
    }


    @SuppressLint("MissingPermission")
    @Test
    public void getLocationDetailsIfPermissionGranted(){
        when(mockView.isLocationPermGranted()).thenReturn(true);
        Task<Location> task = mock(Task.class);
        when(locationClient.getLastLocation()).thenReturn(task);
        mockPresenter.getLocation();
        verify(locationClient,times(1)).getLastLocation();
    }

    @Test
    public void onNullResponseOfUnsplash(){
        Unsplash nullUnsplash = null;
        mockPresenter.onSuccess(nullUnsplash);
        verify(mockView,never()).saveInCache("dummy","dummy");
    }

    @Test
    public void onEmptyResponseOfUnsplash(){
        Unsplash unsplash = new Unsplash();
        unsplash.setResults(Collections.<Result>emptyList());
        mockPresenter.onSuccess(unsplash);
        verify(mockView,never()).saveInCache("dummy",new Gson().toJson(unsplash));
    }

    @Test
    public void onActualResponseOfUnsplash(){
        Unsplash unsplash = new Unsplash();
        Result results = new Result();
        Urls urls = new Urls();
        urls.setSmall("https://dummyurl.api.com");
        results.setUrls(urls);
        List<Result> resultList = new ArrayList<>();
        resultList.add(results);
        unsplash.setResults(resultList);
        mockPresenter.onSuccess(unsplash);
        verify(mockView,times(1))
                .saveInCache(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                        "{\"results\":[{\"urls\":{\"small\":\"https://dummyurl.api.com\"}}]}");
    }

    @Test
    public void onNullResponseofForecast(){
        Forecast forecast = null;
        TodoPresenter spyPresenter = Mockito.spy(mockPresenter);
        spyPresenter.success(forecast);
        verify(mockView,never()).setForeCastInfo(null);
        verify(spyPresenter,times(1)).getUnsplashImages("nature");
    }

    @Test
    public void onEmptyResponseOfForecast(){
        Forecast forecast = mock(Forecast.class);
        TodoPresenter spyPresenter = Mockito.spy(mockPresenter);
        spyPresenter.success(forecast);
        verify(mockView,never()).setForeCastInfo(null);
        verify(spyPresenter,times(1)).getUnsplashImages("nature");
    }

    @Test
    public void onActualResponseOfForecast(){
        Forecast forecast = mock(Forecast.class);
        Currently currently = mock(Currently.class);
        when(currently.getIcon()).thenReturn("cloudy");
        when(forecast.getCurrently()).thenReturn(currently);

        TodoPresenter spyPresenter = Mockito.spy(mockPresenter);
        spyPresenter.success(forecast);

        verify(spyPresenter,times(1)).getUnsplashImages("cloudy");
        verify(mockView,times(1)).setForeCastInfo("its gonna be");
    }

}
