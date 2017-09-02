package com.karthik.todo.Screens.AddTodo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.karthik.todo.Screens.AddTodo.DI.AddTodoComponent;
import com.karthik.todo.Screens.AddTodo.DI.AddTodoModule;
import com.karthik.todo.Screens.AddTodo.MVP.AddTodoPresenterContract;
import com.karthik.todo.Screens.AddTodo.MVP.AddTodoViewContract;
import com.karthik.todo.R;
import com.karthik.todo.TodoApp;
import com.karthik.todo.Utils;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karthikr on 8/8/17.
 */

public class AddTodoActivity extends AppCompatActivity
        implements AddTodoViewContract,SwitchCompat.OnCheckedChangeListener{

    @BindView(R.id.todotitle)
    TextInputEditText todoTitle;
    @BindView(R.id.reminder)
    Switch reminder;
    @BindView(R.id.reminderLayout)
    LinearLayout reminderLayout;
    @BindView(R.id.choosedate)
    Button date;
    @BindView(R.id.choosetime)
    Button time;


    @Inject
    AddTodoPresenterContract presenter;
    private AddTodoComponent addTodoComponent;
    private Calendar calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        ButterKnife.bind(this);
        addTodoComponent = ((TodoApp)getApplication())
                .getComponent()
                .plus(new AddTodoModule(this));
        addTodoComponent.inject(this);
        reminder.setOnCheckedChangeListener(this);
        calendar = Calendar.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setComposedDateAndTime(calendar);
    }

    @OnClick(R.id.save_fab)
    public void saveTodo(){
        presenter.saveTodo();
    }

    @OnClick(R.id.cross_button)
    public void crossClicked(){
        finish();
    }

    @OnClick(R.id.choosedate)
    public void dateSelected(){
        presenter.showDateSelection();
    }

    @OnClick(R.id.choosetime)
    public void timeSelected(){
        presenter.showTimeSelection();
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
    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year,monthOfYear,dayOfMonth);
                presenter.setComposedDateAndTime(calendar);
            }
        },Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void showTimePickerDialog() {
        TimePickerDialog dialog = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                calendar.set(Calendar.HOUR_OF_DAY,selectedHour);
                calendar.set(Calendar.MINUTE,selectedMinute);
                presenter.setComposedDateAndTime(calendar);
            }
        },Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),true);

        dialog.show();
    }

    @Override
    public void setDefaultDateAndTime(String formattedDate, String formattedTime) {
        date.setText(formattedDate);
        time.setText(formattedTime);
    }

    @Override
    public boolean isReminderSet() {
       return reminder.isChecked();
    }

    @Override
    public String getComposedReminderTime() {
        return Utils.getComposedTime(this,calendar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.closeDb();
        addTodoComponent = null;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        reminderLayout.setVisibility(isChecked?View.VISIBLE:View.GONE);
    }
}
