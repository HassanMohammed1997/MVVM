package com.semicolon.notemvvm;

import static com.semicolon.notemvvm.Constant.NOTE_DESC;
import static com.semicolon.notemvvm.Constant.NOTE_ID;
import static com.semicolon.notemvvm.Constant.NOTE_PRIORITY;
import static com.semicolon.notemvvm.Constant.NOTE_TITLE;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;
import com.semicolon.notemvvm.database.Note;
import com.semicolon.notemvvm.viewModel.NoteViewModel;

public class AddNoteActivity extends AppCompatActivity {

  private EditText titleEditText, descEditText;
  private NumberPicker picker;
  private NoteViewModel noteViewModel;
  String extraTitle, extraDesc;
  int extraPriority, extraId;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_note);

    noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
    titleEditText = findViewById(R.id.add_note_activity_et_title);
    descEditText = findViewById(R.id.add_note_activity_et_description);
    picker = findViewById(R.id.add_note_activity_picker);

    picker.setMinValue(1);
    picker.setMaxValue(10);

    if (getIntent().hasExtra("id")) {
      setTitle(getString(R.string.edit_note));
      extraTitle = getIntent().getStringExtra(NOTE_TITLE);
      extraDesc = getIntent().getStringExtra(NOTE_DESC);
      extraPriority = getIntent().getIntExtra(NOTE_PRIORITY, 0);
      extraId = getIntent().getIntExtra(NOTE_ID, -1);
      descEditText.setText(extraDesc);
      titleEditText.setText(extraTitle);
      picker.setValue(extraPriority);
    } else {
      setTitle(getString(R.string.add_note));
    }

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.add_note_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    if (itemId == R.id.action_save) {
      if (getIntent().hasExtra(NOTE_ID)) {
        updateNote();
      } else {
        saveNote();
      }
    }
    return super.onOptionsItemSelected(item);
  }

  private void updateNote() {
    String title = titleEditText.getText().toString();
    String desc = descEditText.getText().toString();
    int priority = picker.getValue();
    Note note = new Note(title, desc, priority);
    note.setId(extraId);
    noteViewModel.update(note);
    Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
    finish();
  }

  private void saveNote() {
    String title = titleEditText.getText().toString();
    String desc = descEditText.getText().toString();
    int value = picker.getValue();

    Note note = new Note(title, desc, value);
    noteViewModel.insert(note);
    Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
    finish();
  }
}
