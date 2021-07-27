package com.example.fitnessapp.menu;

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

/**
 * A simple {@link Fragment} subclass. Use the {@link Nutrition#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Nutrition extends Fragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  private int calorieCount = 0;

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public Nutrition() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment Nutrition.
   */
  // TODO: Rename and change types and number of parameters
  public static Nutrition newInstance(String param1, String param2) {
    Nutrition fragment = new Nutrition();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
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

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_nutrition, container, false);
    Context context = getContext();
    TextView currentDate = view.findViewById(R.id.current_date);
    Button addCaloriesBtn = view.findViewById(R.id.add_calories);
    TextView calorieCounter = view.findViewById(R.id.calorie_count);
    EditText caloriesInput = view.findViewById(R.id.calorie_input);
    String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());

    if (getDate(context).equals("")) {
      setDate(date, context);
    }

    if (!date.equals(getDate(context))) {
      setDate(date, context);
      setDailyCount(0, context);
    }

    setCalorieCount(getDailyCount(context));
    calorieCounter.setText(String.valueOf(getCalorieCount()));
    currentDate.setText(date);

    addCaloriesBtn.setOnClickListener(
        v -> {
          if (getCalorieCount() > 50000) {
            return;
          }

          String input = caloriesInput.getText().toString();

          if (!input.equals("") && input.length() < 5) {
            int amount = Integer.parseInt(input);
            setCalorieCount(calorieCount + amount);
            calorieCounter.setText(String.valueOf(getCalorieCount()));
            setDailyCount(getCalorieCount(), context);
          } else {
            Toast.makeText(context, "Invalid Input", Toast.LENGTH_SHORT).show();
          }
          caloriesInput.setText("");
        });

    return view;
  }

  public int getCalorieCount() {
    return calorieCount;
  }

  public void setCalorieCount(int calorieCount) {
    this.calorieCount = calorieCount;
  }
}
