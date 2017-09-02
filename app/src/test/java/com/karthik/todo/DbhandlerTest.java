package com.karthik.todo;

import android.app.Application;

import com.karthik.todo.DB.Dbhander;
import com.karthik.todo.DB.Models.Todo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


/**
 * Created by karthikrk on 16/08/17.
 */

public class DbhandlerTest {
    private Realm realm;
    private Dbhander dbhander;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        dbhander = new Dbhander(realm);
    }

    @Test(expected = IllegalStateException.class)
    public void throwExceptionWhenRealmIsNull(){
        realm = null;

        Todo todo = new Todo();
        todo.setTodoTitle("demo title");
        dbhander.saveTodo(todo);
    }
}
