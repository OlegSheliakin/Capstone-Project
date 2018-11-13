package home.oleg.placesnearme.app.di.modules

import android.content.Context
import android.content.SharedPreferences

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import home.oleg.placesnearme.app.PlacesNearMeApp
import com.home.olegsheliakin.corettools.error_handler.ErrorHandler
import com.home.olegsheliakin.corettools.error_handler.MainErrorHandler
import com.home.olegsheliakin.corettools.logger.Logger
import com.home.olegsheliakin.corettools.logger.LoggerImpl
import com.home.olegsheliakin.corettools.resource.ResourceProvider
import com.home.olegsheliakin.corettools.resource.ResourceProviderImpl

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

    @Binds
    abstract fun provideLogger(impl: LoggerImpl): Logger

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideSharedPreferences(app: PlacesNearMeApp): SharedPreferences {
            return app.getSharedPreferences(PlacesNearMeApp::class.java.simpleName, Context.MODE_PRIVATE)
        }
    }
}
