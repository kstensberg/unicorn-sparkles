package stensberg.kevin.unicornsparkles.particle;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// Originally found at https://github.com/manoj-chauhan/Sparkles/
// Written by Manoj Chauhan <manojchauhan100@gmail.com>
// Modified by Kevin Stensberg <kstensberg@gmail.com>
public class ParticleView extends SurfaceView implements SurfaceHolder.Callback {

    private ParticleDrawingThread mDrawingThread;

    private ArrayList<Particle> mParticleList;
    private ArrayList<Particle> mRecycleList;

    private Context mContext;

    public ParticleView(Context context) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        this.mContext = context;

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mDrawingThread.setSurfaceSize(width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mDrawingThread = new ParticleDrawingThread(holder, mContext);
        mParticleList = mDrawingThread.getParticleList();
        mRecycleList = mDrawingThread.getRecycleList();
        mDrawingThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mDrawingThread.stopDrawing();
        while (retry) {
            try {
                mDrawingThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Particle p;
        int recycleCount = 0;

        if(mRecycleList.size() > 1)
            recycleCount = 2;
        else
            recycleCount = mRecycleList.size();

        for (int i = 0; i < recycleCount; i++) {
            p = mRecycleList.remove(0);
            p.init((int)event.getX(), (int)event.getY());
            mParticleList.add(p);
        }

        for (int i = 0; i < 2-recycleCount; i++)
            mParticleList.add(new Particle((int)event.getX(), (int)event.getY()));

        super.onTouchEvent(event);

        return true;
    }
}