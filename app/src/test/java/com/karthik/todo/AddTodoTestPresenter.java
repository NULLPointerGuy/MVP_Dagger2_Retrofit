package com.karthik.todo;

import com.karthik.todo.DB.Dbhander;
import com.karthik.todo.DB.Models.Todo;
import com.karthik.todo.Screens.AddTodo.MVP.AddTodoPresenter;
import com.karthik.todo.Screens.AddTodo.MVP.AddTodoViewContract;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by karthikrk on 16/08/17.
 */

public class AddTodoTestPresenter {
    @Mock
    Dbhander dbhander;
    @Mock
    AddTodoViewContract mockView;
    private AddTodoPresenter mockPresenter;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockPresenter = new AddTodoPresenter(mockView,dbhander);
    }

    @Test
    public void whenAnyFieldIsEmptyShowError(){
        when(mockView.isTodoValidTitle()).thenReturn(false);
        when(mockView.isTodoValidDetail()).thenReturn(false);

        mockPresenter.saveTodo();

        verify(mockView,times(1)).showAppropriateError();
    }

    @Test
    public void saveInDbWhenTheFieldsAreNonEmpty(){
        when(mockView.isTodoValidTitle()).thenReturn(true);
        when(mockView.isTodoValidDetail()).thenReturn(true);

        mockPresenter.saveTodo();

        verify(dbhander,times(1)).saveTodo(any(Todo.class));
        verify(mockView,times(1)).showSaveSuccessMessage();
    }

    @Test
    public void closeDbCalled(){
        mockPresenter.closeDb();
        verify(dbhander,times(1)).closeDb();
    }
}
