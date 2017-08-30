package com.karthik.todo.Screens.AddTodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.karthik.todo.Screens.AddTodo.DI.AddTodoComponent;
import com.karthik.todo.Screens.AddTodo.DI.AddTodoModule;
import com.karthik.todo.Screens.AddTodo.MVP.AddTodoPresenterContract;
import com.karthik.todo.Screens.AddTodo.MVP.AddTodoViewContract;
import com.karthik.todo.R;
import com.karthik.todo.TodoApp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karthikr on 8/8/17.
 */

public class AddTodoActivity extends AppCompatActivity
        implements AddTodoViewContract{

    @BindView(R.id.todotitle)
    TextInputEditText todoTitle;


    @Inject
    AddTodoPresenterContract presenter;
    private AddTodoComponent addTodoComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        ButterKnife.bind(this);
        addTodoComponent = ((TodoApp)getApplication())
                .getComponent()
                .plus(new AddTodoModule(this));
        addTodoComponent.inject(this);
    }

    @OnClick(R.id.save_fab)
    public void saveTodo(){
        presenter.saveTodo();
    }

    @OnClick(R.id.cross_button)
    public void crossClicked(){
        finish();
    }

    @Override
    public boolean isTodoValidTitle() {
        todoTitle.setError(null);
        return todoTitle.getText()!=null
                && !(todoTitle.getText().toString().trim().isEmpty());
    }

    @Override
    public void showAppropriateError() {
        if(todoTitle.getText().toString().trim().isEmpty()){
            todoTitle.setError(getString(R.string.todo_title_error));
        }
    }

    @Override
    public String getTodoTitle() {
        return todoTitle.getText().toString();
    }

    @Override
    public void showSaveSuccessMessage() {
        Toast.makeText(this,getString(R.string.save_success),
                Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.closeDb();
        addTodoComponent = null;
    }
}
