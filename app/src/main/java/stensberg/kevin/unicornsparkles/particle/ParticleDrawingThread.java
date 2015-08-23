package stensberg.kevin.unicornsparkles.particle;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.SurfaceHolder;

import stensberg.kevin.unicornsparkles.R;

// Originally found at https://github.com/manoj-chauhan/Sparkles/
// Written by Manoj Chauhan <manojchauhan100@gmail.com>
// Modified by Kevin Stensberg <kstensberg@gmail.com>
class ParticleDrawingThread extends Thread {

    private boolean mRun = true;

    private SurfaceHolder mSurfaceHolder;

    private ArrayList<Particle> mParticleList =new ArrayList<Particle>();
    private ArrayList<Particle> mRecycleList =new ArrayList<Particle>();

    private int mCanvasWidth;
    private int mCanvasHeight;
    private Paint mPaint;
    private Bitmap mImage[] =new Bitmap[3];

    public ParticleDrawingThread(SurfaceHolder mSurfaceHolder, Context mContext) {
        this.mSurfaceHolder = mSurfaceHolder;
        this.mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mImage[0] =((BitmapDrawable)mContext.getResources().getDrawable(R.drawable.yellow_spark)).getBitmap();
        mImage[1] =((BitmapDrawable)mContext.getResources().getDrawable(R.drawable.blue_spark)).getBitmap();
        mImage[2] =((BitmapDrawable)mContext.getResources().getDrawable(R.drawable.red_spark)).getBitmap();

    }

    @Override
    public void run() {
        while (mRun) {
            Canvas c = null;
            try {
                c = mSurfaceHolder.lockCanvas(null);
                synchronized (mSurfaceHolder) {
                    doDraw(c);
                }
            } finally {
                if (c != null) {
                    mSurfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }

    private void doDraw(Canvas c) {
        c.drawRect(0, 0, mCanvasWidth, mCanvasHeight, mPaint);
        synchronized (mParticleList) {
            for (int i = 0; i < mParticleList.size(); i++) {
                Particle p = mParticleList.get(i);
                p.move();
                c.drawBitmap(mImage[p.color], p.x-10, p.y-10, mPaint);
                if (p.x < 0 || p.x > mCanvasWidth || p.y < 0 || p.y > mCanvasHeight) {
                    mRecycleList.add(mParticleList.remove(i));
                    i--;
                }
            }
        }
    }

    public void stopDrawing() {
        this.mRun = false;
    }

    public ArrayList<Particle> getParticleList() {
        return mParticleList;
    }

    public ArrayList<Particle> getRecycleList() {
        return mRecycleList;
    }

    public void setSurfaceSize(int width, int height) {
        mCanvasWidth = width;
        mCanvasHeight = height;
    }
}