package stensberg.kevin.unicornsparkles.particle;

import java.util.ArrayList;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// Originally found at https://github.com/manoj-chauhan/Sparkles/
// Written by Manoj Chauhan <manojchauhan100@gmail.com>
// Modified by Kevin Stensberg <kstensberg@gmail.com>
public class ParticleView extends SurfaceView implements SurfaceHolder.Callback {

    private ParticleDrawingThread drawingThread;

    private ArrayList<Particle> particleList;
    private ArrayList<Particle> recycleList;

    private Context context;

    public ParticleView(Context context) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        this.context = context;

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        drawingThread.setSurfaceSize(width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawingThread = new ParticleDrawingThread(holder, context);
        particleList = drawingThread.getParticleList();
        recycleList = drawingThread.getRecycleList();
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
        Particle particle;
        int recycleCount = 0;

        if(recycleList.size() > 1)
            recycleCount = 2;
        else
            recycleCount = recycleList.size();

        for (int i = 0; i < recycleCount; i++) {
            particle = recycleList.remove(0);
            particle.init((int) event.getX(), (int) event.getY());
            particleList.add(particle);
        }

        for (int i = 0; i < 2 - recycleCount; i++)
            particleList.add(new Particle((int)event.getX(), (int)event.getY()));

        super.onTouchEvent(event);

        return true;
    }
}