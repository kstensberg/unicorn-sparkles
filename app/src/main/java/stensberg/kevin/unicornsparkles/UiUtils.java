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

        bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.blue_spark));
        bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.red_spark));
        bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.yellow_spark));
        bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.unicorn1));
        bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.unicorn2));
        //bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.mlp1));
        //bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.mlp2));
        //bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.mlp3));
        //bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.mlp4));
        //bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.mlp5));
        //bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.mlp6));
        bitmaps.add(UiUtils.getBitmapFromResourceId(context, R.drawable.rainbow1));

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
