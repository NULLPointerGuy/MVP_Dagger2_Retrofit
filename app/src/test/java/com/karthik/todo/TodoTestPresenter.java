package com.karthik.todo;

import com.google.android.gms.location.FusedLocationProviderClient;

import com.karthik.todo.APIService.ForecastAPIManager;
import com.karthik.todo.APIService.UnsplashAPIManager;
import com.karthik.todo.DB.Dbhander;
import com.karthik.todo.Pojo.Result;
import com.karthik.todo.Pojo.Unsplash;
import com.karthik.todo.Screens.Todo.MVP.TodoPresenter;
import com.karthik.todo.Screens.Todo.MVP.TodoPresenterContract;
import com.karthik.todo.Screens.Todo.MVP.TodoViewContract;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.mock;
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
    TodoViewContract mockView;
    private TodoPresenterContract mockPresenter;

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
}
