package net.mz.rb.subdiffusion;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Draw_dif extends SurfaceView implements SurfaceHolder.Callback {
    private Draw_dif_help draw_dif_help;

    public Draw_dif(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        draw_dif_help = new Draw_dif_help(getContext(), getHolder());
        draw_dif_help.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // изменение размеров SurfaceView
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        draw_dif_help.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                draw_dif_help.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
