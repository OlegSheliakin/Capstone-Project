package home.oleg.placesnearme.presentation.feature.base;

import java.util.Objects;

import io.reactivex.annotations.NonNull;

public class BasePresenter<T extends View> {

    private T view;

    public void attach(T view) {
        this.view = view;
    }

    public void detach() {
        this.view = view;
    }

    protected void onView(@NonNull Action<T> action) {
        Objects.requireNonNull(action, "action must not be null");

        if (view != null) {
            action.perform(view);
        }
    }

}
