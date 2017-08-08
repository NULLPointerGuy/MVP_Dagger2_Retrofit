package com.karthik.todo.AddTodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.karthik.todo.AddTodo.DI.AddTodoComponent;
import com.karthik.todo.AddTodo.DI.AddTodoModule;
import com.karthik.todo.AddTodo.MVP.AddTodoPresenterContract;
import com.karthik.todo.AddTodo.MVP.AddTodoViewContract;
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
    @BindView(R.id.tododescription)
    TextInputEditText todoDescription;

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

    @OnClick(R.id.save)
    public void saveTodo(){
        presenter.saveTodo();
    }

    @Override
    public boolean isTodoValidTitle() {
        todoTitle.setError(null);
        return todoTitle.getText()!=null
                && !(todoTitle.getText().toString().trim().isEmpty());
    }

    @Override
    public boolean isTodoValidDetail() {
        todoDescription.setError(null);
        return todoDescription.getText()!=null
                && !(todoDescription.getText().toString().trim().isEmpty());
    }

    @Override
    public void showAppropriateError() {
        if(todoTitle.getText().toString().trim().isEmpty()){
            todoTitle.setError(getString(R.string.todo_title_error));
        }

        if(todoDescription.getText().toString().trim().isEmpty()){
            todoDescription.setError(getString(R.string.todo_description_error));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addTodoComponent = null;
    }
}
