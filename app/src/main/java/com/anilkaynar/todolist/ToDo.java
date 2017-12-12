package com.anilkaynar.todolist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by anilkaynar on 11.12.2017.
 */
@Entity(tableName = "todo")
public class ToDo {
    @PrimaryKey(autoGenerate=true)
    public int id;
    public String metin;
    @ColumnInfo(index = true)
    public String tarih;
    public String zaman;
    public byte priority;
    public boolean isDone;

    public ToDo(String metin, String tarih, String zaman, byte priority) {
        this.metin = metin;
        this.tarih = tarih;
        this.zaman = zaman;
        isDone = false;
        this.priority = priority;
    }
}
