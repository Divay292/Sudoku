package com.example.sudoku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SudokuBoard extends View {
    private final int boardColour;
    private final int cellFillColor;
    private final int cellsHighlightColor;

    private final int letterColor;
    private final int letterColorSolve;

    private final Paint boardColPaint = new Paint();
    private final Paint cellFillColorPaint = new Paint();
    private final Paint cellsHighlightColorPaint = new Paint();
    private final Paint letterPaint = new Paint();
    private final Rect letterPaintBounds = new Rect();

    private int cellSize;

    private final Solver solver = new Solver();

    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a  = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SudokuBoard,
                0, 0);

        try {
            boardColour = a.getInteger(R.styleable.SudokuBoard_boardCol, 0);
            cellFillColor = a.getInteger(R.styleable.SudokuBoard_cellFillCol, 0);
            cellsHighlightColor = a.getInteger(R.styleable.SudokuBoard_cellsHighlightColor, 0);
            letterColor = a.getInteger(R.styleable.SudokuBoard_letterColor, 0);
            letterColorSolve = a.getInteger(R.styleable.SudokuBoard_letterColorSolve, 0);
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isValid;

        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            solver.setSelectedRow((int)Math.ceil(y/cellSize));
            solver.setSelectedCol((int)Math.ceil(x/cellSize));
            isValid = true;
        }
        else {
            isValid = false;
        }

        return isValid;
    }

    private void drawNumbers(Canvas canvas) {

        letterPaint.setTextSize(cellSize);
        for (int r=0; r<9; r++) {
            for (int c=0; c<9; c++) {
                if (solver.getBoard()[r][c] != 0) {
                    String text = Integer.toString(solver.getBoard()[r][c]);
                    float width, height;

                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();

                    canvas.drawText(text, (c*cellSize) + (cellSize - width)/2,
                            (r*cellSize + cellSize) - (cellSize - height)/2, letterPaint);
                }
            }
        }

        letterPaint.setColor(letterColorSolve);

        for (ArrayList<Object> letter : solver.getEmptyBoxIndex()) {
            int r = (int)letter.get(0);
            int c = (int)letter.get(1);

            String text = Integer.toString(solver.getBoard()[r][c]);
            float width, height;

            letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
            width = letterPaint.measureText(text);
            height = letterPaintBounds.height();

            canvas.drawText(text, (c*cellSize) + (cellSize - width)/2,
                    (r*cellSize + cellSize) - (cellSize - height)/2, letterPaint);
        }
    }

    private void colorCell(Canvas canvas, int r, int c) {
        if (solver.getSelectedCol() != -1 && solver.getSelectedRow() != -1) {
            canvas.drawRect((c-1)*cellSize, 0, c*cellSize, cellSize*9,
                    cellsHighlightColorPaint);
            canvas.drawRect(0, (r-1)*cellSize, cellSize*9, r*cellSize,
                    cellsHighlightColorPaint);
            canvas.drawRect((c-1)*cellSize, (r-1)*cellSize, c*cellSize, r*cellSize,
                    cellsHighlightColorPaint);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        boardColPaint.setStyle(Paint.Style.STROKE);
        boardColPaint.setStrokeWidth(16);
        boardColPaint.setColor(boardColour);
        boardColPaint.setAntiAlias(true);

        cellFillColorPaint.setStyle(Paint.Style.FILL);
        cellFillColorPaint.setColor(cellFillColor);
        cellFillColorPaint.setAntiAlias(true);

        cellsHighlightColorPaint.setStyle(Paint.Style.FILL);
        cellsHighlightColorPaint.setColor(cellsHighlightColor);
        cellsHighlightColorPaint.setAntiAlias(true);

        letterPaint.setStyle(Paint.Style.FILL);
        letterPaint.setAntiAlias(true);
        letterPaint.setColor(letterColor);

        colorCell(canvas, solver.getSelectedRow(), solver.getSelectedCol());
        canvas.drawRect(0, 0, getWidth(), getHeight(), boardColPaint);
        drawBoard(canvas);
        drawNumbers(canvas);
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

    public Solver getSolver() {
        return this.solver;
    }
}
