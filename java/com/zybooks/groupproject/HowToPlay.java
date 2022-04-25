package com.zybooks.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HowToPlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_play);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.game_icon:
                    Intent intentGame = new Intent(HowToPlay.this, MainActivity.class);
                    startActivity(intentGame);
                    break;

                case R.id.leaderboard_icon:
                    Intent intentLeaderBoard = new Intent(HowToPlay.this, ScoreActivity.class);
                    startActivity(intentLeaderBoard);
                    break;

                case R.id.options_icon:
                    Intent intentOptions = new Intent(HowToPlay.this, Options.class);
                    startActivity(intentOptions);
                    break;

                default:
                    break;

            }
            return false;
        });
    }
}
