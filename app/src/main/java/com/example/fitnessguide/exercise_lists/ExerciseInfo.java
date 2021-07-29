package com.example.fitnessguide.exercise_lists;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessguide.R;

public class ExerciseInfo extends AppCompatActivity {

  private TextView nameLabel;
  private TextView descriptionLabel;
  private String name;
  private String desc;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exercise_info);
    nameLabel = findViewById(R.id.exercise_name);
    descriptionLabel = findViewById(R.id.exercise_desc);
    name = getIntent().getStringExtra("exerciseName");
    desc = getIntent().getStringExtra("exerciseDesc");
    nameLabel.setText(name);
    descriptionLabel.setText(desc);
  }

  public void backBtn(View v) {
    this.finish();
  }
}