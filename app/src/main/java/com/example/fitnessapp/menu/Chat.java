package com.example.fitnessapp.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.fitnessapp.R;
import com.example.fitnessapp.chat.ChatRoom;

public class Chat extends Fragment {

  public Chat() {}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Create View
    View view = inflater.inflate(R.layout.fragment_chat, container, false);

    // General Chat
    TextView generalChat = view.findViewById(R.id.join_room_general);
    generalChat.setOnClickListener(
        v -> {
          Intent intent = new Intent(v.getContext(), ChatRoom.class);
          intent.putExtra("roomName", "General");
          startActivity(intent);
        });

    // Fitness Chat
    TextView fitnessChat = view.findViewById(R.id.join_room_fitness);
    fitnessChat.setOnClickListener(
        v -> {
          Intent intent = new Intent(v.getContext(), ChatRoom.class);
          intent.putExtra("roomName", "Fitness");
          startActivity(intent);
        });

    // Nutrition Chat
    TextView nutritionChat = view.findViewById(R.id.join_room_nutrition);
    nutritionChat.setOnClickListener(
        v -> {
          Intent intent = new Intent(v.getContext(), ChatRoom.class);
          intent.putExtra("roomName", "Nutrition");
          startActivity(intent);
        });

    // Health Chat
    TextView healthChat = view.findViewById(R.id.join_room_health);
    healthChat.setOnClickListener(
        v -> {
          Intent intent = new Intent(v.getContext(), ChatRoom.class);
          intent.putExtra("roomName", "Health");
          startActivity(intent);
        });

    return view;
  }
}
