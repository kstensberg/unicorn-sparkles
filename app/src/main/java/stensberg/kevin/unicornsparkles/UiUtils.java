package stensberg.kevin.unicornsparkles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

public class UiUtils {
    public static Drawable getDrawableFromResourceId(Context context, int resourceId) {
        return ContextCompat.getDrawable(context, resourceId);
    }

    public static Bitmap getBitmapFromResourceId(Context context, int resourceId) {
        return BitmapFactory.decodeResource(context.getResources(), resourceId);
    }
}
