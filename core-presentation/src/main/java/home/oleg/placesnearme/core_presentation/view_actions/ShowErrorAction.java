package home.oleg.placesnearme.core_presentation.view_actions;

import com.smedialink.common.function.Action;

import home.oleg.placesnearme.core_presentation.base.ErrorView;
import io.reactivex.annotations.NonNull;

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
        errorView.showError();
    }

}
