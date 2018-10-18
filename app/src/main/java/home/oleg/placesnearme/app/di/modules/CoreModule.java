package home.oleg.placesnearme.app.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.smedialink.common.ErrorHandler;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.app.PlacesNearMeApp;
import home.oleg.placesnearme.core_presentation.provider.ResourceProvider;
import home.oleg.placesnearme.core_presentation.provider.ResourceProviderImpl;
import timber.log.Timber;

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

@Module
public abstract class CoreModule {

    @Provides
    @NonNull
    static Context provideContext(PlacesNearMeApp app) {
        return app;
    }

    @Binds
    @NonNull
    abstract ResourceProvider provideResourceProvider(ResourceProviderImpl impl);

    //TODO implement error handler
    @Provides
    @NonNull
    static ErrorHandler provideErrorHandler() {
        return Timber::e;
    }
}
