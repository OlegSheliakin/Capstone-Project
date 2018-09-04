package home.oleg.placesnearme.presentation.base;

import com.smedialink.common.function.Action;

import io.reactivex.annotations.NonNull;
import timber.log.Timber;

/**
 * Created by Oleg Sheliakin on 21.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class ShowErrorAction<T extends ErrorView> implements Action<T> {

    private final Throwable error;

    public ShowErrorAction(@NonNull Throwable error) {
        this.error = error;
    }

    @Override
    public void perform(@NonNull ErrorView errorView) {
        Timber.e(error);
        errorView.showError();
    }

}
