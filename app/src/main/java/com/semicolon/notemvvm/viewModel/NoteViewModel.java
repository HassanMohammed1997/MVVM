package com.semicolon.notemvvm.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.semicolon.notemvvm.database.Note;
import com.semicolon.notemvvm.repository.NoteRepository;
import java.util.List;

public class NoteViewModel extends AndroidViewModel {

  private NoteRepository noteRepository;

  private LiveData<List<Note>> noteLiveData;

  public NoteViewModel(@NonNull Application application) {
    super(application);
    noteRepository = new NoteRepository(application);
    noteLiveData = noteRepository.getNoteLiveData();
  }


  public void insert(Note note) {
    noteRepository.insert(note);
  }

  public void update(Note note) {
    noteRepository.update(note);
  }

  public void delete(Note note) {
    noteRepository.delete(note);
  }

  public void deleteAll() {
    noteRepository.deleteAllNotes();
  }

  public LiveData<List<Note>> getNoteLiveData() {
    return noteLiveData;
  }
}
