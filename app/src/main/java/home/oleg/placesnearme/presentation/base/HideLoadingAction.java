package home.oleg.placesnearme.presentation.base;

import android.support.annotation.NonNull;

import com.smedialink.common.function.Action;

/**
 * Created by Oleg Sheliakin on 21.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class HideLoadingAction<T extends LoadingView> implements Action<T> {

    @Override
    public void perform(@NonNull LoadingView loadingView) {
        loadingView.hideLoading();
    }

}


