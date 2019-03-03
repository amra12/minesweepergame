package com.prj.amrafeta.minesweeperhw.View;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.prj.amrafeta.minesweeperhw.MainActivity;
import com.prj.amrafeta.minesweeperhw.Model.MineSweeperModel;

public class MineSweeperView extends View {

    private Paint paintText;
    private Paint paintBg;
    private Paint paintLine;




    public static int mode = 1;

    public MineSweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBg = new Paint();
        paintBg.setColor(Color.YELLOW);
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.BLUE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        paintText = new Paint();
        paintText.setColor(Color.BLACK);
        BitmapFactory.Options options = new BitmapFactory.Options();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(),
                paintBg);

        drawGameGrid(canvas);
        drawPlayers(canvas);

        // Z-order is important!

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        paintText.setTextSize(getHeight() / 5);
    }

    private void drawPlayers(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (MineSweeperModel.getInstance().getField(i, j) == MineSweeperModel.FLAG) {
                    int ith = i * getWidth() / 5;
                    int jth = (j + 1) * getHeight() / 5;
                    canvas.drawText("F", ith, jth, paintText);
                } else if (MineSweeperModel.getInstance().getField(i, j) == MineSweeperModel.BOMB) {

                    int ith = i * getWidth() / 5;
                    int jth = (j + 1) * getHeight() / 5;
                    canvas.drawText("Q", ith, jth, paintText);
                } else if (MineSweeperModel.getInstance().getField(i, j) == MineSweeperModel.NUMBERD) {
                    int ct = 0;
                    int ith = i * getWidth() / 5;
                    int jth = (j + 1) * getHeight() / 5;

                    for (int x = -1; x < 2; x++) {

                        for (int y = -1; y < 2; y++) {
                            if (-1 < j + y && 5 > j + y) {
                                if (-1 < i + x && 5 > i + x) {
                                    if ((MineSweeperModel.getInstance().getField(i + x, j + y))
                                            == MineSweeperModel.BOMB) {
                                        ct++;

                                    }
                                }
                            }
                        }
                    }
                    canvas.drawText(ct + "", ith, jth, paintText);

                }

            }
        }
    }

    private void drawGameGrid(Canvas canvas) {
        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        // four horizontal lines
        canvas.drawLine(0, getHeight() / 5, getWidth(), getHeight() / 5,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 5, getWidth(),
                2 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 3 * getHeight() / 5, getWidth(),
                3 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 4 * getHeight() / 5, getWidth(),
                4 * getHeight() / 5, paintLine);

        // four vertical lines
        canvas.drawLine(getWidth() / 5, 0, getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(2 * getWidth() / 5, 0, 2 * getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(3 * getWidth() / 5, 0, 3 * getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(4 * getWidth() / 5, 0, 4 * getWidth() / 5, getHeight(),
                paintLine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int tX = ((int) event.getX()) / (getWidth() / 5);
            int tY = ((int) event.getY()) / (getHeight() / 5);
            if (this.mode == 1) {

                if (MineSweeperModel.getInstance().getField(tX, tY) == MineSweeperModel.EMPTY) {
                    MineSweeperModel.getInstance().changeStatus(MineSweeperModel.NUMBERD);
                    MineSweeperModel.getInstance().setField(tX, tY,
                            MineSweeperModel.getInstance().getNextPlayer());


                    invalidate();
                }
                else if (MineSweeperModel.getInstance().getField(tX, tY) == MineSweeperModel.FLAG) {
                    Snackbar.make(this,"this is flag!",Snackbar.LENGTH_LONG).show();

                }
                else if (MineSweeperModel.getInstance().getField(tX, tY) == MineSweeperModel.BOMB) {
                    MineSweeperModel.getInstance().resetModel();
                    ((MainActivity) getContext()).setMessage(
                            "Touch the game area to play"
                    );
                    Snackbar.make(this,"You hit a bomb , Game over!",Snackbar.LENGTH_LONG).show();
                    invalidate();
                }
            }
            else if (MineSweeperModel.getInstance().getField(tX, tY) == MineSweeperModel.BOMB){
                MineSweeperModel.getInstance().changeStatus(MineSweeperModel.FLAG);
                MineSweeperModel.getInstance().setField(tX, tY,
                        MineSweeperModel.getInstance().getNextPlayer());
                if(MineSweeperModel.getInstance().bombNumber()==0){
                    MineSweeperModel.getInstance().resetModel();
                    ((MainActivity) getContext()).setMessage(
                            "Touch the game area to play"
                    );
                    Snackbar.make(this,"You Won The Game!",Snackbar.LENGTH_LONG).show();
                    invalidate();
                }
                invalidate();
            }

            else if (this.mode == 2) {
                if (MineSweeperModel.getInstance().getField(tX, tY) == MineSweeperModel.EMPTY) {
                    Snackbar.make(this,"Game over!",Snackbar.LENGTH_LONG).show();
                    MineSweeperModel.getInstance().resetModel();
                    invalidate();
                }


            }
        }
        return true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }

    public void resetGame() {
        MineSweeperModel.getInstance().resetModel();
        invalidate();
    }

    public void setMode(boolean mode) {
        if(mode){this.mode=2;}

        else {this.mode = 1; }

        ((MainActivity) getContext()).setMessage(
                "Mode is: " + mode
        );
    }
}
