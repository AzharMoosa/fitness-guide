package com.example.fitnessapp.exercise_lists;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessapp.R;

public class CoreExerciseList extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_core_exercise_list);
  }

  public void backBtn(View v) {
    this.finish();
  }
}