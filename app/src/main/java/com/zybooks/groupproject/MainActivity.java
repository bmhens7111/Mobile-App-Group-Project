package com.zybooks.groupproject;

import static com.zybooks.groupproject.R.color.black;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    private Game mGame;
    private GridLayout mTileGrid;
    private TextView scoreField;
    private GestureDetectorCompat mDetector;
    private GridLayout dirButtons;

    private AlertDialog dialog;

    private final String GAME_STATE = "gameState";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTileGrid = findViewById(R.id.game_grid);
        scoreField = findViewById(R.id.scoreField);
        dirButtons = findViewById(R.id.direction_buttons);

        mGame = new Game();
        if (savedInstanceState == null) {
            startGame();
        }
        else {
            String gameState = savedInstanceState.getString(GAME_STATE);
            mGame.setState(gameState);
            setTileValues();
            setScore();
        }
        mDetector = new GestureDetectorCompat(this, new GridGestureListener());

        Button upButton = findViewById(R.id.directionUp);
        upButton.setOnClickListener(v -> {
            if (!mGame.isGameOver()) {
                mGame.move("up");
                setTileValues();
                setScore();
            }
        });
        Button downButton = findViewById(R.id.directionDown);
        downButton.setOnClickListener(v -> {
            if (!mGame.isGameOver()) {
                mGame.move("down");
                setTileValues();
                setScore();
            }
        });
        Button leftButton = findViewById(R.id.directionLeft);
        leftButton.setOnClickListener(v -> {
            if (!mGame.isGameOver()) {
                mGame.move("left");
                setTileValues();
                setScore();
            }
        });
        Button rightButton = findViewById(R.id.directionRight);
        rightButton.setOnClickListener(v -> {
            if (!mGame.isGameOver()) {
                mGame.move("right");
                setTileValues();
                setScore();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.how_to_play_icon:
                    Intent intentHowToPlay = new Intent(MainActivity.this, HowToPlay.class);
                    startActivity(intentHowToPlay);
                    break;

                case R.id.leaderboard_icon:
                    Intent intentLeaderBoard = new Intent(MainActivity.this, ScoreActivity.class);
                    startActivity(intentLeaderBoard);
                    break;

                case R.id.options_icon:
                    Intent intentOptions = new Intent(MainActivity.this, Options.class);
                    startActivity(intentOptions);
                    break;

            }
            return false;
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE, mGame.getState());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_controls, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Determine which menu option was chosen
        if (item.getItemId() == R.id.new_game) {
            startGame();
            return true;
        }
        else if (item.getItemId() == R.id.show_dir_buttons) {
            if (dirButtons.isShown()) {
                dirButtons.setVisibility(View.GONE);
            }
            else {
                dirButtons.setVisibility(View.VISIBLE);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startGame() {
        mGame.newGame();
        setTileValues();
        setScore();
    }

    private void setScore() {
        String score = getString(R.string.score, mGame.getScore());
        scoreField.setText(score);
        if (mGame.isGameOver()) {
            writeScore();
            createWinDialog();
        }
    }

    private void writeScore() {
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput("high_score_list", Context.MODE_APPEND);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintWriter writer = new PrintWriter(outputStream);
        writer.println(mGame.getScore());
        writer.close();
    }

    private void setTileValues() {
        for (int row = 0; row < Game.GRID_SIZE; row++) {
            for (int col = 0; col < Game.GRID_SIZE; col++) {

                // Find the button in the grid layout at this row and col
                int viewIndex = row * Game.GRID_SIZE + col;
                TextView tile = (TextView) mTileGrid.getChildAt(viewIndex);

                tile.setText(String.valueOf(mGame.getValue(row, col)));
                if (mGame.getValue(row, col) == 0) {
                    tile.setTextColor(ContextCompat.getColor(this, R.color.tan));
                }
                else {
                    tile.setTextColor(ContextCompat.getColor(this, black));
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    private class GridGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (velocityY < 0 && Math.abs(velocityY) > Math.abs(velocityX)) {
                //Fling upwards
                mGame.move("up");
                setTileValues();
                setScore();
            }
            else if (velocityY > 0 && Math.abs(velocityY) > Math.abs(velocityX))  {
                //Fling downwards
                mGame.move("down");
                setTileValues();
                setScore();
            }
            else if (velocityX < 0 && Math.abs(velocityX) > Math.abs(velocityY)) {
                //Fling right
                mGame.move("left");
                setTileValues();
                setScore();
            }
            else if (velocityX > 0 && Math.abs(velocityX) > Math.abs(velocityY)) {
                //Fling left
                mGame.move("right");
                setTileValues();
                setScore();
            }
            return true;
        }
    }

    public void createWinDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View winScreenPopupView = getLayoutInflater().inflate(R.layout.popup, null);

        Button winScreenPopupContinue = winScreenPopupView.findViewById(R.id.winScreenPopupContinue);
        Button winScreenPopupQuit = winScreenPopupView.findViewById(R.id.winScreenPopupQuit);
        Button winScreenPopupReset = winScreenPopupView.findViewById(R.id.winScreenPopupReset);

        dialogBuilder.setView(winScreenPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        winScreenPopupContinue.setOnClickListener(view -> {
            dialog.dismiss();
        });

        winScreenPopupQuit.setOnClickListener(view -> {
            finishAffinity();
        });


        winScreenPopupReset.setOnClickListener(view -> {
            startGame();
            dialog.dismiss();
        });
    }
}
