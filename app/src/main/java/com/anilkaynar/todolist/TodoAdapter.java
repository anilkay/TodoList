package com.anilkaynar.todolist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anilkaynar on 11.12.2017.
 */

public class TodoAdapter extends BaseAdapter {
    ArrayList<ToDo> toDoArrayList;
    LayoutInflater layoutInflater;
    Activity activity;
    DatabaseToDo db;
    TodoAdapter(Activity activity, ArrayList<ToDo> arrayList) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        toDoArrayList = arrayList;
        this.activity = activity;
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
        content.setText(to.metin);
        tarih.setText(to.tarih);
        saat.setText(to.zaman);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.toDoDao().DeleteOne(to);
                synchronized (db) {
                    db.notifyAll();
                }
            }
        });
        return satir;
    }
}
