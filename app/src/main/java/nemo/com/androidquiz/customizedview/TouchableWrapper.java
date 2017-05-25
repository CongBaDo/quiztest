package nemo.com.androidquiz.customizedview;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by doba on 5/25/17.
 */
public class TouchableWrapper extends FrameLayout {

    private static final String TAG = "TouchableWrapper";

    int numberOfTaps = 0;
    long lastTapTimeMs = 0;
    long touchDownMs = 0;
    Handler handler = new Handler();


    private GestureDetector gestureDetector;
    public TouchableWrapper(Context context) {
        super(context);
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public TouchableWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public TouchableWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public interface TouchListener{
        public void onUpListener();
        public void onDownListener();
    }

    private TouchListener listener;

    public void setTouchListener(TouchListener listener){
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                mMapIsTouched = true;
                touchDownMs = System.currentTimeMillis();
                this.listener.onDownListener();
                break;
            case MotionEvent.ACTION_MOVE:
                numberOfTaps = 0;
                break;

            case MotionEvent.ACTION_UP:
                handler.removeCallbacksAndMessages(null);
                this.listener.onUpListener();

                break;

        }

        return super.dispatchTouchEvent(ev);

    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }

        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();

            Log.d("Double Tap", "Tapped at: (" + x + "," + y + ")");


            return true;
        }
    }
}
