package com.semicolon.notemvvm.async;

import android.os.AsyncTask;
import com.semicolon.notemvvm.database.NoteDao;

public class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

  private NoteDao noteDao;

  public DeleteAllAsyncTask(NoteDao noteDao) {
    this.noteDao = noteDao;
  }

  @Override
  protected Void doInBackground(Void... voids) {
    noteDao.deleteAllNotes();
    return null;
  }
}
