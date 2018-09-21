package com.mahc.custombottomsheetbehavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;

public class HideViewTwoPhaseBottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    public static final float RANGE = 50f;
    private WeakReference<CustomBottomSheetBehavior> mBottomSheetBehaviorRef;

    float mCurrentChildY;

    public HideViewTwoPhaseBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        if (mBottomSheetBehaviorRef == null || mBottomSheetBehaviorRef.get() == null) {
            getBottomSheetBehavior(parent);
        }

        int peekHeight = mBottomSheetBehaviorRef.get().getPeekHeight();

        float y = dependency.getY();

        int achorPointY = child.getHeight();

        float lastCurrentChildY = mCurrentChildY;
        mCurrentChildY = dependency.getY() - achorPointY;

        int tillY = parent.getHeight() - peekHeight;
        float alpha = 1 - (tillY - dependency.getY()) / RANGE;

        if (alpha > 1) {
            alpha = 1;
        } else if (alpha < 0) {
            alpha = 0;
        }

        changeAlphaIfNecessary(child, alpha);

        child.setY(y - achorPointY);

        return lastCurrentChildY != mCurrentChildY;
    }

    private void changeAlphaIfNecessary(View view, float alpha) {
        if (view.getAlpha() == alpha) {
            return;
        }

        view.setAlpha(alpha);
    }

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