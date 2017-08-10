package com.karthik.todo.Todo.DI;

import com.karthik.todo.DB.Dbhander;
import com.karthik.todo.Todo.MVP.TodoPresenter;
import com.karthik.todo.Todo.MVP.TodoPresenterContract;
import com.karthik.todo.Todo.MVP.TodoViewContract;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by karthikr on 8/8/17.
 */
@Module
public class TodoModule {
    private TodoViewContract view;

    public TodoModule(TodoViewContract view){
        this.view = view;
    }

    @Provides
    TodoViewContract providesTodoView(){
        return view;
    }

    @Provides
    TodoPresenterContract providesPresenter(TodoViewContract viewContract){
        return new TodoPresenter(viewContract);
    }

    @Provides
    Dbhander providesDbHandler(Realm realm){
      return new Dbhander(realm);
    }
}
