package home.oleg.coordinator_behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

public class BottomSheetAwareFabBehavior extends CoordinatorLayout.Behavior<View> {

    private WeakReference<GoogleMapsBottomSheetBehavior> bottomSheetBehaviorWeakReference;
    private boolean isInitialized;

    private List<FloatingActionButton> fabs = new ArrayList<>();
    private int anchorHeight = -1;
    private boolean enabled;
    private static int RANGE = 50;

    public BottomSheetAwareFabBehavior(Context context, AttributeSet attrs) {
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
         if(dependencyY < anchorHeight) {
             updateEnable(true);
         } else {
             updateEnable(false);
         }

            return true;
        }

        return super.onDependentViewChanged(parent, child, dependency);
    }

    private void init(View dependency, View child) {
        if (child instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) child;
            int childCount = ((ViewGroup) child).getChildCount();
            if (childCount < 1) {
                return;
            }

            for (int i = 0; i < childCount; i++) {
                View view = viewGroup.getChildAt(i);
                if (view instanceof FloatingActionButton) {
                    ((FloatingActionButton) view).hide();
                    fabs.add((FloatingActionButton) view);
                }
            }

            anchorHeight = getWeekReference(dependency).get().getHeaderHeight();
            isInitialized = true;
        } else {
            throw new IllegalStateException("this behavior can be applied only to viewgroup");
        }
    }

    private void updateEnable(boolean isEnabled) {
        if (isEnabled == enabled) {
            return;
        }

        for (FloatingActionButton fab : fabs) {
            if(isEnabled){
                fab.show();
            } else {
                fab.hide();
            }
        }

        enabled = isEnabled;
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