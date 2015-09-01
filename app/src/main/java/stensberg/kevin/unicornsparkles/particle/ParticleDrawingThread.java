package stensberg.kevin.unicornsparkles.particle;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import stensberg.kevin.unicornsparkles.R;
import stensberg.kevin.unicornsparkles.UiUtils;

// Originally found at https://github.com/manoj-chauhan/Sparkles/
// Written by Manoj Chauhan <manojchauhan100@gmail.com>
// Modified by Kevin Stensberg <kstensberg@gmail.com>
class ParticleDrawingThread extends Thread {

    private boolean run = true;

    private SurfaceHolder surfaceHolder;

    private ArrayList<Particle> particleList = new ArrayList<Particle>();
    private ArrayList<Particle> recycleList = new ArrayList<Particle>();

    private int canvasWidth;
    private int canvasHeight;
    private Paint paint;

    private Bitmap images[] = new Bitmap[3];

    public ParticleDrawingThread(SurfaceHolder surfaceHolder, Context context) {
        this.surfaceHolder = surfaceHolder;
        this.paint = new Paint();
        paint.setColor(Color.WHITE);

        images[0] = UiUtils.getBitmapFromResourceId(context, R.drawable.yellow_spark);
        images[1] = UiUtils.getBitmapFromResourceId(context, R.drawable.blue_spark);
        images[2] = UiUtils.getBitmapFromResourceId(context, R.drawable.red_spark);

    }

    @Override
    public void run() {
        while (run) {
            Canvas c = null;
            try {
                c = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    doDraw(c);
                }
            } finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }

    private void doDraw(Canvas c) {
        c.drawRect(0, 0, canvasWidth, canvasHeight, paint);
        synchronized (particleList) {
            for (int i = 0; i < particleList.size(); i++) {
                Particle p = particleList.get(i);
                p.move();

                c.drawBitmap(images[p.color], p.x-10, p.y-10, paint);
                if (p.x < 0 || p.x > canvasWidth || p.y < 0 || p.y > canvasHeight) {
                    recycleList.add(particleList.remove(i));
                    i--;
                }
            }
        }
    }

    public void stopDrawing() {
        this.run = false;
    }

    public ArrayList<Particle> getParticleList() {
        return particleList;
    }

    public ArrayList<Particle> getRecycleList() {
        return recycleList;
    }

    public void setSurfaceSize(int width, int height) {
        canvasWidth = width;
        canvasHeight = height;
    }
}