package com.zybooks.groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Game mGame;
    private GridLayout mTileGrid;
    private TextView scoreField;
    private GridLayout dirButtons;
    private GestureDetectorCompat mDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTileGrid = findViewById(R.id.game_grid);
        scoreField = findViewById(R.id.scoreField);
        dirButtons = findViewById(R.id.direction_buttons);

        Button upButton = findViewById(R.id.directionUp);
        upButton.setOnClickListener(v -> {
            mGame.move("up");
            setTileValues();
            setScore();
        });
        Button downButton = findViewById(R.id.directionDown);
        downButton.setOnClickListener(v -> {
            mGame.move("down");
            setTileValues();
            setScore();
        });
        Button leftButton = findViewById(R.id.directionLeft);
        leftButton.setOnClickListener(v -> {
            mGame.move("left");
            setTileValues();
            setScore();
        });
        Button rightButton = findViewById(R.id.directionRight);
        rightButton.setOnClickListener(v -> {
            mGame.move("right");
            setTileValues();
            setScore();
        });

        mGame = new Game();
        startGame();
        mDetector = new GestureDetectorCompat(this, new GridGestureListener());
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
                    tile.setTextColor(ContextCompat.getColor(this, R.color.black));
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

}