package com.example.fitnessguide.routines;

import static com.example.fitnessguide.auth.Authentication.getToken;
import static com.example.fitnessguide.constants.Constants.FRIDAY;
import static com.example.fitnessguide.constants.Constants.MONDAY;
import static com.example.fitnessguide.constants.Constants.SATURDAY;
import static com.example.fitnessguide.constants.Constants.SUNDAY;
import static com.example.fitnessguide.constants.Constants.THURSDAY;
import static com.example.fitnessguide.constants.Constants.TUESDAY;
import static com.example.fitnessguide.constants.Constants.WEDNESDAY;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.fitnessguide.R;
import com.example.fitnessguide.api.ApiUtilities;
import com.example.fitnessguide.api.routines_data.RoutinesData;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRoutine extends AppCompatActivity {

  private LinearLayout viewRoutineList;
  private CheckBox activeCheckbox;
  private RoutinesData currentRoutine;
  private final List<Exercise> exercises = new ArrayList<>();
  private final String[] days = {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};
  private final List<Session> sessions = new ArrayList<>();

  @RequiresApi(api = VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_routine);
    currentRoutine = (RoutinesData) getIntent().getSerializableExtra("routineData");
    activeCheckbox = findViewById(R.id.current_active2);
    activeCheckbox.setChecked(currentRoutine.getIsActive());
    initRoutines();
  }

  // Initialise Routines
  @RequiresApi(api = VERSION_CODES.N)
  private void initRoutines() {
    sessions.addAll(currentRoutine.getRoutines());
    addExercises();
  }

  // Add Exercises
  @RequiresApi(api = VERSION_CODES.N)
  private void addExercises() {
    for (Session session : sessions) {
      exercises.addAll(session.getExercises());
    }
    refreshExerciseList();
  }

  // Refresh Exercise List
  @RequiresApi(api = VERSION_CODES.N)
  private void refreshExerciseList() {
    viewRoutineList = findViewById(R.id.edit_routine_list);
    viewRoutineList.removeAllViews();
    for (String day : days) {
      displayExerciseList(day);
    }
  }

  // Displays Exercise List
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
    viewRoutineList.addView(createBtn(day));
  }

  // Generates Table Row
  private TableRow generateRow(String text, boolean day) {
    if (day) {
      return createRow(text);
    }
    return createExerciseRow(text);
  }

  // Creates Add Exercise Button
  private TableRow createBtn(String day) {
    // Creates Row
    TableRow row = new TableRow(getApplicationContext());
    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    params.setMargins(100, 0, 100, 20);
    row.setLayoutParams(params);

    // Generates Label Text
    TextView textView = new TextView(getApplicationContext());
    textView.setText("Add Exercise");
    row.setGravity(Gravity.CENTER_VERTICAL);
    textView.setTextSize(15);
    textView.setTextColor(ContextCompat.getColor(this, R.color.primary_text));

    // Generates Button
    ImageButton btn = new ImageButton(getApplicationContext());
    btn.setImageResource(R.drawable.add_exercise);
    btn.setBackground(null);

    // Set Popup Dialog
    btn.setOnClickListener(
        new OnClickListener() {
          @Override
          public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditRoutine.this);
            builder.setTitle("Add Exercise");
            LinearLayout layout = new LinearLayout(EditRoutine.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            LayoutParams params =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.setMargins(30, 10, 30, 10);

            final EditText nameInput = new EditText(EditRoutine.this);
            nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
            nameInput.setHint("Exercise Name");

            nameInput.setLayoutParams(params);
            layout.addView(nameInput);

            final EditText setInput = new EditText(EditRoutine.this);
            setInput.setInputType(InputType.TYPE_CLASS_NUMBER);
            setInput.setHint("Number of Sets");
            setInput.setLayoutParams(params);
            layout.addView(setInput);

            final EditText repInput = new EditText(EditRoutine.this);
            repInput.setInputType(InputType.TYPE_CLASS_NUMBER);
            repInput.setHint("Number of Reps");
            repInput.setLayoutParams(params);
            layout.addView(repInput);

            builder.setView(layout);

            builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                  @RequiresApi(api = VERSION_CODES.N)
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    String exerciseName = nameInput.getText().toString().trim();
                    String setNumber = setInput.getText().toString().trim();
                    String repNumber = repInput.getText().toString().trim();

                    if (!exerciseName.equals("")
                        && !setNumber.equals("")
                        && !repNumber.equals("")) {
                      exercises.add(
                          new Exercise(
                              day,
                              exerciseName,
                              Integer.parseInt(setNumber),
                              Integer.parseInt(repNumber)));
                      refreshExerciseList();
                    } else {
                      dialog.cancel();
                    }
                  }
                });
            builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                  }
                });

            builder.show();
          }
        });
    row.addView(btn);
    row.addView(textView);
    return row;
  }

  // Creates Row For Each Exercise
  private TableRow createExerciseRow(String text) {
    // Creates Row
    TableRow row = new TableRow(getApplicationContext());
    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    params.setMargins(150, 0, 150, 30);
    row.setLayoutParams(params);

    // Generates Label Text
    TextView textView = new TextView(getApplicationContext());
    textView.setText(text);
    textView.setTextSize(20);
    textView.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
    textView.setPadding(0, 10, 0, 10);
    row.addView(textView);
    return row;
  }

  // Creates Table Row
  private TableRow createRow(String text) {
    // Creates Row
    TableRow row = new TableRow(getApplicationContext());
    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    params.setMargins(150, 0, 150, 30);
    row.setLayoutParams(params);

    // Generates Label Text
    TextView textView = new TextView(getApplicationContext());
    textView.setText(text);
    textView.setTextSize(25);
    textView.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
    textView.setPadding(0, 30, 0, 30);
    row.setBackground(getDrawable(R.drawable.border));
    row.addView(textView);
    return row;
  }

  // Edit Routine
  @RequiresApi(api = VERSION_CODES.N)
  public void editRoutine(View v) {
    sessions.clear();
    for (String day : days) {
      createSession(day);
    }

    // Dialog Input For Routine Name
    AlertDialog.Builder builder = new AlertDialog.Builder(EditRoutine.this);
    builder.setTitle("Routine Name");

    LinearLayout layout = new LinearLayout(EditRoutine.this);
    layout.setOrientation(LinearLayout.VERTICAL);

    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    params.setMargins(30, 10, 30, 10);

    final EditText nameInput = new EditText(EditRoutine.this);
    nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
    nameInput.setText(currentRoutine.getName());
    nameInput.setHint("Enter Routine Name");
    nameInput.setLayoutParams(params);

    layout.addView(nameInput);

    builder.setView(layout);

    builder.setPositiveButton(
        "OK",
        new DialogInterface.OnClickListener() {
          @RequiresApi(api = VERSION_CODES.N)
          @Override
          public void onClick(DialogInterface dialog, int which) {
            String routineName = nameInput.getText().toString().trim();

            if (routineName.equals("")) {
              dialog.cancel();
            } else {
              boolean isActive = isCurrent();
              Routine routine = new Routine(routineName, isActive, sessions);

              // Call API
              apiEditRoutine(routine);
            }
          }
        });
    builder.setNegativeButton(
        "Cancel",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        });

    builder.setCancelable(false);
    builder.show();
  }

  // Edits Routine Using API
  @RequiresApi(api = VERSION_CODES.N)
  private void apiEditRoutine(Routine routine) {
    final RoutinesData routinesData =
        new RoutinesData(routine.getName(), routine.isActive(), sessions);
    ApiUtilities.getApiInterface()
        .updateRoutine(getToken(this), routinesData, currentRoutine.getId())
        .enqueue(
            new Callback<RoutinesData>() {
              @Override
              public void onResponse(Call<RoutinesData> call, Response<RoutinesData> response) {
                // Finish Activity
                startActivity(new Intent(EditRoutine.this, Routines.class));
              }

              @Override
              public void onFailure(Call<RoutinesData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Cannot Edit Routine", Toast.LENGTH_SHORT)
                    .show();
              }
            });
  }

  // Check If Routine Is Set Active
  private boolean isCurrent() {
    CheckBox checkBox = findViewById(R.id.current_active2);
    return checkBox.isChecked();
  }

  // Create Session
  @RequiresApi(api = VERSION_CODES.N)
  private void createSession(String day) {
    List<Exercise> dayExercises =
        exercises.stream()
            .filter(exercise -> exercise.getDay() != null)
            .filter(exercise -> exercise.getDay().equals(day))
            .collect(Collectors.toList());
    Session session = new Session(day, dayExercises);
    sessions.add(session);
  }

  public void backBtn(View v) {
    startActivity(new Intent(this, Routines.class));
  }
}
