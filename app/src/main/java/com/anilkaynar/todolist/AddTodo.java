package com.anilkaynar.todolist;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTodo extends AppCompatActivity {
    DatabaseToDo db;
    Spinner spinner;
    EditText dateEditText;
    EditText content;
    EditText timeEditText;
    CheckBox calenderChec;
    Calendar calendar = Calendar.getInstance();
    Calendar timec = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    TimePickerDialog.OnTimeSetListener timeSetListener;
    TimePickerDialog pickerDialog;
    NotificationManager notificationManager;
    NotificationCompat.Builder notificationBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        dateEditText = findViewById(R.id.date);
        timeEditText = findViewById(R.id.editTime);
        content = findViewById(R.id.contentto);
        db = DatabaseToDo.getAppDatabase(this);
        spinner = findViewById(R.id.priorityed);
        calenderChec = findViewById(R.id.cekbox5);
        spinner.setAdapter(ArrayAdapter.createFromResource(this, R.array.priority_array, android.R.layout.simple_spinner_dropdown_item));
        ToDoViewModel vi = ViewModelProviders.of(this).get(ToDoViewModel.class);
        vi.getAllTodos().observe(this, listlive -> {
        });
        //db.toDoDao().InsertOne(new ToDo("Yemek yap","12.10.2014","15.00"));
        db.toDoDao().InsertOne(new ToDo("Kalem al", "12.10.2015", "15.30", Byte.parseByte("6")));
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //  calendar.set(year,month,day);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                dateToEditText(calendar);
            }
        };
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                timec.set(Calendar.HOUR, hour);
                timec.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.HOUR, hour);
                calendar.set(Calendar.MINUTE, minute);
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
                new TimePickerDialog(AddTodo.this, timeSetListener, timec.get(Calendar.HOUR_OF_DAY),
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
        Date date1 = timec.getTime();
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        timeEditText.setText(formatter.format(date1));
    }

    private void AddtoDb(ToDo toDo) {
        db.toDoDao().InsertOne(toDo);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void add2Db(View v) throws ParseException {
        ToDo todor = null;
        byte priority = Byte.parseByte(spinner.getSelectedItem().toString());

        if (content.getText() != null) {
            if (dateEditText.getText() != null) {
                if (timeEditText.getText() != null) {
                    todor = new ToDo(content.getText().toString(),
                            dateEditText.getText().toString(), timeEditText.getText().toString(), priority);
                    db.toDoDao().InsertOne(todor);
                } else {
                    todor = new ToDo(content.getText().toString(),
                            dateEditText.getText().toString(), "00:01", priority);
                    db.toDoDao().InsertOne(todor);
                }
            } else {
                if (timeEditText != null) {
                    todor = new ToDo(content.getText().toString(),
                            "", timeEditText.getText().toString(), priority);
                    db.toDoDao().InsertOne(todor);
                } else {
                    todor = new ToDo(content.getText().toString(),
                            "", "00:01", priority);
                    db.toDoDao().InsertOne(todor);
                }
            }

        }
        LocalDate date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = calendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalTime localTime = timec.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            Date deneme = calendar.getTime();
            deneme.setHours(timec.getTime().getHours());
            deneme.setMinutes(timec.getTime().getMinutes());
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel("4444", "Todoapp", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
            Intent intent = new Intent(this, Main2Activity.class);
            PendingIntent activity = PendingIntent.getActivity(this, 444, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            Notification notification = new NotificationCompat.Builder(this, notificationChannel.getId())
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentText(todor.metin)
                    .setContentTitle("What TO DO")
                    .setPriority(todor.priority)
                    .setGroup("444")
                    .setShowWhen(true)
                    .setWhen(calendar.getTimeInMillis())
                    .setContentIntent(activity)
                    .build();
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), activity);
            Log.e("Mevzudur", "" + calendar.getTimeInMillis());
            notificationManager.notify(444, notification);
        }
        if (calenderChec.isChecked()) {
            addToGoogleCalender(todor);
        } else {
            startActivity(new Intent(this, Main2Activity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Resultcode", "Add to me" + resultCode);
        startActivity(new Intent(this, Main2Activity.class));


    }

    public void addToGoogleCalender(ToDo tudor) throws ParseException {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, 2);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_INSERT);
            Uri uri = CalendarContract.Events.CONTENT_URI;
            intent.setData(uri);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD/MM/YYYY", Locale.US);
            Log.e("Timed Exception", simpleDateFormat.parse(tudor.tarih).getTime() + " " + tudor.tarih);

            intent.putExtra(CalendarContract.Events.TITLE, "todo" + tudor.tarih)
                    .putExtra(CalendarContract.Events.DESCRIPTION, tudor.metin)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, simpleDateFormat.parse(tudor.tarih).getTime());
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, 1);
            }
            //startActivity(new Intent(this, Main2Activity.class));

        }
            /*Intent calenderintent = new Intent(Intent.ACTION_EDIT);
            calenderintent.setType("vnd.android.cursor.item/event");
            calenderintent.putExtra("hey", 1);
            */


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("metinEditText", content.getText().toString());
        outState.putString("timeEditText", timeEditText.getText().toString());
        outState.putString("dateEditText", dateEditText.getText().toString());
        outState.putBoolean("calendercheck", calenderChec.isChecked());
        outState.putInt("priority", spinner.getSelectedItemPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        content.setText(savedInstanceState.getString("metinEditText"));
        timeEditText.setText(savedInstanceState.getString("timeEditText"));
        dateEditText.setText(savedInstanceState.getString("dateEditText"));
        calenderChec.setChecked(savedInstanceState.getBoolean("calendercheck"));
        spinner.setSelection(savedInstanceState.getInt("priority"));
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
//http://www.zoftino.com/how-to-read-and-write-calendar-data-in-android