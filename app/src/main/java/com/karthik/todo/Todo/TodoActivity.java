package com.karthik.todo.Todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.karthik.todo.AddTodo.AddTodoActivity;
import com.karthik.todo.DB.Models.Todo;
import com.karthik.todo.R;
import com.karthik.todo.Todo.DI.TodoDashComponent;
import com.karthik.todo.Todo.DI.TodoModule;
import com.karthik.todo.Todo.MVP.TodoPresenterContract;
import com.karthik.todo.Todo.MVP.TodoViewContract;
import com.karthik.todo.TodoApp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmList;
import io.realm.RealmResults;

public class TodoActivity extends AppCompatActivity implements TodoViewContract{

    private TodoDashComponent dashComponent;

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

    @Inject
    Context context;
    @Inject
    TodoPresenterContract presenter;

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
        presenter.getUnsplashImages();
    }

    private void intialize() {
       dashComponent =  ((TodoApp)getApplication()).getComponent().plus(new TodoModule(this));
       dashComponent.inject(this);
       ButterKnife.bind(this);
       setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    protected void onDestroy() {
        super.onDestroy();
        dashComponent = null;
    }
}
