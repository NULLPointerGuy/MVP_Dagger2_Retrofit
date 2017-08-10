package com.karthik.todo.DB.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by karthikrk on 09/08/17.
 */

public class Todo extends RealmObject {
    @PrimaryKey
    private long id;
    private String todoTitle;
    private String todoDesc;
    private boolean isDone;

    public void setId(long id) {
        this.id = id;
    }
    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }
    public void setTodoDesc(String todoDesc) {
        this.todoDesc = todoDesc;
    }
    public long getId() {
        return id;
    }
    public String getTodoTitle() {
        return todoTitle;
    }
    public String getTodoDesc() {
        return todoDesc;
    }
    public boolean isDone() {
        return isDone;
    }
    public void setDone(boolean done) {
        isDone = done;
    }
}
