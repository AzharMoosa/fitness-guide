package com.example.fitnessapp.settings;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessapp.R;

public class About extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);
  }

  public void backBtn(View v) {
    this.finish();
  }
}