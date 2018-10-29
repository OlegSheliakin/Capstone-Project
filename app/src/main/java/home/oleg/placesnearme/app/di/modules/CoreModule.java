package home.oleg.placesnearme.app.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.app.PlacesNearMeApp;
import home.oleg.placesnearme.core_presentation.error_handler.ErrorHanlder;
import home.oleg.placesnearme.core_presentation.error_handler.MainErrorHandler;
import home.oleg.placesnearme.core_presentation.provider.ResourceProvider;
import home.oleg.placesnearme.core_presentation.provider.ResourceProviderImpl;
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

    @Provides
    @NonNull
    static SharedPreferences provideSharedPreferences(PlacesNearMeApp app) {
        return app.getSharedPreferences(PlacesNearMeApp.class.getSimpleName(), Context.MODE_PRIVATE);
    }

    @Binds
    @NonNull
    abstract ResourceProvider provideResourceProvider(ResourceProviderImpl impl);

    @Binds
    @NonNull
    abstract ErrorHanlder provideErrorHandler(MainErrorHandler impl);
}
