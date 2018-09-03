package com.smedialink.common.function;

import android.support.annotation.NonNull;

public interface Action<T> {
    void perform(@NonNull T t);
}
