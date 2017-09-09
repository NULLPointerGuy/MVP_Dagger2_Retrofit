package com.karthik.todo.Screens.Todo;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.karthik.corecommon.Models.Todo;
import com.karthik.todo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by karthikrk on 10/08/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    private RealmResults<Todo> todos;

    public TaskAdapter(RealmResults<Todo> todos){
        this.todos = todos;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row
                ,parent,false);
        return new TaskHolder(row);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        holder.todoTitle.setText(todos.get(position).getTodoTitle());
        holder.todotime.setText(todos.get(position).getNotifyTime());
        holder.doneCheck.setChecked(todos.get(position).isDone());
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    class TaskHolder extends RecyclerView.ViewHolder implements
            CompoundButton.OnCheckedChangeListener,View.OnClickListener{
        @BindView(R.id.todoTitle)
        TextView todoTitle;
        @BindView(R.id.todotime)
        TextView todotime;
        @BindView(R.id.done_check)
        CheckBox doneCheck;
        public TaskHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
            doneCheck.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if(isChecked){
                todoTitle.setPaintFlags(todoTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                return;
            }
            todoTitle.setPaintFlags(0);
        }

        @Override
        public void onClick(View view) {
            if(doneCheck.isChecked()){
                doneCheck.setChecked(false);
                return;
            }
            doneCheck.setChecked(true);
        }
    }
}
