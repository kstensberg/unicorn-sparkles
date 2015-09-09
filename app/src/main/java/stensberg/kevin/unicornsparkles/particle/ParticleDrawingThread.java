package stensberg.kevin.unicornsparkles.particle;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

// Originally found at https://github.com/manoj-chauhan/Sparkles/
// Written by Manoj Chauhan <manojchauhan100@gmail.com>
// Modified by Kevin Stensberg <kstensberg@gmail.com>
public class ParticleDrawingThread extends Thread {

    private boolean running = true;

    private SurfaceHolder surfaceHolder;

    private ArrayList<Particle> particleList = new ArrayList<>();

    private int canvasWidth;
    private int canvasHeight;
    private Paint paint;

    public ParticleDrawingThread(SurfaceHolder surfaceHolder, Context context) {
        this.surfaceHolder = surfaceHolder;
        this.paint = new Paint();
        paint.setColor(Color.WHITE);

    }

    public void emitParticle(Particle particle) {
        particleList.add(particle);
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    doDraw(canvas);
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void doDraw(Canvas canvas) {
        if (canvas == null) {
            return;
        }

        canvas.drawRect(0, 0, canvasWidth, canvasHeight, paint);
        synchronized (particleList) {
            for (int i = 0; i < particleList.size(); i++) {
                Particle particle = particleList.get(i);
                particle.move();

                canvas.drawBitmap(particle.bitmap, particle.x - 10, particle.y - 10, paint);

                if (particle.x < 0 || particle.x > canvasWidth || particle.y < 0 || particle.y > canvasHeight) {
                    particleList.remove(i);
                    i--;
                }
            }
        }
    }

    public void stopDrawing() {
        this.running = false;
    }

    public void setSurfaceSize(int width, int height) {
        canvasWidth = width;
        canvasHeight = height;
    }
}