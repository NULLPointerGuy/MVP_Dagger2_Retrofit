package com.karthik.todo;

import com.google.android.gms.location.FusedLocationProviderClient;

import com.karthik.todo.APIService.ForecastAPIManager;
import com.karthik.todo.APIService.UnsplashAPIManager;
import com.karthik.todo.DB.Dbhander;
import com.karthik.todo.Screens.Todo.MVP.TodoPresenter;
import com.karthik.todo.Screens.Todo.MVP.TodoPresenterContract;
import com.karthik.todo.Screens.Todo.MVP.TodoViewContract;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
}
