package com.anilkaynar.todolist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by anilkaynar on 11.12.2017.
 */

public class ToDoViewModel extends AndroidViewModel {
    DatabaseToDo db;
    LiveData<List<ToDo>> toDoMutableLiveData;

    public ToDoViewModel(@NonNull Application application) {
        super(application);
        db = DatabaseToDo.getAppDatabase(application.getApplicationContext());
    }

    public LiveData<List<ToDo>> getAllTodos() {
        toDoMutableLiveData = db.toDoDao().getLiveMevzu();
        return toDoMutableLiveData;
    }

}
