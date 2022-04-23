package com.zybooks.groupproject;

import androidx.appcompat.app.AppCompatActivity;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreActivity extends AppCompatActivity {

    private int NUM_SCORES = 8;

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

                case R.id.how_to_play_icon:
                    Intent intentTutorial = new Intent(ScoreActivity.this, HowToPlay.class);
                    startActivity(intentTutorial);
                    break;

                default:
                    break;

            }
            return false;
        });
        setHighScores();
    }

    private void setHighScores() {
        LinearLayout leaderBoard = findViewById(R.id.leader_board);
        FileInputStream inputStream = null;
        try {
            inputStream = openFileInput("high_score_list");
        }
        catch (FileNotFoundException e) {
            TextView holder = (TextView) leaderBoard.getChildAt(0);
            holder.setText(R.string.no_scores_yet);
            e.printStackTrace();

        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            ArrayList<Integer> sortedScores = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("SCORE CHECKING: " + line);
                sortedScores.add(Integer.parseInt(line));
            }
            Collections.sort(sortedScores);
            Collections.reverse(sortedScores);
            if (sortedScores.size() < NUM_SCORES) {
                NUM_SCORES = sortedScores.size();
            }
            for (int i=0; i < NUM_SCORES; i++) {
                TextView holder = (TextView) leaderBoard.getChildAt(i);
                holder.setText(String.valueOf(sortedScores.get(i)));
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}