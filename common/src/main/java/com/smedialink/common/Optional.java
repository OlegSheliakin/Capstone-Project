package com.smedialink.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.smedialink.common.function.Action;

public final class Optional<T> {

    private T value;

    private Optional(@Nullable T value) {
        this.value = value;
    }

    public static <T> Optional<T> of(@Nullable T value) {
        return new Optional<>(value);
    }

    public void ifPresent(@NonNull Action<T> action) {
        if (value != null) {
            action.perform(value);
        }
    }

}
