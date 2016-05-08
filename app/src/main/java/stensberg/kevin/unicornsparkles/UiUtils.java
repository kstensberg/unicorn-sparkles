package stensberg.kevin.unicornsparkles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UiUtils {
    public static Drawable getDrawableFromResourceId(Context context, int resourceId) {
        return ContextCompat.getDrawable(context, resourceId);
    }

    public static Bitmap getBitmapFromResourceId(Context context, int resourceId) {
        return BitmapFactory.decodeResource(context.getResources(), resourceId);
    }

    public static List<Bitmap> getAllBitmaps(Context context) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.bug));
        bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.droplet));
        bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.heart));
        bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.moon));

        return bitmaps;
    }

    public static Bitmap getRandomBitmap(Context context) {
        List<Bitmap> allBitmaps = getAllBitmaps(context);
        Random rand = new Random();
        int bitmapIdx = rand.nextInt(allBitmaps.size());
        return allBitmaps.get(bitmapIdx);
    }

    public static int getColorFromResourceId(Context context, int resourceId) {
        return ContextCompat.getColor(context, resourceId);
    }
}
