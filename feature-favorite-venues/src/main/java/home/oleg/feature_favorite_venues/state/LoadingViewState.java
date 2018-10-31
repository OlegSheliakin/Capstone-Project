package home.oleg.feature_favorite_venues.state;

public class LoadingViewState implements State<LoadingView> {
    private final boolean isLoading;

    public LoadingViewState(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public void apply(LoadingView loadingView) {
        if (isLoading) {
            loadingView.showLoading();
        } else {
            loadingView.hideLoading();
        }
    }
}
