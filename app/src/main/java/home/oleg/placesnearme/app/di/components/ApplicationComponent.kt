package home.oleg.placesnearme.app.di.components

import dagger.Component
import home.oleg.placesnearme.app.PlacesNearMeApp
import home.oleg.placesnearme.app.di.modules.CoreModule
import home.oleg.placesnearme.coredi.api.AppApi
import home.oleg.placesnearme.coredi.builder.ComponentBuilder
import javax.inject.Singleton

@Component(modules = [CoreModule::class])
@Singleton
interface ApplicationComponent : AppApi {

    @Component.Builder
    interface Builder : ComponentBuilder<ApplicationComponent, PlacesNearMeApp>

}
