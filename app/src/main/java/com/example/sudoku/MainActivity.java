package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private SudokuBoard gameBoard;
    private Solver gameBoardSolver;

    private Button solveBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = findViewById(R.id.SudokuBoard);
        gameBoardSolver = gameBoard.getSolver();

        solveBTN = findViewById(R.id.solveButton);
    }

    public void BTNOnePress(View view) {
        gameBoardSolver.setNumberPos(1);
        gameBoard.invalidate();
    }

    public void BTNTwoPress(View view) {
        gameBoardSolver.setNumberPos(2);
        gameBoard.invalidate();
    }

    public void BTNThreePress(View view) {
        gameBoardSolver.setNumberPos(3);
        gameBoard.invalidate();
    }

    public void BTNFourPress(View view) {
        gameBoardSolver.setNumberPos(4);
        gameBoard.invalidate();
    }

    public void BTNFivePress(View view) {
        gameBoardSolver.setNumberPos(5);
        gameBoard.invalidate();
    }

    public void BTNSixPress(View view) {
        gameBoardSolver.setNumberPos(6);
        gameBoard.invalidate();
    }

    public void BTNSevenPress(View view) {
        gameBoardSolver.setNumberPos(7);
        gameBoard.invalidate();
    }

    public void BTNEightPress(View view) {
        gameBoardSolver.setNumberPos(8);
        gameBoard.invalidate();
    }

    public void BTNNinePress(View view) {
        gameBoardSolver.setNumberPos(9);
        gameBoard.invalidate();
    }
    public void solve(View view){
        if (solveBTN.getText().toString().equals(getString(R.string.Solve))) {
            solveBTN.setText(getString(R.string.Clear));
            gameBoardSolver.getEmptyBoxIndexes();

           solveBoardThread solveBoardThread = new solveBoardThread();

           new Thread(solveBoardThread).start();

           gameBoard.invalidate();

        }
        else {
            solveBTN.setText(getString(R.string.Solve));
            gameBoardSolver.resetBoard();
            gameBoard.invalidate();
        }
    }

    class solveBoardThread implements Runnable {
        @Override
        public void run() {
            gameBoardSolver.solve(gameBoard);
        }
    }

}