package home.oleg.placesnearme.feature_map.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.github.ybq.android.spinkit.SpinKitView;

import butterknife.BindView;
import butterknife.ButterKnife;
import home.oleg.placesnearme.feature_map.R;
import home.oleg.placesnearme.feature_map.R2;

public class LoadingView extends FrameLayout {

    @BindView(R2.id.spinKit)
    SpinKitView spinKitView;

    @BindView(R2.id.retryButton)
    Button retryButton;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_loading, this, true);
        ButterKnife.bind(this);
    }

    public void showLoading() {
        this.setVisibility(VISIBLE);
        retryButton.setVisibility(GONE);
        spinKitView.setVisibility(VISIBLE);
    }

    public void hideLoading() {
        this.setVisibility(GONE);
        retryButton.setVisibility(GONE);
        spinKitView.setVisibility(GONE);
    }

    public void showError() {
        this.setVisibility(VISIBLE);
        retryButton.setVisibility(VISIBLE);
        spinKitView.setVisibility(GONE);
    }
}
