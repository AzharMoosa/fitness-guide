package com.example.fitnessapp.routines;

import static com.example.fitnessapp.auth.Authentication.getToken;
import static com.example.fitnessapp.constants.Constants.FRIDAY;
import static com.example.fitnessapp.constants.Constants.MONDAY;
import static com.example.fitnessapp.constants.Constants.SATURDAY;
import static com.example.fitnessapp.constants.Constants.SUNDAY;
import static com.example.fitnessapp.constants.Constants.THURSDAY;
import static com.example.fitnessapp.constants.Constants.TUESDAY;
import static com.example.fitnessapp.constants.Constants.WEDNESDAY;

import android.content.Intent;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.fitnessapp.R;
import com.example.fitnessapp.api.ApiUtilities;
import com.example.fitnessapp.api.RoutinesData;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewRoutine extends AppCompatActivity {

  private LinearLayout viewRoutineList;
  private RoutinesData currentRoutine;
  private final List<Exercise> exercises = new ArrayList<>();
  private final String[] days = {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};
  private final List<Session> sessions = new ArrayList<>();
  private int dayCount = 0;

  @RequiresApi(api = VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_routine);
    currentRoutine = (RoutinesData) getIntent().getSerializableExtra("routineData");
    initRoutines();
  }

  @RequiresApi(api = VERSION_CODES.N)
  private void initRoutines() {
    sessions.addAll(currentRoutine.getRoutines());
    addExercises();
  }

  private void getSessions(String id) {
    ApiUtilities.getApiInterface()
        .getSessionDataById(getToken(this), id)
        .enqueue(
            new Callback<Session>() {
              @RequiresApi(api = VERSION_CODES.N)
              @Override
              public void onResponse(Call<Session> call, Response<Session> response) {
                if (response.body() != null) {
                  sessions.add(response.body());
                  dayCount++;
                  if (dayCount >= 7) {
                    addExercises();
                  }
                }
              }

              @Override
              public void onFailure(Call<Session> call, Throwable t) {}
            });
  }

  @RequiresApi(api = VERSION_CODES.N)
  private void addExercises() {
    for (Session session : sessions) {
        exercises.addAll(session.getExercises());
    }
    refreshExerciseList();
  }

  @RequiresApi(api = VERSION_CODES.N)
  private void refreshExerciseList() {
    viewRoutineList = findViewById(R.id.view_routine_list);
    viewRoutineList.removeAllViews();
    for (String day : days) {
      displayExerciseList(day);
    }
  }

  @RequiresApi(api = VERSION_CODES.N)
  private void displayExerciseList(String day) {
    List<Exercise> dayExercises =
        exercises.stream()
            .filter(exercise -> exercise.getDay() != null)
            .filter(exercise -> exercise.getDay().equals(day))
            .collect(Collectors.toList());
    for (Exercise exercise : dayExercises) {
      if (exercise.getName() != null && exercise.getSets() != 0 && exercise.getRepetitions() != 0) {
        viewRoutineList.addView(generateRow(exercise.formattedText(), false));
      } else {
        viewRoutineList.addView(generateRow(exercise.formattedText(), true));
      }
    }
  }

  private TableRow generateRow(String text, boolean day) {
    if (day) {
      return createRow(text);
    }
    return createExerciseRow(text);
  }

  private TableRow createExerciseRow(String text) {
    TableRow row = new TableRow(getApplicationContext());
    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    params.setMargins(150, 0, 150, 30);
    row.setLayoutParams(params);
    TextView textView = new TextView(getApplicationContext());
    textView.setText(text);
    textView.setTextSize(20);
    textView.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
    textView.setPadding(0, 10, 0, 10);
    row.addView(textView);
    return row;
  }

  private TableRow createRow(String text) {
    TableRow row = new TableRow(getApplicationContext());
    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    params.setMargins(150, 0, 150, 30);
    row.setLayoutParams(params);
    TextView textView = new TextView(getApplicationContext());
    textView.setText(text);
    textView.setTextSize(25);
    textView.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
    textView.setPadding(0, 30, 0, 30);
    row.setBackground(getDrawable(R.drawable.border));
    row.addView(textView);
    return row;
  }

  public void backBtn(View v) {
    this.finish();
  }

  public void deleteRoutine(View v) {
    ApiUtilities.getApiInterface().deleteRoutine(getToken(this), currentRoutine.getId()).enqueue(
        new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {

          }
        });
    startActivity(new Intent(ViewRoutine.this, Routines.class));
  }
}
