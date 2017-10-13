package com.karthik.corecommon;

import android.os.Bundle;

import com.karthik.corecommon.Db.Dbhander;
import com.karthik.corecommon.Presenters.AddTodoPresenter;
import com.karthik.corecommon.Views.AddTodoView;
import com.karthik.corecommon.Views.DateTimeSelectionView;
import com.karthik.corecommon.Views.ReminderManagedView;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
    AddTodoView mockView;
    @Mock
    DateTimeSelectionView dateTimeSelectionView;
    ReminderManagedView reminderManagedView;
    private AddTodoPresenter mockPresenter;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockPresenter = new AddTodoPresenter(mockView,dateTimeSelectionView,
                reminderManagedView,
                dbhander);
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
        verify(dbhander,times(1)).saveTodo(null,false,null);
        verify(mockView,times(1)).showSaveSuccessMessage();
    }

    @Test
    public void closeDbCalled(){
        mockPresenter.closeDb();
        verify(dbhander,times(1)).closeDb();
    }

    @Test
    public void testWhetherViewDateSelectionisShown(){
        mockPresenter.showDateSelection();
        verify(dateTimeSelectionView,times(1)).showDatePickerDialog();
    }

    @Test
    public void testWhetherViewTimeSelectionisShown(){
        mockPresenter.showTimeSelection();
        verify(dateTimeSelectionView,times(1)).showTimePickerDialog();
    }

    @Test
    public void verifyComposedTimeSet(){
        Calendar cal = Calendar.getInstance();
        String formattedDate = cal.get(Calendar.DAY_OF_MONTH)+" "
                +cal.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault())
                +" "+cal.get(Calendar.YEAR);
        String formattedTime = cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);

        Assert.assertNotEquals(null,formattedDate);
        Assert.assertNotEquals("",formattedDate);
        Assert.assertNotEquals(null,formattedTime);
        Assert.assertNotEquals("",formattedTime);

        mockPresenter.setComposedDateAndTime(cal);
        verify(dateTimeSelectionView,times(1))
                .setDefaultDateAndTime(any(String.class),any(String.class));
    }

    @Test
    public void verifyDiffTimeCalculated(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,1);
        when(dateTimeSelectionView.getSetTimeInMilli())
                .thenReturn(cal.getTimeInMillis());

        Assert.assertNotEquals(true,mockPresenter.getDiffTime()<0);
        long expectedDiff = cal.getTimeInMillis()
                -Calendar.getInstance().getTimeInMillis();
        Assert.assertEquals(expectedDiff,mockPresenter.getDiffTime());
    }

    @Test
    public void verifyTimeCalculatedInMilli(){
        Calendar cal = Calendar.getInstance();

        Assert.assertNotEquals(true,mockPresenter
                .getTimeInSec(cal.getTimeInMillis())<0);
        int expectedValue = (int) (cal.getTimeInMillis()/1000);
        Assert.assertEquals(expectedValue,mockPresenter
                .getTimeInSec(cal.getTimeInMillis()));
    }

}
