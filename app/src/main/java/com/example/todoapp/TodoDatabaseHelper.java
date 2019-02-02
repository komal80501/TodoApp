package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TodoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="Todo_db";
    private static final int DB_VERSION =1;
    private static final String TABLE_NAME = "todo";
    private static final String TODO_TITLE = "todo_title";
    private static final String TODO_DESCRIPTION = "todo_description";
    private static final String TODO_DATE = "todo_date";
    private static final String TODO_TIME = "todo_time";

    public TodoDatabaseHelper(@Nullable Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery="create table " + TABLE_NAME +
                                                "(" +
                                                    TODO_TITLE + " text primary key, " +
                                                    TODO_DESCRIPTION + " text, " +
                                                    TODO_DATE + " text, " +
                                                    TODO_TIME + " text " +
                                                     ")";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addTodoTask(TodoTask todoTask){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TODO_TITLE,todoTask.getToDoTitle());
        contentValues.put(TODO_DESCRIPTION,todoTask.getToDoDescription());
        contentValues.put(TODO_DATE,todoTask.getToDoDate());
        contentValues.put(TODO_TIME,todoTask.getToDoTime());

        try {
            long noOfRows = db.insert(TABLE_NAME, null, contentValues);
            Log.i("result : ", String.valueOf(noOfRows));

            return noOfRows >0;
        }
        catch (Exception e)
        {
            return false;
        }

    }

    public boolean deleteTodoTask(String name){
        SQLiteDatabase database = getWritableDatabase();
        int deletedRow = database.delete(TABLE_NAME, TODO_TITLE + " like '" + name + "'",  null);

        if (deletedRow >0){
            return true;
        }

        return false;
    }


    public List<TodoTask> getTodoTasks(){
        List<TodoTask> todoTaskList= new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "select * from " +TABLE_NAME ;
        Cursor cursor= db.rawQuery(query,null);

        while (cursor.moveToNext()){
            String title=cursor.getString(cursor.getColumnIndex(TODO_TITLE));
            String description=cursor.getString(cursor.getColumnIndex(TODO_DESCRIPTION));
            String date=cursor.getString(cursor.getColumnIndex(TODO_DATE));
            String time=cursor.getString(cursor.getColumnIndex(TODO_TIME));


            TodoTask todoTaskData= new TodoTask();
            todoTaskData.setToDoTitle(title);
            todoTaskData.setToDoDescription(description);
            todoTaskData.setToDoDate(date);
            todoTaskData.setToDoTime(time);
            todoTaskList.add(todoTaskData);
        }

        cursor.close();
        db.close();
        return todoTaskList;
    }
}
