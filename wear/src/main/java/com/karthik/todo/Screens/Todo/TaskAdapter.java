package com.karthik.todo.Screens.Todo;

import android.support.wear.widget.WearableRecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthik.corecommon.Models.Todo;
import com.karthik.todo.AddTodoCallback;
import com.karthik.todo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by karthikrk on 21/09/17.
 */

public class TaskAdapter extends WearableRecyclerView.Adapter<WearableRecyclerView.ViewHolder>{
    private RealmResults<Todo> todo;
    private final int LIST_HEADER = 0;
    private final int LIST_CONTENT = 1;
    private AddTodoCallback callback;

    public TaskAdapter(RealmResults<Todo> todo, AddTodoCallback callback){
        this.todo = todo;
        this.callback = callback;
    }

    public TaskAdapter(AddTodoCallback callback){
        this.callback = callback;
    }

    @Override
    public WearableRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View taskRow;
        if(viewType==LIST_HEADER){
            taskRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row_header,
                    parent,false);
            return new TodoHeader(taskRow);
        }
        taskRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row,
                parent,false);
        return new TodoHolder(taskRow);
    }

    @Override
    public void onBindViewHolder(WearableRecyclerView.ViewHolder genericHolder, int position) {
        if(genericHolder instanceof TodoHolder){
            TodoHolder holder = (TodoHolder) genericHolder;
            holder.todoTitle.setText(todo.get(position-1).getTodoTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return LIST_HEADER;
        return LIST_CONTENT;
    }

    @Override
    public int getItemCount() {
        if(todo==null)
            return 1;
        return todo.size()+1;
    }

    class TodoHolder extends WearableRecyclerView.ViewHolder{
        @BindView(R.id.todoTitle)
        TextView todoTitle;

        public TodoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class TodoHeader extends WearableRecyclerView.ViewHolder implements View.OnClickListener{

        public TodoHeader(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            callback.onAddTodoClicked();
        }
    }
}
