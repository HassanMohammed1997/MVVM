package com.semicolon.notemvvm.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import com.semicolon.notemvvm.async.DeleteAllAsyncTask;
import com.semicolon.notemvvm.async.DeleteAsyncTask;
import com.semicolon.notemvvm.async.InsertAsyncTask;
import com.semicolon.notemvvm.async.UpdateAsyncTask;
import com.semicolon.notemvvm.database.Note;
import com.semicolon.notemvvm.database.NoteDao;
import com.semicolon.notemvvm.database.NoteDatabase;
import java.util.List;

public class NoteRepository {

  private NoteDao noteDao;

  private LiveData<List<Note>> noteLiveData;

  public NoteRepository(Application application) {
    NoteDatabase db = NoteDatabase.getInstance(application);
    noteDao = db.noteDao();
    noteLiveData = noteDao.getAllNotes();
  }

  public void insert(Note note) {
    new InsertAsyncTask(noteDao).execute(note);

  }

  public void update(Note note) {
    new UpdateAsyncTask(noteDao).execute(note);
  }

  public void delete(Note note) {
    new DeleteAsyncTask(noteDao).execute(note);
  }

  public void deleteAllNotes() {
    new DeleteAllAsyncTask(noteDao).execute();
  }

  public LiveData<List<Note>> getNoteLiveData() {
    return noteLiveData;
  }
}
