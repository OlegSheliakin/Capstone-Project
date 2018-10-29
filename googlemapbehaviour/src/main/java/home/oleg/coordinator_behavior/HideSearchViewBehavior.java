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
import java.util.ArrayList;
import java.util.List;

public class HideSearchViewBehavior extends CoordinatorLayout.Behavior<View> {

    private WeakReference<GoogleMapsBottomSheetBehavior> bottomSheetBehaviorWeakReference;
    private boolean isInitialized;

    private List<FloatingActionButton> fabs = new ArrayList<>();
    private int anchorHeight = -1;
    private boolean enabled;
    private static int RANGE = 50;

    public HideSearchViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if (!isInitialized) {
            init(dependency, child);
        } else {
            float dependencyY = dependency.getY();

            int tillY = parent.getHeight() - anchorHeight;
            float alpha = 1 - (tillY - dependency.getY()) / RANGE;
            if (alpha < 0) {
                alpha = 0;
            } else if (alpha > 1) {
                alpha = 1;
            }

            child.setAlpha(alpha);

            return true;
        }

        return super.onDependentViewChanged(parent, child, dependency);
    }

    private void init(View dependency, View child) {
        anchorHeight = getWeekReference(dependency).get().getHeaderHeight();
        isInitialized = true;
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