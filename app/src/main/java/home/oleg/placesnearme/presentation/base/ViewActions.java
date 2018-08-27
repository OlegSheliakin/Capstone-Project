package home.oleg.placesnearme.presentation.base;

public final class ViewActions {

    public static <T extends LoadingView> HideLoadingAction<T> hideLoading() {
        return new HideLoadingAction<T>();
    }

    public static <T extends LoadingView> ShowLoadingAction<T> showLoading() {
        return new ShowLoadingAction<T>();
    }

    public static <T extends ErrorView> ShowErrorAction<T> error(Throwable throwable) {
        return new ShowErrorAction<T>(throwable);
    }

}
