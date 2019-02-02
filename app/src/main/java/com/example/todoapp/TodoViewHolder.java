package com.example.todoapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView todoTitle,todoTime;
    private CardView todoItem;
    private AlertDialog mPopupWindow;
    private TodoDeleteListner mListener;


    public TodoViewHolder(@NonNull View view) {
        super(view);

        todoItem=view.findViewById(R.id.cardView);
        todoTitle=view.findViewById(R.id.toDoListItemTextview);
        todoTime=view.findViewById(R.id.todoListItemTimeTextView);

        todoItem.setOnClickListener(this);
    }

    public TodoViewHolder setListener(TodoDeleteListner listner){
        mListener=listner;
        return this;
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.cardView){
            View view = LayoutInflater.from(v.getContext()).inflate(R.layout.todopopup,null);
            TextView delete=view.findViewById(R.id.delete);
            TextView share=view.findViewById(R.id.share);

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setView(view);
            mPopupWindow = builder.create();
            mPopupWindow.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            mPopupWindow.show();
            delete.setOnClickListener(this);
            share.setOnClickListener(this);
        }

        else if (v.getId()==R.id.delete){
            TodoDatabaseHelper db= new TodoDatabaseHelper(v.getContext());
                if (db.deleteTodoTask(todoTitle.getText().toString())){
                        Toast.makeText(v.getContext(), "Todo Task Deleted", Toast.LENGTH_SHORT).show();
                        mListener.todoTaskDeleted();
                        mPopupWindow.dismiss();
                    }

                    else {
                        Toast.makeText(v.getContext(),"Todo Task Not Deleted",Toast.LENGTH_SHORT).show();
                    }
                }
        else if (v.getId() == R.id.share){
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                String data= "Task Name: " +todoTitle.getText().toString() +"\n"
                            + "Task Time: " +todoTime.getText().toString() + "\n";

                share.putExtra(Intent.EXTRA_SUBJECT, "Task");
                share.putExtra(Intent.EXTRA_TEXT,data);
                v.getContext().startActivity(Intent.createChooser(share,"Share Task Details"));
                mPopupWindow.dismiss();

        }


    }

}
