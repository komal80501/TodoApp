package com.example.todoapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class AddToDoActivity extends AppCompatActivity {

    private ImageButton closeImageButton;
    private EditText editTextTitle,editTextDescription,editTextDate,editTextTime;
    private SwitchCompat switchCompat;

    private AlertDialog mTimeDialog;
    private TimePicker mTimePicker;
    private CardView mOkButton;
    private CardView mCancelButton;
    private DatePicker datePicker;

    private  final Calendar myCalendar = Calendar.getInstance();

    private static final int START_TIME_DIALOG = 100011;
    private static final int END_TIME_DIALOG = 100012;
    private int currentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        editTextTitle= (EditText) findViewById(R.id.toDoTitle);
        editTextDescription=(EditText) findViewById(R.id.todoDescription);
        editTextDate=(EditText) findViewById(R.id.newTodoDateEditText);
        editTextTime=(EditText) findViewById(R.id.newTodoTimeEditText);
        closeImageButton=(ImageButton) findViewById(R.id.btn_close);
        switchCompat=(SwitchCompat) findViewById(R.id.toDoDateSwitchCompat);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddToDoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });



        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                myCalendar.set(Calendar.MINUTE,minute);
                updateTime();
            }
        };

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddToDoActivity.this,time,myCalendar
                .get(Calendar.MINUTE),myCalendar.get(Calendar.HOUR_OF_DAY),false).show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.saveTodo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveData();
            }
        });
    }

    private void updateTime() {
        SimpleDateFormat stf = new SimpleDateFormat("hh:mm aa");

        editTextTime.setText(stf.format(myCalendar.getTimeInMillis()));
    }


    private void updateDate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTextDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void saveData() {
        TodoDatabaseHelper db = new TodoDatabaseHelper(this);

        String todoTitle = editTextTitle.getText().toString();
        String todoDescription =editTextDescription.getText().toString();
        String todoDate=editTextDate.getText().toString();
        String todoTime= editTextTime.getText().toString();

        if (TextUtils.isEmpty(todoTitle)){
            editTextTitle.setError("required");
            editTextTitle.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(todoDescription)){
            editTextDescription.setError("required");
            editTextDescription.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(todoDate)){
            editTextDate.setError("required");
            editTextDate.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(todoTime)){
            editTextTime.setError("required");
            editTextTime.requestFocus();
            return;
        }

        TodoTask todoTask= new TodoTask();
        todoTask.setToDoTitle(todoTitle);
        todoTask.setToDoDescription(todoDescription);
        todoTask.setToDoDate(todoDate);
        todoTask.setToDoTime(todoTime);

        if(db.addTodoTask(todoTask))
        {
            Toast.makeText(this, "data inserted", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(AddToDoActivity.this, MainActivity.class);
                startActivity(intent1);
        }
        else {
            Toast.makeText(this, " Not insert", Toast.LENGTH_SHORT).show();
        }
        }
    }


