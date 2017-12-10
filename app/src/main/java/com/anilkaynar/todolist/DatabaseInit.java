package com.anilkaynar.todolist;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * Created by anilkaynar on 11.12.2017.
 */

public class DatabaseInit {
    public static void populateAsync(@NonNull final DatabaseToDo db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final DatabaseToDo mDb;

        PopulateDbAsync(DatabaseToDo db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }
}

