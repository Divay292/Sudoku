package com.example.sudoku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SudokuBoard extends View {
    private final int boardColour;
    private final Paint boardColPaint = new Paint();
    private int cellSize;
    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a  = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SudokuBoard,
                0, 0);

        try {
            boardColour = a.getInteger(R.styleable.SudokuBoard_boardCol, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        int dimension = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight());
        cellSize = dimension / 9;

        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        boardColPaint.setStyle(Paint.Style.STROKE);
        boardColPaint.setStrokeWidth(16);
        boardColPaint.setColor(boardColour);
        boardColPaint.setAntiAlias(true);

        canvas.drawRect(0, 0, getWidth(), getHeight(), boardColPaint);
        drawBoard(canvas);
    }

    private void drawThickLine() {
        boardColPaint.setStyle(Paint.Style.STROKE);
        boardColPaint.setStrokeWidth(10);
        boardColPaint.setColor(boardColour);
    }

    private void drawThinLine() {
        boardColPaint.setStyle(Paint.Style.STROKE);
        boardColPaint.setStrokeWidth(4);
        boardColPaint.setColor(boardColour);
    }

    private void drawBoard(Canvas canvas) {
        for (int i = 0; i < 10; i++) {
            if (i%3 == 0) {
                drawThickLine();
            } else {
                drawThinLine();
            }
            canvas.drawLine(cellSize * i, 0, cellSize * i,
                    getWidth(), boardColPaint);
        }
        for (int j = 0; j < 10; j++) {
            if (j%3 == 0) {
                drawThickLine();
            } else {
                drawThinLine();
            }
            canvas.drawLine(0, cellSize*j, getWidth(),
                    cellSize*j, boardColPaint);
        }
    }
}
