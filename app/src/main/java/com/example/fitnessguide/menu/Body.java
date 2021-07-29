package com.example.fitnessguide.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;
import com.example.fitnessguide.R;
import com.example.fitnessguide.exercise_lists.ExerciseList;

public class Body extends Fragment {

  public Body() {}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Create View
    View view = inflater.inflate(R.layout.fragment_body, container, false);

    // Gets Buttons
    ImageButton armLeft = view.findViewById(R.id.list_arms_btn_left);
    ImageButton armRight = view.findViewById(R.id.list_arms_btn_right);
    ImageButton shoulders = view.findViewById(R.id.list_shoulders_btn);
    ImageButton chest = view.findViewById(R.id.list_chest_btn);
    ImageButton core = view.findViewById(R.id.list_core_btn);
    ImageButton legs = view.findViewById(R.id.list_leg_btn);

    // Sets Button On Click Listener
    armLeft.setOnClickListener(
        v -> {
          Intent intent = new Intent(v.getContext(), ExerciseList.class);
          intent.putExtra("exerciseType", "Arm Exercises");
          startActivity(intent);
        });
    armRight.setOnClickListener(
        v -> {
          Intent intent = new Intent(v.getContext(), ExerciseList.class);
          intent.putExtra("exerciseType", "Arm Exercises");
          startActivity(intent);
        });
    shoulders.setOnClickListener(
        v -> {
          Intent intent = new Intent(v.getContext(), ExerciseList.class);
          intent.putExtra("exerciseType", "Shoulder Exercises");
          startActivity(intent);
        });
    chest.setOnClickListener(
        v -> {
          Intent intent = new Intent(v.getContext(), ExerciseList.class);
          intent.putExtra("exerciseType", "Chest Exercises");
          startActivity(intent);
        });
    core.setOnClickListener(
        v -> {
          Intent intent = new Intent(v.getContext(), ExerciseList.class);
          intent.putExtra("exerciseType", "Core Exercises");
          startActivity(intent);
        });
    legs.setOnClickListener(
        v -> {
          Intent intent = new Intent(v.getContext(), ExerciseList.class);
          intent.putExtra("exerciseType", "Leg Exercises");
          startActivity(intent);
        });

    return view;
  }
}
