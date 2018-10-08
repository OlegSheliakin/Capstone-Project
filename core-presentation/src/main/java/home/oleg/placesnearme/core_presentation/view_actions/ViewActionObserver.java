package home.oleg.placesnearme.core_presentation.view_actions;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.smedialink.common.Optional;
import com.smedialink.common.function.Action;

import java.util.Objects;

public final class ViewActionObserver<VIEW> implements Observer<Action<VIEW>> {

    private final VIEW view;

    private ViewActionObserver(@NonNull VIEW view) {
        Objects.requireNonNull(view);
        this.view = view;
    }

    public static <VIEW> ViewActionObserver<VIEW> create(@NonNull VIEW view) {
        return new ViewActionObserver<>(view);
    }

    @Override
    public void onChanged(@Nullable Action<VIEW> viewAction) {
        Optional.of(viewAction).ifPresent(a -> a.perform(view));
    }
}
