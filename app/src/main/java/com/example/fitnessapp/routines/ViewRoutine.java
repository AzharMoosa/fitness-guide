package com.example.fitnessapp  .routines;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessapp.R;

public class ViewRoutine extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_routine);
  }

  public void backBtn(View v) {
    this.finish();
  }
}