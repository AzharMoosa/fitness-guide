package com.example.fitnessapp.routines;

import static com.example.fitnessapp.auth.Authentication.getToken;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.example.fitnessapp.Dashboard;
import com.example.fitnessapp.R;
import com.example.fitnessapp.api.ApiUtilities;
import com.example.fitnessapp.api.RoutinesData;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Routines extends AppCompatActivity {

  private ConstraintLayout spinner;
  private ConstraintLayout routineUI;
  private LinearLayout routinesList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_routines);
    spinner = findViewById(R.id.spinner_routines);
    routineUI = findViewById(R.id.routines_ui);
    routinesList = findViewById(R.id.routines_list);
    routineUI.setVisibility(View.INVISIBLE);
    ApiUtilities.getApiInterface()
        .getRoutinesData(getToken(this))
        .enqueue(
            new Callback<List<RoutinesData>>() {
              @Override
              public void onResponse(
                  Call<List<RoutinesData>> call, Response<List<RoutinesData>> response) {
                if (response.body() != null) {
                  List<RoutinesData> routines = response.body();
                  for (RoutinesData routine : routines) {
                    routinesList.addView(createRow(routine));
                  }
                  spinner.setVisibility(View.INVISIBLE);
                  routineUI.setVisibility(View.VISIBLE);
                }
              }

              @Override
              public void onFailure(Call<List<RoutinesData>> call, Throwable t) {
                Log.e("Error", t.getMessage());
              }
            });
  }

  public void backBtn(View v) {
    startActivity(new Intent(this, Dashboard.class));
  }

  public void createRoutine(View v) {
    startActivity(new Intent(this, CreateRoutine.class));
  }

  private TableRow createRow(RoutinesData routine) {
    TableRow row = new TableRow(getApplicationContext());
    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    params.setMargins(0, 0, 0, 100);
    row.setLayoutParams(params);
    TextView routineName = new TextView(getApplicationContext());
    routineName.setText(routine.getName());
    routineName.setTextSize(25);
    if (routine.getIsActive()) {
      routineName.setTextColor(ContextCompat.getColor(this, R.color.blue));
    } else {
      routineName.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
    }
    routineName.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Routines.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("current_routine", routine.getId());
        editor.apply();
        Intent intent = new Intent(Routines.this,ViewRoutine.class);
        intent.putExtra("routineData", routine);
        startActivity(intent);
      }
    });
    row.addView(routineName);
    return row;
  }
}
