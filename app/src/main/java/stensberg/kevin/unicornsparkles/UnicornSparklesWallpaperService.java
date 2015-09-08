package stensberg.kevin.unicornsparkles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import stensberg.kevin.unicornsparkles.particle.Particle;
import stensberg.kevin.unicornsparkles.particle.ParticleDrawingThread;

public class UnicornSparklesWallpaperService extends WallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new UnicornSparklesWallpaperEngine();
    }

    private class UnicornSparklesWallpaperEngine extends Engine implements SurfaceHolder.Callback {

        private boolean visible;
        private ParticleDrawingThread drawingThread;

        public UnicornSparklesWallpaperEngine() {
            setTouchEventsEnabled(true);

            SurfaceHolder holder = getSurfaceHolder();
            holder.addCallback(this);
            Context context = getApplicationContext();

            drawingThread = new ParticleDrawingThread(holder, context);
            drawingThread.start();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
        }


        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            drawingThread.setSurfaceSize(width, height);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            drawingThread = new ParticleDrawingThread(holder, getApplicationContext());
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

        public void onTouchEvent (MotionEvent event) {
            Log.d("WallpaperService", "touch event");

            Bitmap bitmap = UiUtils.getRandomBitmap(getApplicationContext());
            drawingThread.emitParticle(new Particle((int) event.getX(), (int) event.getY(), bitmap));

            super.onTouchEvent(event);
        }

    }

}
