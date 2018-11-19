package home.oleg.placesnearme.corettools.di

import dagger.Binds
import dagger.Module
import home.oleg.placesnearme.corettools.error_handler.ErrorHandler
import home.oleg.placesnearme.corettools.error_handler.MainErrorHandler
import home.oleg.placesnearme.corettools.logger.Logger
import home.oleg.placesnearme.corettools.logger.LoggerImpl
import home.oleg.placesnearme.corettools.resource.ResourceProvider
import home.oleg.placesnearme.corettools.resource.ResourceProviderImpl

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

@Module
abstract class ToolsModule {

    @Binds
    abstract fun provideResourceProvider(impl: ResourceProviderImpl): ResourceProvider

    @Binds
    abstract fun provideErrorHandler(impl: MainErrorHandler): ErrorHandler

    @Binds
    abstract fun provideLogger(impl: LoggerImpl): Logger

}
