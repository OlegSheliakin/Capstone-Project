package home.oleg.placesnearme.corettools.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ToolsModule::class])
interface ToolsComponent : ToolsApi {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindContext(applicationContext: Context): Builder

        fun build(): ToolsComponent

    }

}


