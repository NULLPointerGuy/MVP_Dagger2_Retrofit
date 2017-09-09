package com.karthik.todo;

import com.karthik.corecommon.Db.Dbhander;
import com.karthik.corecommon.Presenters.AddTodoPresenter;
import com.karthik.corecommon.Views.AddTodoView;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.util.Calendar.DAY_OF_MONTH;
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

        verify(dbhander,times(1)).saveTodo(null,false,null);
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

        verify(mockView,times(1)).getJobFor(0);
        verify(mockView,times(1)).scheduleJob(null,0);
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

    @Test
    public void diffTimeForJobSchedulerShouldBePositive(){
        Calendar futureCal = Calendar.getInstance();
        futureCal.add(Calendar.DATE,1);
        when(mockView.getSetTimeInMilli()).thenReturn(futureCal.getTimeInMillis());

        Assert.assertEquals(mockPresenter.getDiffTime()>0,true);
    }

    @Test
    public void verifyTimeIsInMilliSec(){
        long milli  = Calendar.getInstance().getTimeInMillis();
        long sec = TimeUnit.MILLISECONDS.toSeconds(milli);
        Assert.assertEquals(mockPresenter.getTimeInSec(milli),sec);
    }

    @Test
    public void titleAndComposedReminderTimeCalledForGivenTaskId(){
        mockPresenter.getBundleForJob(0);

        verify(mockView,times(1)).getTodoTitle();
        verify(mockView,times(1)).getComposedReminderTime();
    }

    @Test
    public void verifyGetJobForTaskInteractionInView(){
        mockPresenter.getJobForTask(0);

        verify(mockView,times(1)).getJobFor(0);
    }

}
