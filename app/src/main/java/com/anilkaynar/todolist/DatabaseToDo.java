package com.anilkaynar.todolist;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by anilkaynar on 11.12.2017.
 */
@android.arch.persistence.room.Database(entities = {ToDo.class},version = 1)
public abstract class DatabaseToDo extends RoomDatabase {
    private static   DatabaseToDo Singleton;

    public static DatabaseToDo getAppDatabase(Context context) {
        if (Singleton == null) {
            Singleton =
                    (DatabaseToDo) Room.databaseBuilder(context.getApplicationContext(),DatabaseToDo.class,"TodoDb")
                            // allow queries on the main thread.
                            .allowMainThreadQueries()
                            .build();
        }
        return Singleton;
    }
    public static void DestroyInstance(){
        Singleton=null;
    }
    public abstract ToDoDao toDoDao();
}
