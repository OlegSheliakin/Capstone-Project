package home.oleg.coordinator_behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

public class BottomSheetAwareFabBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

    private WeakReference<GoogleMapsBottomSheetBehavior> bottomSheetBehaviorWeakReference;

    private int anchorHeight = -1;
    private static int RANGE = 50;

    public BottomSheetAwareFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull FloatingActionButton child, @NonNull View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull FloatingActionButton child, @NonNull View dependency) {
        if (anchorHeight == -1) {
            anchorHeight = getWeekReference(dependency).get().getAnchorOffset();
            child.setScaleX(0);
            child.setScaleY(0);
            child.setEnabled(false);
        } else {
            float dependencyY = dependency.getY();

            float scale = 1f - (dependencyY - anchorHeight) / RANGE;

            if (scale > 1) {
                scale = 1;
            } else if (scale < 0) {
                scale = 0;
            }


            child.setEnabled(scale > 0);

            child.setScaleY(scale);
            child.setScaleX(scale);

            return true;
        }

        return super.onDependentViewChanged(parent, child, dependency);
    }

    private WeakReference<GoogleMapsBottomSheetBehavior> getWeekReference(View view) {
        if (bottomSheetBehaviorWeakReference != null) {
            return bottomSheetBehaviorWeakReference;
        } else {
            GoogleMapsBottomSheetBehavior behavior = getBottomSheetBehavior(view);
            return new WeakReference<>(behavior);
        }
    }

    private GoogleMapsBottomSheetBehavior<NestedScrollView> getBottomSheetBehavior(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params)
                .getBehavior();
        if (!(behavior instanceof GoogleMapsBottomSheetBehavior)) {
            throw new IllegalArgumentException(
                    "The view is not associated with GoogleMapsBottomSheetBehavior");
        }
        return (GoogleMapsBottomSheetBehavior) behavior;
    }
}