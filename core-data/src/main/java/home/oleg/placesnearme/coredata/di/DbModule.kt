package home.oleg.placesnearme.coredata.di

import android.content.Context
import dagger.Module
import dagger.Provides
import home.oleg.placesnearme.coredata.DaoProviderFactory
import home.oleg.placesnearme.coredata.dao.DetailedVenueDao
import home.oleg.placesnearme.coredata.dao.DetailedVenueHistoryDao
import home.oleg.placesnearme.coredata.database.DaoProvider
import javax.inject.Singleton

@Module
object DbModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideDaoProvider(context: Context): DaoProvider {
        return DaoProviderFactory.create(context)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDetailedVenueWithPhotosDao(provider: DaoProvider): DetailedVenueDao {
        return provider.detailedVenueWithPhotosDao
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDetailedVenueHistoryDao(provider: DaoProvider): DetailedVenueHistoryDao {
        return provider.venueHistoryDao
    }

}
