package stensberg.kevin.unicornsparkles.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// Originally found at https://github.com/manoj-chauhan/Sparkles/
// Written by Manoj Chauhan <manojchauhan100@gmail.com>
// Modified by Kevin Stensberg <kstensberg@gmail.com>
public class ParticleView extends SurfaceView implements SurfaceHolder.Callback {

    private ParticleDrawingThread drawingThread;

    private ArrayList<Particle> particleList;

    private List<Bitmap> bitmaps;

    private Context context;

    public ParticleView(Context context, List<Bitmap> bitmaps) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        this.context = context;
        this.bitmaps = bitmaps;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        drawingThread.setSurfaceSize(width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawingThread = new ParticleDrawingThread(holder, context);
        particleList = drawingThread.getParticleList();
        drawingThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawingThread.stopDrawing();
        while (retry) {
            try {
                drawingThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Random rand = new Random();
        int bitmapIdx = rand.nextInt(bitmaps.size());
        Bitmap bitmap = bitmaps.get(bitmapIdx);

        particleList.add(new Particle((int)event.getX(), (int)event.getY(), bitmap));

        super.onTouchEvent(event);

        return true;
    }
}