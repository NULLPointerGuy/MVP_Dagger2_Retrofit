package com.karthik.corecommon;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.karthik.corecommon.APIService.ForecastAPIManager;
import com.karthik.corecommon.APIService.UnsplashAPIManager;
import com.karthik.corecommon.Db.CacheManagedView;
import com.karthik.corecommon.Db.Dbhander;
import com.karthik.corecommon.Models.Currently;
import com.karthik.corecommon.Models.Forecast;
import com.karthik.corecommon.Models.Result;
import com.karthik.corecommon.Models.Unsplash;
import com.karthik.corecommon.Models.Urls;
import com.karthik.corecommon.Presenters.TodoPresenter;
import com.karthik.corecommon.Views.DashBoardManagedView;
import com.karthik.corecommon.Views.TodoView;


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

import static java.util.Calendar.DAY_OF_MONTH;
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
    TodoView todoView;
    @Mock
    CacheManagedView cacheManagedView;
    @Mock
    DashBoardManagedView dashBoardManagedView;
    private TodoPresenter mockTodoPresenter;
    private FusedLocationProviderClient locationClient;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockTodoPresenter = new TodoPresenter(todoView,cacheManagedView,
                dashBoardManagedView, dbhander,
                unsplashAPIManager,forecastAPIManager,
                locationClient,"weather string");

    }

    @Test
    public void verifyOpenAddTodoScreenCalled(){
        mockTodoPresenter.onAddTodoClicked();
        verify(todoView,times(1)).openAddTodoScreen();
    }

    @Test
    public void showEmptyTextWhenTaskListIsEmpty(){
        when(dbhander.isDbEmpty()).thenReturn(true);
        mockTodoPresenter.loadTasks();
        verify(todoView,times(1)).showEmptyTextAndHideTask();
    }

    @Test
    public void loadTasksWhenTaskListIsNonEmpty(){
        when(dbhander.isDbEmpty()).thenReturn(false);
        mockTodoPresenter.loadTasks();
        verify(todoView,times(1)).hideEmptyTextAndShowTask();
    }


    @Test
    public void validateComposedDashTitle(){
        Calendar calendar = Calendar.getInstance();

        String formatedDateString = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
                + ", " + calendar.get(Calendar.DAY_OF_MONTH)
                + " " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " ";

        mockTodoPresenter.setDashTitle();

        Assert.assertNotEquals(null,formatedDateString);
        Assert.assertNotEquals("",formatedDateString);
        verify(dashBoardManagedView,times(1)).setDashBoardTitle(formatedDateString);

    }

    @Test
    public void verifyUnsplashImageLoadFromCache(){
        String valueOfToday = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        when(cacheManagedView.isCachePresent(valueOfToday))
                .thenReturn(true);

        mockTodoPresenter.getUnsplashImages("nature");

        verify(cacheManagedView,times(1)).getFromCache(valueOfToday);
    }

    @Test
    public void verifyUnsplashFromNetworkWhenCacheIsNotPresent(){
        String valueOfToday = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        when(cacheManagedView.isCachePresent(valueOfToday))
                .thenReturn(false);

        mockTodoPresenter.getUnsplashImages("nature");

        verify(unsplashAPIManager,times(1)).getPhotosList("nature");
    }

    @Test
    public void askLocationPermWhenNotGranted(){
        when(dashBoardManagedView.isLocationPermGranted()).thenReturn(false);

        mockTodoPresenter.getLocation();

        verify(dashBoardManagedView,times(1)).askLocationPermission();
    }

    @Test
    public void verifyUnsplashImagesAreSavedInCache(){
        String valueOfToday = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        Unsplash unsplash = new Unsplash();
        Result results = new Result();
        Urls urls = new Urls();
        urls.setSmall("https://dummyurl.api.com");
        results.setUrls(urls);
        List<Result> resultList = new ArrayList<>();
        resultList.add(results);
        unsplash.setResults(resultList);

        mockTodoPresenter.onSuccess(unsplash);

        verify(cacheManagedView,times(1))
                .saveInCache(valueOfToday,
                        "{\"results\":[{\"urls\":{\"small\":\"https://dummyurl.api.com\"}}]}");
    }

    @Test
    public void verifyWhenUnsplashIsNullNotSavedInCache(){
        mockTodoPresenter.onSuccess(null);

        verify(cacheManagedView,times(0)).saveInCache(any(String.class),
                any(String.class));
    }

    @Test
    public void verifyForecastInfoNotSetWhenResponseisNUll(){
        mockTodoPresenter.success(null);

        verify(dashBoardManagedView,times(0)).setForeCastInfo(any(String.class));
    }

    @Test
    public void verifyForecastSetOnResponse(){
        Forecast forecast = mock(Forecast.class);
        Currently currently = mock(Currently.class);
        when(currently.getIcon()).thenReturn("cloudy");
        when(forecast.getCurrently()).thenReturn(currently);

        mockTodoPresenter.success(forecast);

        verify(dashBoardManagedView,times(1)).setForeCastInfo(any(String.class));
    }
}
