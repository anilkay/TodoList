package com.anilkaynar.todolist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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
    TextToSpeech textToSpeech;
    ListView listView;
    ArrayList<ToDo> all;
    ArrayList<ToDo> forTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = DatabaseToDo.getAppDatabase(this);
        all = (ArrayList<ToDo>) db.toDoDao().getAll();
        FloatingActionButton microphone = findViewById(R.id.microphone);
        microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Add Todo");
                try {
                    startActivityForResult(intent, 4);
                } catch (ActivityNotFoundException a) {

                }
            }
        });
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.ERROR) {
                    Toast.makeText(getApplicationContext(), "Can't use Text to Speech", Toast.LENGTH_SHORT);
                } else {
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });
        listView = findViewById(R.id.listviewMain);
        LiveData<List<ToDo>> all2 = db.toDoDao().getLiveMevzu();
        final TodoAdapter adapter = new TodoAdapter(this, all);
        listView.setAdapter(adapter);
        ToDoViewModel vi = ViewModelProviders.of(this).get(ToDoViewModel.class);
        vi.getAllTodos().observe(this, listlive -> {
            listView.invalidate();
            Collections.sort(listlive, new CompareTodo());
            forTextToSpeech = (ArrayList<ToDo>) listlive;
            TodoAdapter adapter1 = new TodoAdapter(Main2Activity.this, (ArrayList<ToDo>) listlive);
            listView.setAdapter(adapter1);
            listView.setItemsCanFocus(false);
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Toast.makeText(getApplicationContext(), "Lolilop lolipop", Toast.LENGTH_LONG).show();
                    textToSpeech.speak(forTextToSpeech.get(i).metin, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                Log.e("Şekil", "Başlandı mevzuya");
                Toast.makeText(getApplicationContext(), "Adama mamma", Toast.LENGTH_LONG).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Result code", "Result Code" + resultCode);
        if (requestCode == 4 && resultCode != 0) { //Problem Solved.
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            new Thread(() -> {
                db.toDoDao().InsertOne(new ToDo(result.get(0), "1.1.2018", "00.00", Byte.parseByte("9")));
            }
            ).start();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
