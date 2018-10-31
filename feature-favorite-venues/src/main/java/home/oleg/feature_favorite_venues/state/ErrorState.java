package home.oleg.feature_favorite_venues.state;

public class ErrorState implements State<ErrorView> {

    private final Throwable throwable;

    public ErrorState(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public void apply(ErrorView errorView) {
        errorView.showError();
    }
}
