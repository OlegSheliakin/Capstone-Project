package home.oleg.placesnearme.app

import android.app.Application

import home.oleg.placesnearme.AppApiProvider
import home.oleg.placesnearme.api.AppApi
import home.oleg.placesnearme.app.di.components.ApplicationComponent
import home.oleg.placesnearme.app.di.components.DaggerApplicationComponent
import timber.log.Timber

class PlacesNearMeApp : Application(), AppApiProvider {
    lateinit var applicationComponent: ApplicationComponent

    override val appApi: AppApi
        get() = applicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder().bind(this).build()
        Timber.plant(Timber.DebugTree())
    }
}
