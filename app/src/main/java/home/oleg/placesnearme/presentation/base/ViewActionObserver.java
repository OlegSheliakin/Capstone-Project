package home.oleg.placesnearme.presentation.base;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.Objects;

public class ViewActionObserver<VIEW> implements Observer<ViewAction<VIEW>> {

    private final VIEW view;

    private ViewActionObserver(VIEW view) {
        this.view = view;
    }

    public static <VIEW> ViewActionObserver<VIEW> create(VIEW view) {
        return new ViewActionObserver<>(view);
    }

    @Override
    public void onChanged(@Nullable ViewAction<VIEW> viewAction) {
        Objects.requireNonNull(viewAction).accept(view);

    }
}
