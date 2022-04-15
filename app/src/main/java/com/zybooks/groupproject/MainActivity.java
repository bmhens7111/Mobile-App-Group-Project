package com.zybooks.groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Game mGame;
    private GridLayout mTileGrid;
    private TextView scoreField;
    private GestureDetectorCompat mDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTileGrid = findViewById(R.id.game_grid);
        scoreField = findViewById(R.id.scoreField);

        mGame = new Game();
        startGame();
        mDetector = new GestureDetectorCompat(this, new GridGestureListener());
    }

    private void startGame() {
        mGame.newGame();
        setTileValues();
        setScore();
    }

    private void setScore() {
        scoreField.setText("Score: " + String.valueOf(mGame.getScore()));
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

    public void onNewGameClick(View view) {
        startGame();
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