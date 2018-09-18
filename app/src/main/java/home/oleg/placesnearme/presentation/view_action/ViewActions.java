package home.oleg.placesnearme.presentation.view_action;

import home.oleg.placesnearme.presentation.base.ErrorView;
import home.oleg.placesnearme.presentation.base.LoadingView;

public final class ViewActions {

    private ViewActions() {
    }

    public static <T extends LoadingView> HideLoadingAction<T> hideLoading() {
        return new HideLoadingAction<>();
    }

    public static <T extends LoadingView> ShowLoadingAction<T> showLoading() {
        return new ShowLoadingAction<>();
    }

    public static <T extends ErrorView> ShowErrorAction<T> error(Throwable throwable) {
        return new ShowErrorAction<>(throwable);
    }

}
