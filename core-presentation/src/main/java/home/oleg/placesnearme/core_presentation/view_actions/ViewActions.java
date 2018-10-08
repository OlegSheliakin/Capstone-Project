package home.oleg.placesnearme.core_presentation.view_actions;

import home.oleg.placesnearme.core_presentation.base.DataView;
import home.oleg.placesnearme.core_presentation.base.ErrorView;
import home.oleg.placesnearme.core_presentation.base.LoadingView;

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

    public static <R, V extends DataView<R>> ShowDataAction<R, V> create(R data) {
        return ShowDataAction.create(data);
    }

}
