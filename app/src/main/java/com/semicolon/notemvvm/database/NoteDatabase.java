package com.semicolon.notemvvm.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import com.semicolon.notemvvm.async.PopulateDBAsyncTask;

@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

  private static NoteDatabase instance;


  public abstract NoteDao noteDao();

  public static synchronized NoteDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context, NoteDatabase.class, "notes_database")
          .fallbackToDestructiveMigration()
          .addCallback(callback)
          .build();
    }

    return instance;
  }

  private static RoomDatabase.Callback callback = new Callback() {
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      PopulateDBAsyncTask populateDBAsyncTask = new PopulateDBAsyncTask(instance);
      populateDBAsyncTask.execute();
    }
  };


}
