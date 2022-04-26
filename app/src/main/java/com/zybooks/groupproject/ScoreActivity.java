package com.zybooks.groupproject;

import androidx.annotation.NonNull;
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

                case R.id.options_icon:
                    Intent intentOptions = new Intent(ScoreActivity.this, OptionsActivity.class);
                    startActivity(intentOptions);
                    break;

            }
            return false;
        });
        setHighScores();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.score_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Determine which menu option was chosen
        if (item.getItemId() == R.id.reset_scores) {
            deleteFile("high_score_list");
            setHighScores();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setHighScores() {
        LinearLayout leaderBoard = findViewById(R.id.leader_board);
        FileInputStream inputStream = null;
        try {
            inputStream = openFileInput("high_score_list");
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
                    switch (i) {
                        case 0:
                            holder.setBackgroundColor(getResources().getColor(R.color.gold));
                            holder.setText("1st" + "  " + String.valueOf(sortedScores.get(i)));
                            break;
                        case 1:
                            holder.setBackgroundColor(getResources().getColor(R.color.silver));
                            holder.setText("2nd" + "  " + String.valueOf(sortedScores.get(i)));
                            break;
                        case 2:
                            holder.setBackgroundColor(getResources().getColor(R.color.bronze));
                            holder.setText("3rd" + "  " + String.valueOf(sortedScores.get(i)));
                            break;
                        default:
                            holder.setText(i+1 + "th" + "  " + String.valueOf(sortedScores.get(i)));
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e) {
            TextView holder = (TextView) leaderBoard.getChildAt(0);
            holder.setBackgroundColor(getResources().getColor(R.color.white));
            holder.setText(R.string.no_scores_yet);
            e.printStackTrace();
        }
    }
}