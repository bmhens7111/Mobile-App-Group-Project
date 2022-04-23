package com.zybooks.groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ScoreActivity extends AppCompatActivity {

    private final int NUM_SCORES = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.game_icon:
                    Intent intentGame = new Intent(ScoreActivity.this, MainActivity.class);
                    startActivity(intentGame);
                    break;

                default:
                    break;

            }
            return false;
        });

        try {
            setHighScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHighScores() throws IOException{
        LinearLayout leaderBoard = findViewById(R.id.leader_board);
        FileInputStream inputStream = openFileInput("high_score_list");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null && i < NUM_SCORES) {
                TextView holder = (TextView) leaderBoard.getChildAt(i);
                holder.setText(line);
                switch (i) {
                    case 0:
                        holder.setBackgroundColor(getResources().getColor(R.color.gold));
                        break;
                    case 1:
                        holder.setBackgroundColor(getResources().getColor(R.color.silver));
                        break;
                    case 2:
                        holder.setBackgroundColor(getResources().getColor(R.color.bronze));
                        break;
                    default:
                        break;
                }
                i++;
            }
        }
    }
}