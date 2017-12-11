package com.anilkaynar.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
Calendar cal=Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void add(View v){
        Intent i=new Intent(this,AddTodo.class);
        startActivity(i);

    }

    public void getMainActivity(View v) {
        startActivity(new Intent(this, Main2Activity.class));
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