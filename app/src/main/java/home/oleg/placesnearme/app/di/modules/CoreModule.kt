package home.oleg.placesnearme.app.di.modules

import android.content.Context
import android.content.SharedPreferences

import androidx.annotation.NonNull
import dagger.Binds
import dagger.Module
import dagger.Provides
import home.oleg.placesnearme.app.PlacesNearMeApp
import home.oleg.placesnearme.core_presentation.error_handler.ErrorHandler
import home.oleg.placesnearme.core_presentation.error_handler.MainErrorHandler
import home.oleg.placesnearme.core_presentation.provider.ResourceProvider
import home.oleg.placesnearme.core_presentation.provider.ResourceProviderImpl

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

@Module
abstract class CoreModule {

    @Binds
    abstract fun provideResourceProvider(impl: ResourceProviderImpl): ResourceProvider

    @Binds
    abstract fun provideErrorHandler(impl: MainErrorHandler): ErrorHandler

    @Binds
    abstract fun provideContext(app: PlacesNearMeApp): Context

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideSharedPreferences(app: PlacesNearMeApp): SharedPreferences {
            return app.getSharedPreferences(PlacesNearMeApp::class.java.simpleName, Context.MODE_PRIVATE)
        }
    }
}
