package com.karthik.todo.AddTodo.DI;

import com.karthik.todo.AddTodo.MVP.AddTodoPresenter;
import com.karthik.todo.AddTodo.MVP.AddTodoPresenterContract;
import com.karthik.todo.AddTodo.MVP.AddTodoViewContract;
import com.karthik.todo.DB.Dbhander;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by karthikr on 8/8/17.
 */
@Module
public class AddTodoModule {
    private AddTodoViewContract view;

    public AddTodoModule(AddTodoViewContract view){
        this.view = view;
    }

    @Provides
    AddTodoViewContract providesView(){
        return view;
    }

    @Provides
    AddTodoPresenterContract providesPresenter(AddTodoViewContract viewContract){
        return new AddTodoPresenter(viewContract);
    }

    @Provides
    Dbhander providesDbHandler(Realm realmdb){
        return new Dbhander(realmdb);
    }
}
