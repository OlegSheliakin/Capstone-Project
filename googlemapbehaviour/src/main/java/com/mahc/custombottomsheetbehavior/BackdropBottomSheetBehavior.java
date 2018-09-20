package com.mahc.custombottomsheetbehavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;

public class BackdropBottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    private WeakReference<CustomBottomSheetBehavior> mBottomSheetBehaviorRef;

    private float mCurrentChildY;

    public BackdropBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (dependency instanceof NestedScrollView) {
            CustomBottomSheetBehavior.from(dependency);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        if (mBottomSheetBehaviorRef == null || mBottomSheetBehaviorRef.get() == null) {
            getBottomSheetBehavior(parent);
        }

        int collapsedY = dependency.getHeight() - mBottomSheetBehaviorRef.get().getPeekHeight();

        int achorPointY = mBottomSheetBehaviorRef.get().getAnchorPoint();

        float lastCurrentChildY = mCurrentChildY;
        int offset = child.getHeight() - achorPointY;

        mCurrentChildY = (dependency.getY() - achorPointY) * collapsedY / (collapsedY - offset - achorPointY);

        if(mCurrentChildY < 0) {
            mCurrentChildY = 0;
        }

        child.setY(mCurrentChildY);
        return lastCurrentChildY == mCurrentChildY;
    }

    /**
     * Look into the CoordiantorLayout for the {@link BottomSheetBehaviorGoogleMapsLike}
     *
     * @param coordinatorLayout with app:layout_behavior= {@link BottomSheetBehaviorGoogleMapsLike}
     */
    private void getBottomSheetBehavior(@NonNull CoordinatorLayout coordinatorLayout) {

        for (int i = 0; i < coordinatorLayout.getChildCount(); i++) {
            View child = coordinatorLayout.getChildAt(i);

            if (child instanceof NestedScrollView) {
                CustomBottomSheetBehavior temp = CustomBottomSheetBehavior.from(child);
                mBottomSheetBehaviorRef = new WeakReference<>(temp);
                break;
            }
        }
    }
}