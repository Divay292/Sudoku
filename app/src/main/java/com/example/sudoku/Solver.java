package com.example.sudoku;

import java.util.ArrayList;

public class Solver {

    int[][] board;
    ArrayList<ArrayList<Object>> emptyBoxIndex;

    int selected_row;
    int selected_col;

//    board = new int[][] {{0, 0, 0, 0, 0, 0, 0, 0, 0},
//                         {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                         {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                         {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                         {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                         {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                         {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                         {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                         {0, 0, 0, 0, 0, 0, 0, 0, 0}};


    Solver() {
        selected_row = -1;
        selected_col = -1;

        board = new int[9][9];

        for (int r=0; r<9; r++) {
            for (int c=0; c<9; c++) {
                board[r][c] = 0;
            }
        }

        emptyBoxIndex = new ArrayList<>();
    }

    private void getEmptyBoxIndexes() {
        for (int r=0; r<9; r++) {
            for (int c=0; c<9; c++) {
                if (this.board[r][c] == 0) {
                    this.emptyBoxIndex.add(new ArrayList<>());
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size()-1).add(r);
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size()-1).add(c);
                }
            }
        }
    }

    public void setNumberPos(int num) {
        if (this.selected_row !=1 && this.selected_col != -1) {
            if (this.board[this.selected_row-1][this.selected_col-1] == num) {
                this.board[this.selected_row-1][this.selected_col-1] = 0;
            }
            else {
                this.board[this.selected_row-1][this.selected_col-1] = num;
            }
        }
    }

    public int[][] getBoard() {
        return this.board;
    }

    public ArrayList<ArrayList<Object>> getEmptyBoxIndex() {
        return this.emptyBoxIndex;
    }
    public int getSelectedRow() {
        return selected_row;
    }

    public int getSelectedCol() {
        return selected_col;
    }

     public void setSelectedRow(int r) {
        selected_row = r;
     }

    public void setSelectedCol(int c) {
        selected_col = c;
    }
}
