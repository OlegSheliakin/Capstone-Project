package home.oleg.placesnearme.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.DataBaseFactory;
import home.oleg.placesnearme.dao.DetailedVenueWithPhotosDao;
import home.oleg.placesnearme.database.AppDatabase;
import home.oleg.placesnearme.database.DaoProvider;

@Module
public class DbModule {

    @Singleton
    @Provides
    static DaoProvider provideDaoProvider(Context context) {
        return DataBaseFactory.create(context);
    }

    @Singleton
    @Provides
    static DetailedVenueWithPhotosDao provideDetailedVenueWithPhotosDao(DaoProvider provider) {
        return provider.getDetailedVenueWithPhotosDao();
    }


}
