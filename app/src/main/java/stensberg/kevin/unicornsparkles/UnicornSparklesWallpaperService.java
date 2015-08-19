package stensberg.kevin.unicornsparkles;

import android.service.wallpaper.WallpaperService;

public class UnicornSparklesWallpaperService extends WallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new UnicornSparklesWallpaperEngine();
    }

    private class UnicornSparklesWallpaperEngine extends Engine {
    }
}
