package com.anilkaynar.todolist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class CompareTodo implements Comparator<ToDo> {

    @Override
    public int compare(ToDo toDo, ToDo t1) {
        if (toDo.priority >= t1.priority) {
            return -1;
        } else {
            return 1;
        }
    }
}
public class Main2Activity extends AppCompatActivity {
    DatabaseToDo db;
    ListView listView;
    ArrayList<ToDo> all;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = DatabaseToDo.getAppDatabase(this);
        all = (ArrayList<ToDo>) db.toDoDao().getAll();
        listView = findViewById(R.id.listviewMain);
        LiveData<List<ToDo>> all2 = db.toDoDao().getLiveMevzu();
        final TodoAdapter adapter = new TodoAdapter(this, all);
        listView.setAdapter(adapter);
        ToDoViewModel vi = ViewModelProviders.of(this).get(ToDoViewModel.class);
        vi.getAllTodos().observe(this, listlive -> {
            listView.invalidate();
            Collections.sort(listlive, new CompareTodo());
            TodoAdapter adapter1 = new TodoAdapter(Main2Activity.this, (ArrayList<ToDo>) listlive);
            listView.setAdapter(adapter1);
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("Şekil", "Başlandı mevzuya");
                getApplication().getContentResolver().notifyChange(null, null);

            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), AddTodo.class), 1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
