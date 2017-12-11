package com.anilkaynar.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTodo extends AppCompatActivity {
    DatabaseToDo db;
    EditText dateEditText;
    EditText timeEditText;
    Calendar calendar = Calendar.getInstance();
    Calendar timec = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    TimePickerDialog.OnTimeSetListener timeSetListener;
    TimePickerDialog pickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        dateEditText = findViewById(R.id.date);
        timeEditText = findViewById(R.id.editTime);
        db=DatabaseToDo.getAppDatabase(this);
        //db.toDoDao().InsertOne(new ToDo("Yemek yap","12.10.2014","15.00"));
        db.toDoDao().InsertOne(new ToDo("Kalem al", "12.10.2015", "15.30"));
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //  calendar.set(year,month,day);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_YEAR, day);
                dateToEditText(calendar);
            }
        };
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                timec.set(Calendar.HOUR, Calendar.MINUTE);
                timeToEditText(timec);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTodo.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(AddTodo.this,timeSetListener, timec.get(Calendar.HOUR_OF_DAY),
                        timec.get(Calendar.MINUTE), true).show();

            }
        });
    }

    private void dateToEditText(Calendar calender1) {
        String dateFormat = "DD/MM/YYYY";
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.US);
        dateEditText.setText(format.format(calender1.getTime()));
    }

    private void timeToEditText(Calendar timeCalendar) {
        Date date1=timec.getTime();
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        timeEditText.setText(formatter.format(date1));
    }

    private void AddtoDb(ToDo toDo){
        db.toDoDao().InsertOne(toDo);
    }
}
/*
TimePickerDialog pickerDialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Log.i("SEn",i+" "+i1);
            }
        },
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
        pickerDialog.show();
 */