package home.oleg.coordinator_behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.mahc.custombottomsheetbehavior.R;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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

        toolbar = findViewById(R.id.expanded_toolbar);
    }
}