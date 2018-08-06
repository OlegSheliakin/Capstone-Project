package home.oleg.placesnearme.presentation.feature.base;

import io.reactivex.annotations.NonNull;

public interface Action<T> {
    void perform(@NonNull T t);
}
