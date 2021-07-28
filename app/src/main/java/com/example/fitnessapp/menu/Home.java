package com.example.fitnessapp.menu;

import static com.example.fitnessapp.auth.Authentication.getToken;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.example.fitnessapp.R;
import com.example.fitnessapp.api.ApiUtilities;
import com.example.fitnessapp.api.auth_data.UserData;
import com.example.fitnessapp.current_workout.CurrentWorkout;
import com.example.fitnessapp.routines.Routines;
import com.example.fitnessapp.schedule.WorkoutSchedule;
import com.example.fitnessapp.settings.HealthInformation;
import com.example.fitnessapp.settings.UserSettings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

  private TextView nameLabel;
  private ConstraintLayout homeUI;
  private ConstraintLayout spinner;

  public Home() {}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Create View
    View view = inflater.inflate(R.layout.fragment_home, container, false);

    // Gets UI & Spinner
    nameLabel = view.findViewById(R.id.user_text);
    homeUI = view.findViewById(R.id.home_ui);
    homeUI.setVisibility(View.INVISIBLE);
    spinner = view.findViewById(R.id.spinner_routines);

    // Gets Buttons
    ImageButton routinesButton = view.findViewById(R.id.routines_btn);
    ImageButton scheduleButton = view.findViewById(R.id.schedule_btn);
    ImageButton workoutLogButton = view.findViewById(R.id.workout_log_btn);
    ImageButton healthInfoButton = view.findViewById(R.id.health_information_btn);
    ImageButton userInfoButton = view.findViewById(R.id.user_info_btn);

    // Sets Button On Click Listener
    routinesButton.setOnClickListener(
        v -> startActivity(new Intent(v.getContext(), Routines.class)));
    scheduleButton.setOnClickListener(
        v -> startActivity(new Intent(v.getContext(), WorkoutSchedule.class)));
    workoutLogButton.setOnClickListener(
        v -> startActivity(new Intent(v.getContext(), CurrentWorkout.class)));
    healthInfoButton.setOnClickListener(
        v -> startActivity(new Intent(v.getContext(), HealthInformation.class)));
    userInfoButton.setOnClickListener(
        v -> startActivity(new Intent(v.getContext(), UserSettings.class)));

    // Gets User Data
    ApiUtilities.getApiInterface()
        .getUserData(getToken(getContext()))
        .enqueue(
            new Callback<UserData>() {
              @Override
              public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.body() != null) {
                  // Sets Name Text
                  nameLabel.setText(response.body().getName());

                  // Hide Spinner & Show Home Screen
                  spinner.setVisibility(View.INVISIBLE);
                  homeUI.setVisibility(View.VISIBLE);
                }
              }

              @Override
              public void onFailure(Call<UserData> call, Throwable t) {
                Toast.makeText(getContext(), "Cannot Connect To Server", Toast.LENGTH_SHORT).show();
              }
            });
    return view;
  }
}
