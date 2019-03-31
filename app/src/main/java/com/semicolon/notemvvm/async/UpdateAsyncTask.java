package com.semicolon.notemvvm.async;

import android.os.AsyncTask;
import com.semicolon.notemvvm.database.Note;
import com.semicolon.notemvvm.database.NoteDao;

public class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {

  private NoteDao noteDao;

  public UpdateAsyncTask(NoteDao noteDao) {
    this.noteDao = noteDao;
  }

  @Override
  protected Void doInBackground(Note... notes) {
    noteDao.update(notes[0]);
    return null;
  }
}
