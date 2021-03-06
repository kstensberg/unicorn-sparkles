package stensberg.kevin.unicornsparkles;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import stensberg.kevin.unicornsparkles.particle.ParticleView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout topLayout = (LinearLayout) findViewById(R.id. top_layout);

        List<Bitmap> bitmaps = UiUtils.getAllBitmaps(this);

        ParticleView view = new ParticleView(this, bitmaps);
        topLayout.addView(view);
    }

    public void setWallpaper(View v) {
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(this, UnicornSparklesWallpaperService.class));
        startActivity(intent);
    }
}
