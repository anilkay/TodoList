package com.anilkaynar.todolist;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by anilkaynar on 11.12.2017.
 */

public class TodoAdapter extends BaseAdapter {
    ArrayList<ToDo> toDoArrayList;
    TextToSpeech textToSpeech;
    LayoutInflater layoutInflater;
    Activity activity;
    DatabaseToDo db;
    TodoAdapter(Activity activity, ArrayList<ToDo> arrayList) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        toDoArrayList = arrayList;
        this.activity = activity;
        textToSpeech = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.ERROR) {
                    Toast.makeText(activity, "Can't use Text to Speech", Toast.LENGTH_SHORT);
                } else {
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });
        db = DatabaseToDo.getAppDatabase(activity.getApplicationContext());
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return toDoArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return toDoArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View satir;
        final ToDo to = toDoArrayList.get(i);
        satir = layoutInflater.inflate(R.layout.todo_line, null);
        CheckBox c = satir.findViewById(R.id.cekbox);
        TextView content = satir.findViewById(R.id.content1);
        TextView tarih = satir.findViewById(R.id.tarih);
        TextView saat = satir.findViewById(R.id.saat);
        if (to.metin != null) {
            content.setText(to.metin);
        }
        if (to.tarih != null) {
            tarih.setText(to.tarih);
        }
        tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(activity, "Lolilop lolipop", Toast.LENGTH_LONG).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(to.metin, TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
        saat.setText(to.zaman);
        Button sendsms = satir.findViewById(R.id.sendsmsbut);
        sendsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, 1);
                String smscontent = to.metin + "\n" + to.tarih;
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("smsto:"));
                sendIntent.putExtra("sms_body", smscontent);
                activity.startActivity(sendIntent); //Sms issie work fine now
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(() -> {
                    Log.e("This is fucking error", "İsn't it");
                    db.toDoDao().DeleteOne(to);
                }).start();
                synchronized (db) {
                    db.notifyAll();
                }
            }
        });
        return satir;
    }
}
