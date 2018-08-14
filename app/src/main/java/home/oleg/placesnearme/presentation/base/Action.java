package home.oleg.placesnearme.presentation.base;

import io.reactivex.annotations.NonNull;

public interface Action<T> {
    void perform(@NonNull T t);
}
