package com.zybooks.groupproject;

import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.Random;
import java.util.Scanner;

public class Game {
    final static int GRID_SIZE = 4;
    int[][] tileArray;
    Random randGen = new Random();

    public Game() {
        tileArray = new int[GRID_SIZE][GRID_SIZE];
    }

    public void newGame() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                tileArray[row][col] = 0;
            }
        }
        addTile();
        addTile();
    }

    //add a new tile to the board
    public void addTile() {
        int xPos = randGen.nextInt(GRID_SIZE);
        int yPos = randGen.nextInt(GRID_SIZE);
        while (isTaken(xPos, yPos)) {
            xPos = randGen.nextInt(GRID_SIZE);
            yPos = randGen.nextInt(GRID_SIZE);
        }
        if (randGen.nextInt(5) == 0) { //new tiles have 20% chance to have a value of 4
            tileArray[xPos][yPos] = 4;
        }
        else {
            tileArray[xPos][yPos] = 2;
        }
    }

    //Check if that tile already has a number in it
    public boolean isTaken(int x, int y) {
        boolean taken = false;
        if (tileArray[x][y] != 0) {
            taken = true;
        }
        return taken;
    }

    public int getValue(int row, int col) {
        return tileArray[row][col];
    }

    public int getScore() {
        int score = 0;
        for (int row = 0; row < Game.GRID_SIZE; row++) {
            for (int col = 0; col < Game.GRID_SIZE; col++) {
                score = score + tileArray[row][col];
            }
        }
        return score;
    }

    public void move(String direction) {
        int currentRow = 0;
        int currentCol = 0;
        switch (direction) {
            case "up":
                for (int row=1; row<GRID_SIZE; row++) {
                    currentRow = 0;
                    for (int col=0; col<GRID_SIZE; col++) {
                        currentRow = row;
                        while (currentRow > 0) {
                            if (isTaken(currentRow-1, col)) {
                                if (tileArray[currentRow-1][col] == tileArray[currentRow][col]) {
                                    tileArray[currentRow-1][col] += tileArray[currentRow][col];
                                    tileArray[currentRow][col] = 0;
                                }
                                currentRow--;
                            }
                            else {
                                tileArray[currentRow-1][col] = tileArray[currentRow][col];
                                tileArray[currentRow][col] = 0;
                                currentRow--;
                            }
                        }
                    }
                }
                addTile();
                break;

            case "down":
                for (int row=GRID_SIZE-2; row>=0; row--) {
                    currentRow = GRID_SIZE-1;
                    for (int col=0; col<GRID_SIZE; col++) {
                        currentRow = row;
                        while (currentRow < GRID_SIZE-1) {
                            if (isTaken(currentRow+1, col)) {
                                if (tileArray[currentRow+1][col] == tileArray[currentRow][col]) {
                                    tileArray[currentRow+1][col] += tileArray[currentRow][col];
                                    tileArray[currentRow][col] = 0;
                                }
                                currentRow++;
                            }
                            else {
                                tileArray[currentRow+1][col] = tileArray[currentRow][col];
                                tileArray[currentRow][col] = 0;
                                currentRow++;
                            }
                        }
                    }
                }
                addTile();
                break;

            case "left":
                for (int col=1; col<GRID_SIZE; col++) {
                    currentCol = 0;
                    for (int row=0; row<GRID_SIZE; row++) {
                        currentCol = col;
                        while (currentCol > 0) {
                            if (isTaken(row, currentCol-1)) {
                                if (tileArray[row][currentCol-1] == tileArray[row][currentCol]) {
                                    tileArray[row][currentCol-1] += tileArray[row][currentCol];
                                    tileArray[row][currentCol] = 0;
                                }
                                currentCol--;
                            }
                            else {
                                tileArray[row][currentCol-1] = tileArray[row][currentCol];
                                tileArray[row][currentCol] = 0;
                                currentCol--;
                            }
                        }
                    }
                }
                addTile();
                break;

            case "right":
                for (int col=GRID_SIZE-2; col>=0; col--) {
                    currentCol = 0;
                    for (int row=0; row<GRID_SIZE; row++) {
                        currentCol = col;
                        while (currentCol < GRID_SIZE-1) {
                            if (isTaken(row, currentCol+1)) {
                                if (tileArray[row][currentCol+1] == tileArray[row][currentCol]) {
                                    tileArray[row][currentCol+1] += tileArray[row][currentCol];
                                    tileArray[row][currentCol] = 0;
                                }
                                currentCol++;
                            }
                            else {
                                tileArray[row][currentCol+1] = tileArray[row][currentCol];
                                tileArray[row][currentCol] = 0;
                                currentCol++;
                            }
                        }
                    }
                }
                addTile();
                break;
        }
    }
}