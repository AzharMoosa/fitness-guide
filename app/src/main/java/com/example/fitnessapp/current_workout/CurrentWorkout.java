package com.example.fitnessapp.current_workout;

import static com.example.fitnessapp.auth.Authentication.getToken;

import android.content.Intent;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.fitnessapp.Dashboard;
import com.example.fitnessapp.R;
import com.example.fitnessapp.api.ApiUtilities;
import com.example.fitnessapp.api.RoutinesData;
import com.example.fitnessapp.routines.Exercise;
import com.example.fitnessapp.routines.Session;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentWorkout extends AppCompatActivity {

  private RoutinesData routine;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_current_workout);
    getCurrentWorkout();
  }

  private void getCurrentWorkout() {
    ApiUtilities.getApiInterface()
        .getActiveRoutine(getToken(this))
        .enqueue(
            new Callback<List<RoutinesData>>() {
              @RequiresApi(api = VERSION_CODES.N)
              @Override
              public void onResponse(
                  Call<List<RoutinesData>> call, Response<List<RoutinesData>> response) {
                if (response.body() != null) {
                  List<RoutinesData> routines = response.body();
                  if (routines.size() > 0) {
                    routine = routines.get(0);
                    displayActiveRoutine();
                  }
                }
              }

              @Override
              public void onFailure(Call<List<RoutinesData>> call, Throwable t) {
                Log.e("Error", t.getMessage());
              }
            });
  }

  @RequiresApi(api = VERSION_CODES.N)
  private void displayActiveRoutine() {
    String currentDay = getDay();
    TextView dayLabel = findViewById(R.id.current_day);
    dayLabel.setText(currentDay);
    LinearLayout currentWorkoutList = findViewById(R.id.current_workout_list);
    List<Exercise> exercises = new ArrayList<>();
    for (Session session : routine.getRoutines()) {
      exercises.addAll(session.getExercises());
    }
    List<Exercise> dayExercises =
        exercises.stream()
            .filter(exercise -> exercise.getDay() != null)
            .filter(exercise -> exercise.getDay().equals(currentDay))
            .collect(Collectors.toList());
    for (Exercise exercise : dayExercises) {
      if (exercise.getName() != null && exercise.getSets() != 0 && exercise.getRepetitions() != 0) {
        currentWorkoutList.addView(
            createExerciseRow(exercise.formattedText()));
      }
    }
  }

  private TableRow createExerciseRow(String text) {
    TableRow row = new TableRow(getApplicationContext());
    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    params.setMargins(0, 60, 0, 0);
    row.setLayoutParams(params);
    TextView textView = new TextView(getApplicationContext());
    textView.setText(text);
    textView.setTextSize(20);
    textView.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
    textView.setPadding(0, 10, 0, 10);

    row.addView(textView);

    return row;
  }

  private String getDay() {
    Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.DAY_OF_WEEK);
    switch (day) {
      case Calendar.SUNDAY:
        return "Sunday";
      case Calendar.MONDAY:
        return "Monday";
      case Calendar.TUESDAY:
        return "Tuesday";
      case Calendar.WEDNESDAY:
        return "Wednesday";
      case Calendar.THURSDAY:
        return "Thursday";
      case Calendar.FRIDAY:
        return "Friday";
      case Calendar.SATURDAY:
        return "Saturday";
      default:
        return "Error: Loading";
    }
  }

  public void backBtn(View v) {
    startActivity(new Intent(this, Dashboard.class));
  }
}
