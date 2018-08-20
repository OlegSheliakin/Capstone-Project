package home.oleg.placesnearme.utils;

import dagger.Lazy;
import home.oleg.placenearme.utils.Supplier;

public class LazyDelegate<T> implements Lazy<T> {

    private T value;
    private final Supplier<T> supplier;

    private LazyDelegate(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> Lazy<T> delegate(Supplier<T> supplier) {
        return new LazyDelegate<>(supplier);
    }

    @Override
    public T get() {
        if (value == null) {
            value = supplier.supply();
        }

        return value;
    }

}
