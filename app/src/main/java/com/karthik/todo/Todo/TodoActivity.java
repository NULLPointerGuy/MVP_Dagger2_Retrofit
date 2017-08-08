package com.karthik.todo.Todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.karthik.todo.AddTodo.AddTodoActivity;
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

public class TodoActivity extends AppCompatActivity implements TodoViewContract{

    private TodoDashComponent dashComponent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

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
    protected void onDestroy() {
        super.onDestroy();
        dashComponent = null;
    }
}
