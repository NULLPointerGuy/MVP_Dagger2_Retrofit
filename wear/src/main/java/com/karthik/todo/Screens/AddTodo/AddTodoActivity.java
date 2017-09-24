package com.karthik.todo.Screens.AddTodo;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.EditText;

import com.firebase.jobdispatcher.Job;
import com.karthik.corecommon.Modules.AddTodoModule;
import com.karthik.corecommon.Presenters.Contracts.AddTodoPresenterContract;
import com.karthik.corecommon.TodoApp;
import com.karthik.corecommon.Views.AddTodoView;
import com.karthik.todo.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karthikrk on 20/09/17.
 */

public class AddTodoActivity extends WearableActivity implements AddTodoView {

    @BindView(R.id.todoTitle)
    EditText todoTitle;

    @Inject
    AddTodoPresenterContract presenter;
    private AddTodoWearComponent addTodoComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        intialize();
    }

    private void intialize() {
        addTodoComponent = DaggerAddTodoWearComponent.builder()
                .todoComponent(((TodoApp)getApplication()).getComponent())
                .addTodoModule(new AddTodoModule(this))
                .build();
        addTodoComponent.inject(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.closeDb();
        addTodoComponent = null;
    }


    @OnClick(R.id.save)
    public void save(){
        presenter.saveTodo();
    }

    @Override
    public boolean isTodoValidTitle() {
        todoTitle.setError(null);
        return todoTitle.getText()!=null
                && !(todoTitle.getText().toString().trim().isEmpty());
    }

    @Override
    public void showAppropriateError() {
        todoTitle.setError(getString(R.string.todo_title_error));
    }

    @Override
    public String getTodoTitle() {
        return todoTitle.getText().toString();
    }

    @Override
    public void showSaveSuccessMessage() {
        finish();
    }

    @Override
    public void showDatePickerDialog() {

    }

    @Override
    public void showTimePickerDialog() {

    }

    @Override
    public void setDefaultDateAndTime(String formattedDate, String formattedTime) {

    }

    @Override
    public boolean isReminderSet() {
        return false;
    }

    @Override
    public String getComposedReminderTime() {
        return null;
    }

    @Override
    public Bundle getBundleForJob(String title, int id, String composedTitle) {
        return null;
    }

    @Override
    public long getSetTimeInMilli() {
        return 0;
    }

    @Override
    public Job getJobFor(int taskId) {
        return null;
    }

    @Override
    public void scheduleJob(Job job, int taskId) {

    }
}
