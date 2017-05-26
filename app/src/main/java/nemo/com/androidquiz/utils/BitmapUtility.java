package nemo.com.androidquiz.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class BitmapUtility {

    private static final String TAG = "BitmapUtility";

    public static Bitmap createDrawableFromView(Context context, View view) {

        Bitmap bit = null;
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
            view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
            view.buildDrawingCache();
            bit = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(bit);
            view.draw(canvas);
        } catch (NullPointerException e) {
            Log.e("NullPointerException", e.toString());
            e.printStackTrace();
        }
        return bit;
    }
}
