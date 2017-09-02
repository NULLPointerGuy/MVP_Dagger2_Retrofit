package com.karthik.todo;

import com.karthik.todo.DB.Dbhander;
import com.karthik.todo.DB.Models.Todo;
import com.karthik.todo.Screens.AddTodo.MVP.AddTodoPresenter;
import com.karthik.todo.Screens.AddTodo.MVP.AddTodoViewContract;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.DAY_OF_MONTH;
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

        mockPresenter.saveTodo();

        verify(mockView,times(1)).showAppropriateError();
    }

    @Test
    public void saveInDbWhenTheFieldsAreNonEmpty(){
        when(mockView.isTodoValidTitle()).thenReturn(true);

        mockPresenter.saveTodo();

        verify(dbhander,times(1)).saveTodo(any(Todo.class));
        verify(mockView,times(1)).showSaveSuccessMessage();
    }

    @Test
    public void closeDbCalled(){
        mockPresenter.closeDb();
        verify(dbhander,times(1)).closeDb();
    }

    @Test
    public void testWhetherReminderISetInDb(){
        when(mockView.isTodoValidTitle()).thenReturn(true);
        when(mockView.isReminderSet()).thenReturn(true);

        mockPresenter.saveTodo();

        verify(mockView,times(1)).getComposedReminderTime();
    }

    @Test
    public void testWhetherViewDateSelectionisShown(){
        mockPresenter.showDateSelection();

        verify(mockView,times(1)).showDatePickerDialog();
    }

    @Test
    public void testWhetherViewTimeSelectionisShown(){
        mockPresenter.showTimeSelection();

        verify(mockView,times(1)).showTimePickerDialog();
    }

    @Test
    public void testComposedStringFromCalender(){
        Calendar calendar = mock(Calendar.class);
        when(calendar.get(DAY_OF_MONTH)).thenReturn(4);
        when(calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault()))
                .thenReturn("SEP");
        when(calendar.get(Calendar.YEAR)).thenReturn(2017);
        when(calendar.get(Calendar.HOUR_OF_DAY)).thenReturn(4);
        when(calendar.get(Calendar.MINUTE)).thenReturn(10);

        mockPresenter.setComposedDateAndTime(calendar);
        String formattedDate = calendar.get(Calendar.DAY_OF_MONTH)+" "
               +calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault())
               +" "+calendar.get(Calendar.YEAR);
        String formattedTime = calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);

        Assert.assertEquals(formattedDate,"4 SEP 2017");
        Assert.assertEquals(formattedTime,"4:10");

        verify(mockView,times(1)).setDefaultDateAndTime(formattedDate,formattedTime);
    }
}
