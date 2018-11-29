package com.sweetworks.users.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sweetworks.users.R;
import com.sweetworks.users.fragments.SearchFragment;

/**
 * Created by César Pardo on 25/11/2018.
 */
public class Home extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    //If its coming from screen rotation avoid add the fragment again
    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.fragment_container, new SearchFragment())
          .commit();
    }
  }
}
