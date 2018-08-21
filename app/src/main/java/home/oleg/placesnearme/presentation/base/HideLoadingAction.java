package home.oleg.placesnearme.presentation.base;

/**
 * Created by Oleg Sheliakin on 21.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class HideLoadingAction<T extends LoadingView> implements ViewAction<T> {

    @Override
    public void accept(LoadingView loadingView) {
        loadingView.hideLoading();
    }

}


