package home.oleg.placesnearme.app.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.data.DataBaseFactory;
import home.oleg.placesnearme.data.dao.DetailedVenueWithPhotosDao;
import home.oleg.placesnearme.data.database.DaoProvider;

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
