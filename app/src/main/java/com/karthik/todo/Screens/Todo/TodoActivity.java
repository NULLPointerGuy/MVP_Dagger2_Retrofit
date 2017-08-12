package com.karthik.todo.Screens.Todo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.karthik.todo.Screens.AddTodo.AddTodoActivity;
import com.karthik.todo.DB.Models.Todo;
import com.karthik.todo.R;
import com.karthik.todo.Screens.Todo.DI.TodoDashComponent;
import com.karthik.todo.Screens.Todo.DI.TodoModule;
import com.karthik.todo.Screens.Todo.MVP.TodoPresenterContract;
import com.karthik.todo.Screens.Todo.MVP.TodoViewContract;
import com.karthik.todo.TodoApp;
import com.karthik.todo.Utils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

public class TodoActivity extends AppCompatActivity implements TodoViewContract{

    private TodoDashComponent dashComponent;
    private final int REQUEST_LOCATION_CAPTURE = 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.dashbackgroundImage)
    ImageView dashBackgroundImage;
    @BindView(R.id.empty_text)
    TextView emptyText;
    @BindView(R.id.taskList)
    RecyclerView taskList;
    @BindView(R.id.dashTitle)
    TextView dashTitle;
    @BindView(R.id.dashWeather)
    TextView dashWeather;

    @Inject
    TodoPresenterContract presenter;
    @Inject
    SharedPreferences prefs;
    @Inject
    Picasso picasso;

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

    private void intialize() {
       dashComponent =  ((TodoApp)getApplication()).getComponent().plus(new TodoModule(this,this));
       dashComponent.inject(this);
       ButterKnife.bind(this);
       setSupportActionBar(toolbar);
       presenterIntialize();
    }

    private void presenterIntialize() {
        presenter.setDashTitle();
        if(Utils.isLocationPermissionGranted(this)){
            presenter.getLocation();
            return;
        }
        ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_CAPTURE);
    }

    @Override
    @OnClick(R.id.fab)
    public void onAddTodoClicked() {
       presenter.onAddTodoClicked();
    }

    @Override
    public void openAddTodoScreen() {
        startActivity(new Intent(this, AddTodoActivity.class));
    }

    @Override
    public void hideEmptyTextAndShowTask() {
        emptyText.setVisibility(View.GONE);
        taskList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyTextAndHideTask() {
        emptyText.setVisibility(View.VISIBLE);
        taskList.setVisibility(View.GONE);
    }

    @Override
    public TodoDashComponent getTodoDashComponent() {
        return dashComponent;
    }

    @Override
    public void loadTasks(RealmResults<Todo> todos) {
        taskList.setLayoutManager(new LinearLayoutManager(this));
        taskList.setItemAnimator(new DefaultItemAnimator());
        taskList.setAdapter(new TaskAdapter(todos));
    }

    @Override
    public void saveInCache(String date,String json) {
        prefs.edit().putString(date,json).commit();
    }

    @Override
    public void loadImage(String url) {
        picasso.load(url).into(dashBackgroundImage);
    }

    @Override
    public boolean isCachePresent(String date) {
        return prefs.contains(date);
    }

    @Override
    @Nullable
    public String getFromCache(String date) {
        return prefs.getString(date,null);
    }

    @Override
    public void setDashBoardTitle(String date) {
        dashTitle.setText(date);
    }

    @Override
    public void setForeCastInfo(String foreCastInfo) {
        dashWeather.setVisibility(View.VISIBLE);
        dashWeather.setText(foreCastInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dashComponent = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_LOCATION_CAPTURE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.getLocation();
                    return;
                }
                presenter.getUnsplashImages("Nature");
                break;
        }
    }
}
