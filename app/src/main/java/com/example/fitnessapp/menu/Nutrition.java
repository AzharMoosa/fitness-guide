package com.example.fitnessapp.menu;

import static com.example.fitnessapp.constants.Constants.CALORIE_INPUT_MAX;
import static com.example.fitnessapp.constants.Constants.CALORIE_LIMIT;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.fitnessapp.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Nutrition extends Fragment {

  private int calorieCount = 0;

  public Nutrition() {}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public static String getDate(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    return preferences.getString("currentDate", "");
  }

  public static void setDate(String date, Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString("currentDate", date);
    editor.apply();
  }

  public static int getDailyCount(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    return preferences.getInt("calorieCount", 0);
  }

  public static void setDailyCount(int count, Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putInt("calorieCount", count);
    editor.apply();
  }

  public int getCalorieCount() {
    return calorieCount;
  }

  public void setCalorieCount(int calorieCount) {
    this.calorieCount = calorieCount;
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Create View
    View view = inflater.inflate(R.layout.fragment_nutrition, container, false);
    Context context = getContext();

    // Gets Elements
    TextView currentDate = view.findViewById(R.id.current_date);
    Button addCaloriesBtn = view.findViewById(R.id.add_calories);
    TextView calorieCounter = view.findViewById(R.id.calorie_count);
    EditText caloriesInput = view.findViewById(R.id.calorie_input);

    // Gets Date
    String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
    if (getDate(context).equals("")) {
      setDate(date, context);
    }
    if (!date.equals(getDate(context))) {
      setDate(date, context);
      setDailyCount(0, context);
    }

    // Sets Calorie Counter
    setCalorieCount(getDailyCount(context));
    calorieCounter.setText(String.valueOf(getCalorieCount()));
    currentDate.setText(date);

    // Add Calories
    addCaloriesBtn.setOnClickListener(
        v -> {
          // Limits Calorie Count
          if (getCalorieCount() > CALORIE_LIMIT) {
            return;
          }

          // Retrieves Input
          String input = caloriesInput.getText().toString();

          // Parse Input
          if (!input.equals("") && input.length() < CALORIE_INPUT_MAX) {
            // Calculate New Calorie Amount
            int amount = Integer.parseInt(input);
            int total = calorieCount + amount;
            setCalorieCount(total);
            calorieCounter.setText(String.valueOf(total));
            setDailyCount(total, context);
          } else {
            Toast.makeText(context, "Invalid Input", Toast.LENGTH_SHORT).show();
          }

          // Clear Text Input
          caloriesInput.setText("");
        });

    return view;
  }
}
