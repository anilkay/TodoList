package com.anilkaynar.todolist;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTodo extends AppCompatActivity {
EditText editText;
Calendar calendar=Calendar.getInstance();
DatePickerDialog.OnDateSetListener date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        editText=(EditText)findViewById(R.id.date);
       date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
              //  calendar.set(year,month,day);
               calendar.set(Calendar.YEAR, year);
               calendar.set(Calendar.MONTH, month);
              calendar.set(Calendar.DAY_OF_YEAR, day);
               dateToEditText(calendar);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTodo.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void dateToEditText(Calendar calender1){
        String dateFormat="DD/MM/YYYY";
        SimpleDateFormat format=new SimpleDateFormat(dateFormat, Locale.US);
        editText.setText(format.format(calender1.getTime()));
    }

}
