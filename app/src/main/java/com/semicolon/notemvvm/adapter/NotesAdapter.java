package com.semicolon.notemvvm.adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.DiffUtil.ItemCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.semicolon.notemvvm.R;
import com.semicolon.notemvvm.database.Note;

public class NotesAdapter extends ListAdapter<Note, NotesAdapter.ViewHolder> {

  public static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new ItemCallback<Note>() {
    @Override
    public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
      return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
      return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getPriority() == newItem
          .getPriority() && oldItem.getDescription().equals(newItem.getDescription());
    }
  };

  private OnItemClickListener listener;

  public NotesAdapter() {
    super(DIFF_CALLBACK);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View itemView = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.notes_list_item, viewGroup, false);
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

    Note note = getItem(i);
    holder.priority.setText(String.valueOf(note.getPriority()));
    holder.desc.setText(note.getDescription());
    holder.title.setText(note.getTitle());


  }

  public Note getNoteAt(int position) {
    return getItem(position);
  }

  public void setListener(OnItemClickListener listener) {
    this.listener = listener;
  }

  public interface OnItemClickListener {

    void onItemClicked(Note note);
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    TextView title, desc, priority;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      title = itemView.findViewById(R.id.notes_list_item_tv_title);
      desc = itemView.findViewById(R.id.notes_list_item_tv_desc);
      priority = itemView.findViewById(R.id.notes_list_item_tv_priority);

      itemView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          int position = getAdapterPosition();
          listener.onItemClicked(getItem(position));
        }
      });
    }
  }
}
