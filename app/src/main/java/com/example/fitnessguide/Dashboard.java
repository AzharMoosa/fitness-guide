package com.example.fitnessguide;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.fitnessguide.menu.Body;
import com.example.fitnessguide.menu.Chat;
import com.example.fitnessguide.menu.Home;
import com.example.fitnessguide.menu.Nutrition;
import com.example.fitnessguide.menu.Settings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class Dashboard extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);
    BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_view);
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.nav_fragment, new Home())
        .commit();
    navigationView.setOnNavigationItemSelectedListener(navListener);
  }

  // Bottom Navbar
  private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
      new OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          Fragment selectedFragment = null;
          switch (item.getItemId()) {
            case R.id.navigation_nutrition:
              selectedFragment = new Nutrition();
              break;
            case R.id.navigation_body:
              selectedFragment = new Body();
              break;
            case R.id.navigation_chat:
              selectedFragment = new Chat();
              break;
            case R.id.navigation_settings:
              selectedFragment = new Settings();
              break;
            case R.id.navigation_home:
            default:
              selectedFragment = new Home();
              break;
          }
          getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.nav_fragment, selectedFragment)
              .commit();
          return true;
        }
      };

  // Disable Back Button
  @Override
  public void onBackPressed() {}

}
