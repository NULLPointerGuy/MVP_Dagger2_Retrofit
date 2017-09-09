package com.karthik.corecommon.Modules;

import android.content.Context;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.karthik.corecommon.Db.Dbhander;
import com.karthik.corecommon.Presenters.AddTodoPresenter;
import com.karthik.corecommon.Presenters.Contracts.AddTodoPresenterContract;
import com.karthik.corecommon.Views.AddTodoView;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by karthikr on 8/8/17.
 */
@Module
public class AddTodoModule {
    private AddTodoView view;

    public AddTodoModule(AddTodoView view){
        this.view = view;
    }

    @Provides
    AddTodoView providesView(){
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
    AddTodoPresenterContract providesPresenter(AddTodoView viewContract, Dbhander dbhander){
        return new AddTodoPresenter(viewContract,dbhander);
    }
}
