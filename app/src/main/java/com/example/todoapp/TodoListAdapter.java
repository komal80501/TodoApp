package com.example.todoapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoViewHolder> implements TodoDeleteListner {

    private Context mCtx;
    private List<TodoTask> mTodoTaskList;
    private TodoDeleteListner mListner;

    public TodoListAdapter(Context mCtx, List<TodoTask> mTodoTaskList, TodoDeleteListner mListner) {
        this.mCtx = mCtx;
        this.mTodoTaskList = mTodoTaskList;
        this.mListner = mListner;
    }

    public void setmTodoTaskList(List<TodoTask> mTodoTaskList) {
        this.mTodoTaskList = mTodoTaskList;
    }

    public void setmListner(TodoDeleteListner mListner) {
        this.mListner = mListner;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(mCtx);
       View view = layoutInflater.inflate(R.layout.list_item,null);
       return new TodoViewHolder(view).setListener(this);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
            TodoTask todoTask= mTodoTaskList.get(position);

            holder.todoTitle.setText(todoTask.getToDoTitle());
            holder.todoTime.setText(getTimeString(todoTask.getToDoDate() + " " + getTimeString(todoTask.getToDoTime())));

    }

    public String getTimeString(String timestamp)
    {
        Calendar c= Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm aaa ");

         timestamp= formatter.format(c.getTime());
        return timestamp;
    }

    @Override
    public int getItemCount() {
        return  mTodoTaskList.size();
    }

    @Override
    public void todoTaskDeleted() {
        mListner.todoTaskDeleted();
    }
}
