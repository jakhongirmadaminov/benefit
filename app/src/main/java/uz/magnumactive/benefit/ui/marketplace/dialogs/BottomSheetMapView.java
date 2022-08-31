package uz.magnumactive.benefit.ui.marketplace.dialogs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.gms.maps.MapView;

public class BottomSheetMapView extends MapView {

    public BottomSheetMapView(Context context) {
        super(context);
    }

    public BottomSheetMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomSheetMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            default:
                break;
        }

        return super.onInterceptTouchEvent(event);
    }
}