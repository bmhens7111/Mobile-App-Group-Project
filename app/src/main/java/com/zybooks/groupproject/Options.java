package com.zybooks.groupproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Options extends AppCompatActivity{
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        radioGroup = findViewById(R.id.darkModeRadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                if(radioGroup.isShown()) {
                    if(i == R.id.darkModeLight) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                    else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                }
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.game_icon:
                    Intent intentGame = new Intent(Options.this, MainActivity.class);
                    startActivity(intentGame);
                    break;

                case R.id.how_to_play_icon:
                    Intent intentTutorial = new Intent(Options.this, HowToPlay.class);
                    startActivity(intentTutorial);
                    break;

                case R.id.leaderboard_icon:
                    Intent intentLeaderBoard = new Intent(Options.this, ScoreActivity.class);
                    startActivity(intentLeaderBoard);
                    break;

                default:
                    break;

            }
            return false;
        });
    }
}
