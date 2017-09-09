package com.karthik.corecommon.Models;

import java.util.Calendar;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by karthikrk on 09/08/17.
 */

public class Todo extends RealmObject {
    @PrimaryKey
    private long id;
    private String todoTitle;
    private boolean isDone;
    private boolean isReminderSet;
    private String notifyTime;

    public void setId(long id) {
        this.id = id;
    }
    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }
    public long getId() {
        return id;
    }
    public String getTodoTitle() {
        return todoTitle;
    }
    public boolean isDone() {
        return isDone;
    }
    public void setDone(boolean done) {
        isDone = done;
    }
    public boolean isReminderSet() {
        return isReminderSet;
    }
    public void setReminderSet(boolean reminderSet) {
        isReminderSet = reminderSet;
    }
    public String getNotifyTime() {
        return notifyTime;
    }
    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

}
