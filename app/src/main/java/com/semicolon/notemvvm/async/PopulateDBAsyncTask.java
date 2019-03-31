package com.semicolon.notemvvm.async;

import android.os.AsyncTask;
import com.semicolon.notemvvm.database.Note;
import com.semicolon.notemvvm.database.NoteDao;
import com.semicolon.notemvvm.database.NoteDatabase;

public class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
  private NoteDao noteDao;

  public PopulateDBAsyncTask(NoteDatabase db) {
    this.noteDao = db.noteDao();
  }

  @Override
  protected Void doInBackground(Void... voids) {
    noteDao.insert(new Note("Title 1", "Desc 1", 1));
    noteDao.insert(new Note("Title 2", "Desc 2", 2));
    noteDao.insert(new Note("Title 3", "Desc 3", 3));
    return null;
  }
}
