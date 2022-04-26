package com.zybooks.groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("testing");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        RadioGroup radioGroup = findViewById(R.id.darkModeRadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.isShown()) {
                    if (i == R.id.darkModeLight) {
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
                    Intent intentGame = new Intent(OptionsActivity.this, MainActivity.class);
                    startActivity(intentGame);
                    break;

                case R.id.leaderboard_icon:
                    Intent intentLeaderBoard = new Intent(OptionsActivity.this, ScoreActivity.class);
                    startActivity(intentLeaderBoard);
                    break;

                case R.id.how_to_play_icon:
                    Intent intentHelp = new Intent(OptionsActivity.this, HowToPlay.class);
                    startActivity(intentHelp);
                    break;
                default:
                    break;

            }
            return false;
        });
    }

    @Override
    protected void onNightModeChanged(int mode) {
        super.onNightModeChanged(mode);
    }
}
