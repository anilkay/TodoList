package com.anilkaynar.todolist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by anilkaynar on 11.12.2017.
 */
@Dao
public interface ToDoDao {
    @Query("Select * from todo")
    List<ToDo> getAll();
    @Insert
    void InsertAll(ToDo... toDos);
    @Insert
    void InsertOne(ToDo todo);
    @Delete
    void DeleteOne(ToDo todo);

    @Query("Select*from todo")
    LiveData<List<ToDo>> getLiveMevzu();
}
