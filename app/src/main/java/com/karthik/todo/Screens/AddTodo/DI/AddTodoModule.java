package com.karthik.todo.Screens.AddTodo.DI;

import android.content.Context;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.karthik.todo.Screens.AddTodo.MVP.AddTodoPresenter;
import com.karthik.todo.Screens.AddTodo.MVP.AddTodoPresenterContract;
import com.karthik.todo.Screens.AddTodo.MVP.AddTodoViewContract;
import com.karthik.todo.DB.Dbhander;

import javax.inject.Singleton;

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
    public Realm providesRealmDb(){
        return Realm.getDefaultInstance();
    }

    @Provides
    Dbhander providesDbHandler(Realm realmdb){
        return new Dbhander(realmdb);
    }

    @Provides
    FirebaseJobDispatcher providesFireBaseJobDispatcher(Context context){
        return new FirebaseJobDispatcher(new GooglePlayDriver(context));
    }

    @Provides
    AddTodoPresenterContract providesPresenter(AddTodoViewContract viewContract,Dbhander dbhander,
                                               FirebaseJobDispatcher jobDispatcher){
        return new AddTodoPresenter(viewContract,dbhander,jobDispatcher);
    }
}
