package home.oleg.placesnearme.di.modules;

import android.content.Context;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.PlacesNearMeApp;
import home.oleg.placesnearme.common.converter.DrawableConverter;
import home.oleg.placesnearme.common.converter.DrawableConverterImpl;
import home.oleg.placesnearme.common.provider.ResourceProvider;
import home.oleg.placesnearme.common.provider.ResourceProviderImpl;
import home.oleg.placesnearme.presentation.errorhandler.ErrorHandler;
import home.oleg.placesnearme.repositories.QueryParamCreator;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
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

    @Binds
    @NonNull
    abstract DrawableConverter provideDrawableConverter(DrawableConverterImpl impl);

    @Provides
    @NonNull
    static CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    //TODO implement error handler
    @Provides
    @NonNull
    static ErrorHandler provideErrorHandler() {
        return Timber::e;
    }
}
