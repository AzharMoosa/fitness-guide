package com.example.fitnessapp.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fitnessapp.R;
import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder> {

  private List<Message> messageList;

  public ChatRoomAdapter(List<Message> messageList) {
    this.messageList = messageList;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
    return new ChatRoomAdapter.ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Message message = messageList.get(position);
    holder.name.setText(message.getName());
    holder.message.setText(message.getMessage());
  }

  @Override
  public int getItemCount() {
    return messageList.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    private final TextView name;
    private final TextView message;

    public ViewHolder(View view) {
      super(view);

      name = (TextView) view.findViewById(R.id.name);
      message = (TextView) view.findViewById(R.id.message);
    }
  }
}
