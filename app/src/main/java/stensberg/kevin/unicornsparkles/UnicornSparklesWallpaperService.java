package stensberg.kevin.unicornsparkles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.text.DateFormat;
import java.util.Date;

public class UnicornSparklesWallpaperService extends WallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new UnicornSparklesWallpaperEngine();
    }

    private class UnicornSparklesWallpaperEngine extends Engine {
        private final Handler handler = new Handler();
        private int bgColor;
        private boolean visible = true;
        private final Runnable drawRunner = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

        public UnicornSparklesWallpaperEngine() {
            setTouchEventsEnabled(true);
            bgColor = Color.parseColor("#C0C0C0");
            handler.post(drawRunner);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                handler.post(drawRunner);
            } else {
                handler.removeCallbacks(drawRunner);
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible = false;
            handler.removeCallbacks(drawRunner);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        public void onTouchEvent (MotionEvent event) {

        }

        private void draw() {
            SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    draw(canvas);
                }
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }

            handler.removeCallbacks(drawRunner);

            if (visible) {
                handler.postDelayed(drawRunner, 200);
            }
        }

        private void draw(Canvas canvas) {
            Log.w("LiveWallpaper", "drawing");
            canvas.drawColor(bgColor);

            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setTextSize(74);

            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

            canvas.drawText(currentDateTimeString, 40, 100, paint);
        }

    }

}
