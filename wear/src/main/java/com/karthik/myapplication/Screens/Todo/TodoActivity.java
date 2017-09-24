package com.karthik.myapplication.Screens.Todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;

import com.karthik.corecommon.Models.Todo;
import com.karthik.corecommon.Modules.TodoModule;
import com.karthik.corecommon.Presenters.Contracts.TodoPresenterContract;
import com.karthik.corecommon.TodoApp;
import com.karthik.corecommon.Views.TodoView;
import com.karthik.myapplication.AddTodoCallback;
import com.karthik.myapplication.R;
import com.karthik.myapplication.Screens.AddTodo.AddTodoActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class TodoActivity extends WearableActivity implements TodoView,AddTodoCallback{

    @BindView(R.id.empty_text)
    TextView emptyText;
    @BindView(R.id.taskList)
    WearableRecyclerView taskList;

    @Inject
    TodoPresenterContract presenter;

    private TodoDashWearComponent wearComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        intialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadTasks();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wearComponent = null;
    }

    @Override
    public void onAddTodoClicked() {
        presenter.onAddTodoClicked();
    }

    @Override
    public void openAddTodoScreen() {
        startActivity(new Intent(this,AddTodoActivity.class));
    }

    @Override
    public void hideEmptyTextAndShowTask() {
        emptyText.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyTextAndHideTask() {
        emptyText.setVisibility(View.VISIBLE);
        loadEmptyTaskList();
    }

    @Override
    public void loadTasks(RealmResults<Todo> todo) {
        taskList.setLayoutManager(new LinearLayoutManager(this));
        taskList.setItemAnimator(new DefaultItemAnimator());
        taskList.setAdapter(new TaskAdapter(todo,this));
    }

    @Override
    public void saveInCache(String date, String json) {

    }

    @Override
    public void loadImage(String url) {

    }

    @Override
    public boolean isCachePresent(String date) {
        return false;
    }

    @Override
    public String getFromCache(String date) {
        return null;
    }

    @Override
    public void setDashBoardTitle(String date) {

    }

    @Override
    public void setForeCastInfo(String foreCastInfo) {

    }

    @Override
    public boolean isLocationPermGranted() {
        return false;
    }

    @Override
    public void askLocationPermission() {

    }

    private void intialize() {
       wearComponent =  DaggerTodoDashWearComponent.builder()
                .todoComponent(((TodoApp)getApplication()).getComponent())
                .todoModule(new TodoModule(this,this))
                .build();
       wearComponent.inject(this);
       ButterKnife.bind(this);
    }

    private void loadEmptyTaskList() {
        taskList.setLayoutManager(new LinearLayoutManager(this));
        taskList.setItemAnimator(new DefaultItemAnimator());
        taskList.setAdapter(new TaskAdapter(this));
    }
}
