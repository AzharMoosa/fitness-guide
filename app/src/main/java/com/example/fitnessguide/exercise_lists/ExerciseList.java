package com.example.fitnessguide.exercise_lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.fitnessguide.R;
import com.example.fitnessguide.api.ApiUtilities;
import com.example.fitnessguide.api.exercise_data.ExerciseData;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseList extends AppCompatActivity {

  private LinearLayout exerciseList;
  private String exerciseType;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exercise_list);
    TextView exerciseListTitle = findViewById(R.id.exercise_list_title);
    exerciseList = findViewById(R.id.exercises_list);

    // Gets Exercise Type
    exerciseType = getIntent().getStringExtra("exerciseType");
    exerciseListTitle.setText(exerciseType);

    // Gets Exercise List
    getExercises();
  }

  private void getExercises() {
    ApiUtilities.getApiInterface()
        .getExercisesByType(exerciseType).enqueue(new Callback<List<ExerciseData>>() {
      @Override
      public void onResponse(Call<List<ExerciseData>> call,
          Response<List<ExerciseData>> response) {
        if (response.body() != null) {
          List<ExerciseData> exercises = response.body();
          for (ExerciseData exercise : exercises) {
            // Displays Each Exercise
            exerciseList.addView(createRow(exercise));
          }
        }
      }

      @Override
      public void onFailure(Call<List<ExerciseData>> call, Throwable t) {
        Toast.makeText(getApplicationContext(), "Cannot Get Exercises", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private TableRow createRow(ExerciseData routine) {
    // Creates Row
    TableRow row = new TableRow(getApplicationContext());
    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    params.setMargins(0, 0, 0, 100);
    row.setLayoutParams(params);

    // Generates Text Label
    TextView exerciseName = new TextView(getApplicationContext());
    exerciseName.setText(routine.getName());
    exerciseName.setTextSize(25);
    exerciseName.setTextColor(ContextCompat.getColor(this, R.color.primary_text));

    exerciseName.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View v) {
            Intent intent = new Intent(ExerciseList.this, ExerciseInfo.class);
            intent.putExtra("exerciseName", routine.getName());
            intent.putExtra("exerciseDesc", routine.getDescription());
            startActivity(intent);
          }
        });

    row.addView(exerciseName);
    return row;
  }

  public void backBtn(View v) {
    this.finish();
  }
}