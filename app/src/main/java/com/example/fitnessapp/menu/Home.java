package com.example.fitnessapp.menu;

import static com.example.fitnessapp.auth.Authentication.getToken;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.example.fitnessapp.api.ApiUtilities;
import com.example.fitnessapp.R;
import com.example.fitnessapp.api.UserData;
import com.example.fitnessapp.routines.Routines;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass. Use the {@link Home#newInstance} factory method to create an
 * instance of this fragment.
 */
public class Home extends Fragment {

  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  private String mParam1;
  private String mParam2;
  private TextView nameLabel;
  private ConstraintLayout homeUI;
  private ConstraintLayout spinner;

  public Home() {}

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment Home.
   */
  public static Home newInstance(String param1, String param2) {
    Home fragment = new Home();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  public void routineView(View v) {
    startActivity(new Intent(this.getContext(), Routines.class));
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    nameLabel = view.findViewById(R.id.user_text);
    homeUI = view.findViewById(R.id.home_ui);
    homeUI.setVisibility(View.INVISIBLE);
    spinner = view.findViewById(R.id.spinner_routines);
    ImageButton button = view.findViewById(R.id.routines_btn);
    button.setOnClickListener(v -> startActivity(new Intent(v.getContext(), Routines.class)));
    ApiUtilities.getApiInterface()
        .getUserData(getToken(getContext()))
        .enqueue(
            new Callback<UserData>() {
              @Override
              public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.body() != null) {
                  nameLabel.setText(response.body().getName());
                  spinner.setVisibility(View.INVISIBLE);
                  homeUI.setVisibility(View.VISIBLE);
                }
              }

              @Override
              public void onFailure(Call<UserData> call, Throwable t) {}
            });
    return view;
  }
}
