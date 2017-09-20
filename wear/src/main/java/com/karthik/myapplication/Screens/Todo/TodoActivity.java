package com.karthik.myapplication.Screens.Todo;

import android.os.Bundle;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karthik.corecommon.Models.Todo;
import com.karthik.corecommon.Modules.TodoModule;
import com.karthik.corecommon.Presenters.Contracts.TodoPresenterContract;
import com.karthik.corecommon.TodoApp;
import com.karthik.corecommon.Views.TodoView;
import com.karthik.myapplication.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

public class TodoActivity extends WearableActivity implements TodoView {

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
    @OnClick(R.id.addTodo)
    public void onAddTodoClicked() {
        presenter.onAddTodoClicked();
    }

    @Override
    public void openAddTodoScreen() {

    }

    @Override
    public void hideEmptyTextAndShowTask() {
        taskList.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyTextAndHideTask() {
        taskList.setVisibility(View.GONE);
        emptyText.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadTasks(RealmResults<Todo> todo) {

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
}
