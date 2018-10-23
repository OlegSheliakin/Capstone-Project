package home.oleg.placesnearme.app.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.data.DaoProviderFactory;
import home.oleg.placesnearme.data.dao.DetailedVenueDao;
import home.oleg.placesnearme.data.dao.DetailedVenueHistoryDao;
import home.oleg.placesnearme.data.database.DaoProvider;

@Module
public final class DbModule {

    @Singleton
    @Provides
    static DaoProvider provideDaoProvider(Context context) {
        return DaoProviderFactory.create(context);
    }

    @Singleton
    @Provides
    static DetailedVenueDao provideDetailedVenueWithPhotosDao(DaoProvider provider) {
        return provider.getDetailedVenueWithPhotosDao();
    }

    @Singleton
    @Provides
    static DetailedVenueHistoryDao provideDetailedVenueHistoryDao(DaoProvider provider) {
        return provider.getVenueHistoryDao();
    }

}
