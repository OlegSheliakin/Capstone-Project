package home.oleg.coordinator_behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mahc.custombottomsheetbehavior.R;

public class MergedAppBarLayout extends AppBarLayout {

    protected Toolbar toolbar;
    protected View background;

    public MergedAppBarLayout(Context context) {
        super(context);
        init();
    }

    public MergedAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.mergedappbarlayout, this);

        setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
        getContext().setTheme(R.style.AppTheme_AppBarOverlay);

        background = findViewById(R.id.background);
        toolbar = findViewById(R.id.expanded_toolbar);
    }
}