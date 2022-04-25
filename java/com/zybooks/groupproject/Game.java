package com.zybooks.groupproject;

import java.util.Random;

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
        int row = randGen.nextInt(GRID_SIZE);
        int col = randGen.nextInt(GRID_SIZE);
        while (tileArray[row][col] != 0) {
            row = randGen.nextInt(GRID_SIZE);
            col = randGen.nextInt(GRID_SIZE);
        }
        if (randGen.nextInt(6) == 0) { //new tiles have small chance to have a value of 4
            tileArray[row][col] = 4;
        }
        else {
            tileArray[row][col] = 2;
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

    //This function only considers the corner tiles and the center 4 tiles
    public boolean existsMergeAble(int row, int col) {
        //Top left tile
        if (row == 0 && col == 0) {
            //Check tile to right or tile below
            if (tileArray[row][col] == tileArray[row][col+1] ||
                tileArray[row][col] == tileArray[row+1][col]) {
                return true;
            }
            else {
                return false;
            }
        }
        //Bottom left tile
        else if (row == GRID_SIZE-1 && col == 0) {
            //Check tile to right or tile above
            if (tileArray[row][col] == tileArray[row][col+1] ||
                tileArray[row][col] == tileArray[row-1][col]) {
                return true;
            }
            else {
                return false;
            }
        }
        //Top right tile
        else if (row == 0 && col == GRID_SIZE-1) {
            //Check tile to left or tile below
            if (tileArray[row][col] == tileArray[row][col-1] ||
                tileArray[row][col] == tileArray[row+1][col]) {
                return true;
            }
            else {
                return false;
            }
        }
        //Bottom right tile
        else if (row == GRID_SIZE-1 && col == GRID_SIZE-1) {
            //Check tile to left or tile above
            if (tileArray[row][col] == tileArray[row][col-1] ||
                tileArray[row][col] == tileArray[row-1][col]) {
                return true;
            }
            else {
                return false;
            }
        }
        //Inner tiles
        else if (row > 0 && row < GRID_SIZE-1 && col > 0 && col < GRID_SIZE-1) {
            //Check tile above, below, to right, or to left
            if (tileArray[row][col] == tileArray[row-1][col] ||
                tileArray[row][col] == tileArray[row+1][col] ||
                tileArray[row][col] == tileArray[row][col+1] ||
                tileArray[row][col] == tileArray[row][col-1]) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean isGameOver() {
        for (int row = 0; row < Game.GRID_SIZE; row++) {
            for (int col = 0; col < Game.GRID_SIZE; col++) {
                //If there is an empty space on the grid or if two adjacent tiles can be merged
                if (tileArray[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void move(String direction) {
        boolean[][] alreadyMerged = new boolean[GRID_SIZE][GRID_SIZE];
        int currentRow;
        int currentCol;
        switch (direction) {
            case "up":
                for (int row=1; row<GRID_SIZE; row++) {
                    for (int col=0; col<GRID_SIZE; col++) {
                        currentRow = row;
                        while (currentRow > 0) {
                            if (isTaken(currentRow-1, col)) {
                                if (tileArray[currentRow-1][col] == tileArray[currentRow][col]) {
                                    if (!alreadyMerged[currentRow][col]) {
                                        tileArray[currentRow-1][col] += tileArray[currentRow][col];
                                        tileArray[currentRow][col] = 0;
                                        alreadyMerged[currentRow][col] = true;
                                    }
                                }
                            }
                            else {
                                tileArray[currentRow-1][col] = tileArray[currentRow][col];
                                tileArray[currentRow][col] = 0;
                            }
                            currentRow--;
                        }
                    }
                }
                addTile();
                break;

            case "down":
                for (int row=GRID_SIZE-2; row>=0; row--) {
                    for (int col=0; col<GRID_SIZE; col++) {
                        currentRow = row;
                        while (currentRow < GRID_SIZE-1) {
                            if (isTaken(currentRow+1, col)) {
                                if (tileArray[currentRow+1][col] == tileArray[currentRow][col]) {
                                    if (!alreadyMerged[currentRow][col]) {
                                        tileArray[currentRow+1][col] += tileArray[currentRow][col];
                                        tileArray[currentRow][col] = 0;
                                        alreadyMerged[currentRow][col] = true;
                                    }
                                }
                            }
                            else {
                                tileArray[currentRow+1][col] = tileArray[currentRow][col];
                                tileArray[currentRow][col] = 0;
                            }
                            currentRow++;
                        }
                    }
                }
                addTile();
                break;

            case "left":
                for (int col=1; col<GRID_SIZE; col++) {
                    for (int row=0; row<GRID_SIZE; row++) {
                        currentCol = col;
                        while (currentCol > 0) {
                            if (isTaken(row, currentCol-1)) {
                                if (tileArray[row][currentCol-1] == tileArray[row][currentCol]) {
                                    if (!alreadyMerged[row][currentCol]) {
                                        tileArray[row][currentCol-1] += tileArray[row][currentCol];
                                        tileArray[row][currentCol] = 0;
                                        alreadyMerged[row][currentCol] = true;
                                    }
                                }
                            }
                            else {
                                tileArray[row][currentCol-1] = tileArray[row][currentCol];
                                tileArray[row][currentCol] = 0;
                            }
                            currentCol--;
                        }
                    }
                }
                addTile();
                break;

            case "right":
                for (int col=GRID_SIZE-2; col>=0; col--) {
                    for (int row=0; row<GRID_SIZE; row++) {
                        currentCol = col;
                        while (currentCol < GRID_SIZE-1) {
                            if (isTaken(row, currentCol+1)) {
                                if (tileArray[row][currentCol+1] == tileArray[row][currentCol]) {
                                    if (!alreadyMerged[row][currentCol]) {
                                        tileArray[row][currentCol+1] += tileArray[row][currentCol];
                                        tileArray[row][currentCol] = 0;
                                        alreadyMerged[row][currentCol] = true;
                                    }
                                }
                            }
                            else {
                                tileArray[row][currentCol+1] = tileArray[row][currentCol];
                                tileArray[row][currentCol] = 0;
                            }
                            currentCol++;
                        }
                    }
                }
                addTile();
                break;
        }
    }
}