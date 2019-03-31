package com.semicolon.notemvvm;

import static com.semicolon.notemvvm.Constant.NOTE_DESC;
import static com.semicolon.notemvvm.Constant.NOTE_ID;
import static com.semicolon.notemvvm.Constant.NOTE_PRIORITY;
import static com.semicolon.notemvvm.Constant.NOTE_TITLE;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.semicolon.notemvvm.adapter.NotesAdapter;
import com.semicolon.notemvvm.adapter.NotesAdapter.OnItemClickListener;
import com.semicolon.notemvvm.database.Note;
import com.semicolon.notemvvm.viewModel.NoteViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  NoteViewModel noteViewModel;
  List<Note> noteList = new ArrayList<>();
  private RecyclerView notesRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    noteViewModel = ViewModelProviders.of(MainActivity.this).get(NoteViewModel.class);

    notesRecyclerView = findViewById(R.id.main_activity_rv_displayNotes);
    notesRecyclerView.setHasFixedSize(true);
    notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    final NotesAdapter adapter = new NotesAdapter();
    notesRecyclerView.setAdapter(adapter);

    noteViewModel.getNoteLiveData().observe(this, new Observer<List<Note>>() {
      @Override
      public void onChanged(@Nullable List<Note> notes) {
        //update recycler view
        adapter.submitList(notes);
      }
    });

    setTitle("My Notes");

    FloatingActionButton addActionButton = findViewById(R.id.main_activity_btn_addAction);
    addActionButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent in = new Intent(MainActivity.this, AddNoteActivity.class);
        startActivity(in);

      }
    });

    new ItemTouchHelper(new SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder,
          @NonNull ViewHolder viewHolder1) {
        return false;
      }

      @Override
      public void onSwiped(@NonNull ViewHolder holder, int i) {
        final int position = holder.getAdapterPosition();
        Note note = adapter.getNoteAt(position);
        noteViewModel.delete(note);
        adapter.notifyItemRemoved(position);
        Toast.makeText(MainActivity.this, "Note deleted...", Toast.LENGTH_SHORT).show();


      }
    }).attachToRecyclerView(notesRecyclerView);

    adapter.setListener(new OnItemClickListener() {
      @Override
      public void onItemClicked(Note note) {
        Intent in = new Intent(MainActivity.this, AddNoteActivity.class);
        in.putExtra(NOTE_TITLE, note.getTitle());
        in.putExtra(NOTE_DESC, note.getDescription());
        in.putExtra(NOTE_PRIORITY, note.getPriority());
        in.putExtra(NOTE_ID, note.getId());
        startActivity(in);
      }
    });


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_activity_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    if (itemId == R.id.action_delete_all) {
      noteViewModel.deleteAll();
      Toast.makeText(this, "All notes deleted...", Toast.LENGTH_SHORT).show();
    }
    return super.onOptionsItemSelected(item);
  }
}
