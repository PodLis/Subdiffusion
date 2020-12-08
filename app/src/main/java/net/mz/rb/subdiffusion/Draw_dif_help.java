package net.mz.rb.subdiffusion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class Draw_dif_help extends Thread {

    //private static final String LOG_TAG = "Draw_dif_help";
    private int kk, x1, y1, x2, y2, div2, div3, div4, div5, radius, difSpeed;
    private SurfaceHolder surfaceHolder;
    private volatile boolean running = true;
    private Matrix matrix;
    private Runner runner;

    public Draw_dif_help(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        radius = Functions.radius;
        difSpeed = Functions.difSpeed;
        div2 = (int) Math.pow(Functions.points, 1. / 3);
        div3 = (int) Math.pow(Functions.points, 1. / 5);
        div4 = (int) Math.pow(Functions.points, 1. / 7);
        div5 = (int) Math.pow(Functions.points, 1. / 9);
        kk = 2 * radius + 1;
        x1 = (Functions.width - kk * Functions.X) / 2;
        x2 = x1 + kk * Functions.X + 1;
        y1 = (Functions.height - kk * Functions.Y) / 2;
        y2 = y1 + kk * Functions.Y + 1;
        matrix = new Matrix(Functions.X / 2, Functions.Y / 2);
        runner = new Runner(Functions.points, Functions.Y / 2, Functions.X / 2);
    }

    @Override
    public void run() {
        try {
            sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        Paint paint = new Paint();
        Paint backgroundPaint = new Paint();
        Paint altPaint = new Paint();
        altPaint.setColor(Color.WHITE);
        backgroundPaint.setColor(Color.rgb(235, 235, 235));
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    sleep(50);
                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
                    canvas.drawRect(x1, y1, x2, y2, altPaint);
                    for (int i = 0; i < difSpeed; i++) {
                        runner.AlternateRun();
                    }
                    matrix.upgradeBeautifulMatrix(runner.giveMassiveOfPoints());
                    drawMatrixWithSquares(matrix.getMatrixBody(), radius, canvas, paint);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void drawMatrixWithCircles(int[][] abcd, int radius, Canvas canvas, Paint paint) {
        if (radius != 0) {
            for (int i = 0; i < abcd.length; i++) {
                for (int j = 0; j < abcd[i].length; j++) {
                    //if (abcd[i][j] != 0) Log.d(LOG_TAG, "" + i + " " + j);
                    if (abcd[i][j] != 0) canvas.drawCircle(i, j, radius, paint);
                }
            }
        } else {
            for (int i = 0; i < abcd.length; i++) {
                for (int j = 0; j < abcd[i].length; j++) {
                    //if (abcd[i][j] != 0) Log.d(LOG_TAG, "" + i + " " + j);
                    if (abcd[i][j] != 0) canvas.drawCircle(i, j, radius, paint);
                }
            }
        }
    }

    private void drawMatrixWithSquares(int[][] abcd, int squareRadius, Canvas canvas, Paint paint) {
        for (int i = 0; i < abcd.length; i++) {
            for (int j = 0; j < abcd[i].length; j++) {
                if (abcd[i][j] != 0) {
                    if (abcd[i][j] < div5)
                        paint.setColor(Color.rgb(159, 168, 218));
                    else if (abcd[i][j] < div4)
                        paint.setColor(Color.rgb(121, 134, 203));
                    else if (abcd[i][j] < div3)
                        paint.setColor(Color.rgb(92, 107, 192));
                    else if (abcd[i][j] < div2)
                        paint.setColor(Color.rgb(63, 81, 181));
                    else
                        paint.setColor(Color.rgb(40, 53, 147));
                    canvas.drawRect(kk * i + x1 + 1, kk * j + y1 + 1, kk * i + kk + x1, kk * j + kk + y1, paint);
                    //Log.d(LOG_TAG, "" + i + " " + j);
                }
            }
        }
    }

    public void requestStop() {
        running = false;
    }
}
