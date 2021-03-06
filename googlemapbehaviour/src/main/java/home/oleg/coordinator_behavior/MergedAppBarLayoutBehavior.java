package home.oleg.coordinator_behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewPropertyAnimator;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.mahc.custombottomsheetbehavior.R;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

public class MergedAppBarLayoutBehavior extends AppBarLayout.ScrollingViewBehavior {

    private static final String TAG = MergedAppBarLayoutBehavior.class.getSimpleName();

    private boolean mInit = false;

    // private FrameLayout.LayoutParams mBackGroundLayoutParams;

    private Context mContext;
    private float mInitialY;
    private int mAnchorHeight;

    private boolean mVisible = false;

    private String mToolbarTitle;

    private Toolbar mToolbar;
    private TextView mTitleTextView;
    private View.OnClickListener mOnNavigationClickListener;

    private ValueAnimator mTitleAlphaValueAnimator;
    private int mCurrentTitleAlpha = 0;

    public MergedAppBarLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.GoogleMapsBottomSheetBehavior_Layout);
        mAnchorHeight = a.getDimensionPixelSize(R.styleable.GoogleMapsBottomSheetBehavior_Layout_behavior_anchorHeight, 0);
        a.recycle();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        if (!mInit) {
            init(parent, child);
        }

        boolean childMoved = false;

        if (isDependencyYBelowAnchorPoint(parent, dependency)) {

            childMoved = setToolbarVisible(false, child);

        } else if (isDependencyYBetweenAnchorPointAndToolbar(parent, child, dependency)) {

            childMoved = setToolbarVisible(true, child);
            setFullBackGroundColor(android.R.color.transparent);

        } else if (isDependencyYBelowToolbar(child, dependency) && !isDependencyYReachTop(dependency)) {

            childMoved = setToolbarVisible(true, child);
            if (isTitleVisible())
                setTitleVisible(false);
            setFullBackGroundColor(android.R.color.transparent);

        } else if (isDependencyYBelowStatusToolbar(child, dependency) || isDependencyYReachTop(dependency)) {
            childMoved = setToolbarVisible(true, child);
            if (!isTitleVisible())
                setTitleVisible(true);
            setFullBackGroundColor(android.R.color.white);
        }
        return childMoved;
    }

    private void init(@NonNull CoordinatorLayout parent, @NonNull View child) {

        if (!(child instanceof MergedAppBarLayout)) {
            throw new IllegalArgumentException("The view is not a MergedAppBarLayout");
        }

        MergedAppBarLayout appBarLayout = (MergedAppBarLayout) child;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        }

        mToolbar = appBarLayout.toolbar;

        mTitleTextView = findTitleTextView(mToolbar);
        if (mTitleTextView == null)
            return;

        mInitialY = child.getY();

        child.setVisibility(mVisible ? View.VISIBLE : View.INVISIBLE);

        setFullBackGroundColor(mVisible && mCurrentTitleAlpha == 1 ? android.R.color.white : android.R.color.transparent);
        mTitleTextView.setText(mToolbarTitle);
        mTitleTextView.setAlpha(mCurrentTitleAlpha);
        mInit = true;
        setToolbarVisible(false, child);
    }

    private boolean isDependencyYBelowAnchorPoint(@NonNull CoordinatorLayout parent, @NonNull View dependency) {
        return dependency.getY() > mAnchorHeight;
    }

    private boolean isDependencyYBetweenAnchorPointAndToolbar(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency.getY() <= mAnchorHeight && dependency.getY() > child.getY() + child.getHeight();
    }

    private boolean isDependencyYBelowToolbar(@NonNull View child, @NonNull View dependency) {
        return dependency.getY() <= child.getY() + child.getHeight() && dependency.getY() > child.getY();
    }

    private boolean isDependencyYBelowStatusToolbar(@NonNull View child, @NonNull View dependency) {
        return dependency.getY() <= child.getY();
    }

    private boolean isDependencyYReachTop(@NonNull View dependency) {
        return dependency.getY() == 0;
    }

    private void setFullBackGroundColor(@ColorRes int colorRes) {
        mToolbar.setBackgroundColor(ContextCompat.getColor(mContext, colorRes));
    }

    private TextView findTitleTextView(Toolbar toolbar) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View toolBarChild = toolbar.getChildAt(i);
            if (toolBarChild instanceof TextView &&
                    ((TextView) toolBarChild).getText() != null &&
                    ((TextView) toolBarChild).getText().toString().contentEquals(mContext.getResources().getString(R.string.key_binding_default_toolbar_name))) {
                return (TextView) toolBarChild;
            }
        }
        return null;
    }

    private boolean setToolbarVisible(boolean visible, final View child) {
        ViewPropertyAnimator mAppBarLayoutAnimation;
        boolean childMoved = false;
        if (visible && !mVisible) {
            childMoved = true;
            mAppBarLayoutAnimation = child.animate().setDuration(mContext.getResources().getInteger(android.R.integer.config_shortAnimTime));
            mAppBarLayoutAnimation.setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    child.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((AppCompatActivity) mContext).setSupportActionBar(mToolbar);
                    mToolbar.setNavigationOnClickListener(mOnNavigationClickListener);
                    ActionBar actionBar = ((AppCompatActivity) mContext).getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setDisplayHomeAsUpEnabled(true);
                    }
                    mVisible = true;
                }
            });
            mAppBarLayoutAnimation.alpha(1).start();
        } else if (!visible && mVisible) {
            mAppBarLayoutAnimation = child.animate().setDuration(mContext.getResources().getInteger(android.R.integer.config_shortAnimTime));
            mAppBarLayoutAnimation.setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    child.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((AppCompatActivity) mContext).setSupportActionBar(null);
                    mVisible = false;
                }
            });
            mAppBarLayoutAnimation.alpha(0).start();
        }

        return childMoved;
    }

    private boolean isTitleVisible() {
        return mTitleTextView.getAlpha() == 1;
    }

    private void setTitleVisible(boolean visible) {

        if ((visible && mTitleTextView.getAlpha() == 1) ||
                (!visible && mTitleTextView.getAlpha() == 0))
            return;

        if (mTitleAlphaValueAnimator == null || !mTitleAlphaValueAnimator.isRunning()) {
            mToolbar.setTitle(mToolbarTitle);
            int startAlpha = visible ? 0 : 1;
            int endAlpha = mCurrentTitleAlpha = visible ? 1 : 0;

            mTitleAlphaValueAnimator = ValueAnimator.ofFloat(startAlpha, endAlpha);
            mTitleAlphaValueAnimator.setDuration(mContext.getResources().getInteger(android.R.integer.config_shortAnimTime));
            mTitleAlphaValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mTitleTextView.setAlpha((Float) animation.getAnimatedValue());
                }
            });
            mTitleAlphaValueAnimator.start();
        }
    }

    public void setNavigationOnClickListener(View.OnClickListener listener) {
        this.mOnNavigationClickListener = listener;
    }

    public void setToolbarTitle(String title) {
        this.mToolbarTitle = title;
        if (this.mToolbar != null)
            this.mToolbar.setTitle(title);
    }

    @Override
    public Parcelable onSaveInstanceState(CoordinatorLayout parent, View child) {
        return new SavedState(super.onSaveInstanceState(parent, child),
                mVisible,
                mToolbarTitle,
                mCurrentTitleAlpha);
    }

    @Override
    public void onRestoreInstanceState(CoordinatorLayout parent, View child, Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(parent, child, ss.getSuperState());
        this.mVisible = ss.mVisible;
        this.mToolbarTitle = ss.mToolbarTitle;
        this.mCurrentTitleAlpha = ss.mTitleAlpha;
    }

    protected static class SavedState extends View.BaseSavedState {

        final boolean mVisible;
        final String mToolbarTitle;
        final int mTitleAlpha;

        public SavedState(Parcel source) {
            super(source);
            mVisible = source.readByte() != 0;
            mToolbarTitle = source.readString();
            mTitleAlpha = source.readInt();
        }

        public SavedState(Parcelable superState, boolean visible, String toolBarTitle, int titleAlpha) {
            super(superState);
            this.mVisible = visible;
            this.mToolbarTitle = toolBarTitle;
            this.mTitleAlpha = titleAlpha;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeByte((byte) (mVisible ? 1 : 0));
            out.writeString(mToolbarTitle);
            out.writeInt(mTitleAlpha);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel source) {
                        return new SavedState(source);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }

    public static <V extends View> MergedAppBarLayoutBehavior from(V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params)
                .getBehavior();
        if (!(behavior instanceof MergedAppBarLayoutBehavior)) {
            throw new IllegalArgumentException("The view is not associated with " +
                    "MergedAppBarLayoutBehavior");
        }
        return (MergedAppBarLayoutBehavior) behavior;
    }
}
