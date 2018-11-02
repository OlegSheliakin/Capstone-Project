package home.oleg.placesnearme.builder

import dagger.BindsInstance

interface ComponentBuilder<out C, in I> {
    @BindsInstance
    fun bind(instance: I): ComponentBuilder<C, I>

    fun build(): C
}
