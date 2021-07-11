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

/**
 * A simple {@link Fragment} subclass. Use the {@link Chat#newInstance} factory method to create an
 * instance of this fragment.
 */
public class Chat extends Fragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public Chat() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment Chat.
   */
  // TODO: Rename and change types and number of parameters
  public static Chat newInstance(String param1, String param2) {
    Chat fragment = new Chat();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_chat, container, false);
    // General Chat
    TextView generalChat = view.findViewById(R.id.join_room_general);
    generalChat.setOnClickListener(v -> {
      Intent intent = new Intent(v.getContext(), ChatRoom.class);
      intent.putExtra("roomName", "General");
      startActivity(intent);
    });
    // Fitness Chat
    TextView fitnessChat = view.findViewById(R.id.join_room_fitness);
    fitnessChat.setOnClickListener(v -> {
      Intent intent = new Intent(v.getContext(), ChatRoom.class);
      intent.putExtra("roomName", "Fitness");
      startActivity(intent);
    });
    // Nutrition Chat
    TextView nutritionChat = view.findViewById(R.id.join_room_nutrition);
    nutritionChat.setOnClickListener(v -> {
      Intent intent = new Intent(v.getContext(), ChatRoom.class);
      intent.putExtra("roomName", "Nutrition");
      startActivity(intent);
    });
    // Health Chat
    TextView healthChat = view.findViewById(R.id.join_room_health);
    healthChat.setOnClickListener(v -> {
      Intent intent = new Intent(v.getContext(), ChatRoom.class);
      intent.putExtra("roomName", "Health");
      startActivity(intent);
    });


    return view;
  }
}