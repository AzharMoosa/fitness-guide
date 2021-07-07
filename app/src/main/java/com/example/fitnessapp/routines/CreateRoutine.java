package com.example.fitnessapp.routines;

import static com.example.fitnessapp.constants.Constants.FRIDAY;
import static com.example.fitnessapp.constants.Constants.MONDAY;
import static com.example.fitnessapp.constants.Constants.SATURDAY;
import static com.example.fitnessapp.constants.Constants.SUNDAY;
import static com.example.fitnessapp.constants.Constants.THURSDAY;
import static com.example.fitnessapp.constants.Constants.TUESDAY;
import static com.example.fitnessapp.constants.Constants.WEDNESDAY;

import android.content.DialogInterface;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.fitnessapp.R;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateRoutine extends AppCompatActivity {

  private LinearLayout createRoutineList;
  private final List<Exercise> exercises = new ArrayList<>();
  private final String[] days = {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};

  @RequiresApi(api = VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_routine);
    initRoutines();
    refreshExerciseList();
  }

  private void initRoutines() {
    for (String day : days) {
      Exercise exercise = new Exercise(day);
      exercises.add(exercise);
    }
  }

  @RequiresApi(api = VERSION_CODES.N)
  private void refreshExerciseList() {
    createRoutineList = findViewById(R.id.create_routine_list);
    createRoutineList.removeAllViews();
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
        createRoutineList.addView(generateRow(exercise.formattedText(), false));
      } else {
        createRoutineList.addView(generateRow(exercise.formattedText(), true));
      }
    }
    createRoutineList.addView(createBtn(day));
  }

  private TableRow generateRow(String text, boolean day) {
    if (day) {
      return createRow(text);
    }
    return createExerciseRow(text);
  }

  public void backBtn(View v) {
    this.finish();
  }

  private TableRow createBtn(String day) {
    TableRow row = new TableRow(getApplicationContext());
    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    params.setMargins(100, 0, 100, 20);
    row.setLayoutParams(params);
    TextView textView = new TextView(getApplicationContext());
    textView.setText("Add Exercise");
    row.setGravity(Gravity.CENTER_VERTICAL);
    textView.setTextSize(15);
    textView.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
    ImageButton btn = new ImageButton(getApplicationContext());
    btn.setImageResource(R.drawable.add_exercise);
    btn.setBackground(null);
    btn.setOnClickListener(
        new OnClickListener() {
          @Override
          public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateRoutine.this);
            builder.setTitle("Add Exercise");
            LinearLayout layout = new LinearLayout(CreateRoutine.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            LayoutParams params =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.setMargins(30, 10, 30, 10);

            final EditText nameInput = new EditText(CreateRoutine.this);
            nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
            nameInput.setHint("Exercise Name");

            nameInput.setLayoutParams(params);
            layout.addView(nameInput);

            final EditText setInput = new EditText(CreateRoutine.this);
            setInput.setInputType(InputType.TYPE_CLASS_NUMBER);
            setInput.setHint("Number of Sets");
            setInput.setLayoutParams(params);
            layout.addView(setInput);

            final EditText repInput = new EditText(CreateRoutine.this);
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
}
