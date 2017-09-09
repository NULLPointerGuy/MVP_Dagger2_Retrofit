package com.karthik.corecommon.Db;



import com.karthik.corecommon.Models.Todo;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by karthikrk on 09/08/17.
 */

public class Dbhander {
    private Realm realmdb;

    public Dbhander(Realm realmdb){
        this.realmdb = realmdb;
    }

    public void saveTodo(String title,boolean isReminderSet,String notifyTime){
       if(realmdb==null||realmdb.isClosed())
           throw new IllegalStateException("db is closed for null please recreate handler");

       realmdb.beginTransaction();
       Todo tododb = realmdb.createObject(Todo.class,autoIncTodo());
       tododb.setTodoTitle(title);
       if(isReminderSet){
           tododb.setReminderSet(true);
           tododb.setNotifyTime(notifyTime);
       }
       realmdb.commitTransaction();
    }

    public int getRecentTodoId(){
        return realmdb
                .where(Todo.class)
                .max("id")
                .intValue();
    }

    public boolean isDbEmpty(){
        return realmdb.isEmpty();
    }

    public RealmResults<Todo> getAllTodo(){
       return realmdb.where(Todo.class).findAll();
    }

    public void closeDb(){
        realmdb.close();
    }

    private long autoIncTodo(){
        if(realmdb.isEmpty())
          return 1;
       else
          return realmdb.where(Todo.class)
                  .max("id")
                  .intValue()+1;
    }
}
