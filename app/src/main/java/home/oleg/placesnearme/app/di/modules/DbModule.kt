package home.oleg.placesnearme.app.di.modules

import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import home.oleg.placesnearme.data.DaoProviderFactory
import home.oleg.placesnearme.data.dao.DetailedVenueDao
import home.oleg.placesnearme.data.dao.DetailedVenueHistoryDao
import home.oleg.placesnearme.data.database.DaoProvider

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
