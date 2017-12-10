package com.anilkaynar.todolist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by anilkaynar on 11.12.2017.
 */
@Entity(tableName = "todo")
public class ToDo {
    @PrimaryKey(autoGenerate=true)
    public int id;
    public String metin;
    @ColumnInfo(index = true)
    public Date tarih;
    public String zaman;
    public boolean isDone;
}
