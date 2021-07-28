package com.example.fitnessapp.menu;

import static com.example.fitnessapp.auth.Authentication.signOut;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.fitnessapp.MainActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.settings.About;
import com.example.fitnessapp.settings.ChatSettings;
import com.example.fitnessapp.settings.HealthInformation;
import com.example.fitnessapp.settings.UserSettings;

public class Settings extends Fragment {

  public Settings() {}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Create View
    View view = inflater.inflate(R.layout.fragment_settings, container, false);

    // Gets Text Buttons
    TextView signOutBtn = view.findViewById(R.id.sign_out);
    TextView userSettings = view.findViewById(R.id.user_settings);
    TextView chatSettings = view.findViewById(R.id.chat_settings);
    TextView healthSettings = view.findViewById(R.id.health_info);
    TextView aboutSettings = view.findViewById(R.id.about_settings);

    // Sets Button On Click Listener
    signOutBtn.setOnClickListener(
        v -> {
          signOut(getContext());
          startActivity(new Intent(v.getContext(), MainActivity.class));
        });
    userSettings.setOnClickListener(
        v -> {
          startActivity(new Intent(v.getContext(), UserSettings.class));
        });
    chatSettings.setOnClickListener(
        v -> {
          startActivity(new Intent(v.getContext(), ChatSettings.class));
        });
    healthSettings.setOnClickListener(
        v -> {
          startActivity(new Intent(v.getContext(), HealthInformation.class));
        });
    aboutSettings.setOnClickListener(
        v -> {
          startActivity(new Intent(v.getContext(), About.class));
        });

    return view;
  }
}
