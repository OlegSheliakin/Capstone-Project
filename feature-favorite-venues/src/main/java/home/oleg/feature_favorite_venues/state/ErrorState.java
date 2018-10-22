package home.oleg.feature_favorite_venues.state;

import home.oleg.placesnearme.core_presentation.base.ErrorView;

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
