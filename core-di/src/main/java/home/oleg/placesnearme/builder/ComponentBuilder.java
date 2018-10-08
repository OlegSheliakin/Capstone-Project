package home.oleg.placesnearme.builder;

import dagger.BindsInstance;

public interface ComponentBuilder<C, I> {
    @BindsInstance
    ComponentBuilder<C, I> bind(I instance);

    C build();
}
