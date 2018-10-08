package home.oleg.coordinator_behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mahc.custombottomsheetbehavior.R;

import java.lang.ref.WeakReference;

public class HideViewTwoPhaseBottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    private static final float RANGE = 50f;

    private float mCurrentChildY;

    private int peekHeight;

    public HideViewTwoPhaseBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HideViewTwoPhaseBottomSheetBehavior);
        peekHeight = a.getDimensionPixelSize(R.styleable.HideViewTwoPhaseBottomSheetBehavior_peekHeight, 0);
        a.recycle();
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        ViewGroup.LayoutParams params = dependency.getLayoutParams();
        if (params instanceof CoordinatorLayout.LayoutParams) {
            return ((CoordinatorLayout.LayoutParams) params).getBehavior() instanceof GoogleMapsBottomSheetBehavior;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        float y = dependency.getY();

        int anchorPointY = child.getHeight();

        float lastCurrentChildY = mCurrentChildY;
        mCurrentChildY = dependency.getY() - anchorPointY;

        int tillY = parent.getHeight() - peekHeight;
        float alpha = 1 - (tillY - dependency.getY()) / RANGE;

        if (alpha > 1) {
            alpha = 1;
        } else if (alpha < 0) {
            alpha = 0;
        }

        changeAlphaIfNecessary(child, alpha);

        child.setY(y - anchorPointY);

        return lastCurrentChildY != mCurrentChildY;
    }

    private void changeAlphaIfNecessary(View view, float alpha) {
        if (view.getAlpha() == alpha) {
            return;
        }

        view.setAlpha(alpha);
    }

}