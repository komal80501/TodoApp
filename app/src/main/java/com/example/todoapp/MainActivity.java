package com.example.todoapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TodoDeleteListner {

   private RecyclerView recyclerView;
   private TodoListAdapter adapter;
   private ArrayList<TodoTask> todoTasksList;
   private TodoDeleteListner listner;
   private TodoDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.addToDoFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(MainActivity.this,AddToDoActivity.class);
                startActivity(in);

            }
        });
        db=new TodoDatabaseHelper(this);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter= new TodoListAdapter(this,db.getTodoTasks(),listner);
        adapter.setmListner(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        //AlarmService
        AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.SECOND,5);

        Intent intent= new Intent("komal.mane.action.DISPLAY_NOTIFICATION");

        PendingIntent broadcast = PendingIntent.getBroadcast(this,100, new Intent(""),PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),broadcast);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_aboutUs) {

            Intent in =new Intent(MainActivity.this,AboutUsActivity.class);
            startActivity(in);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void todoTaskDeleted() {
        adapter.setmTodoTaskList(db.getTodoTasks());
        adapter.notifyDataSetChanged();
    }

}
