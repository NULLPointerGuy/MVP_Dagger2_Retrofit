package com.karthik.corecommon;

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




}
