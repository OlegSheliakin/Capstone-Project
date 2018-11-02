package home.oleg.placesnearme.app.di.modules


import android.content.SharedPreferences

import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import home.oleg.placenearme.models.Section
import home.oleg.placenearme.repositories.DetailedVenueRepository
import home.oleg.placenearme.repositories.DistanceRepository
import home.oleg.placenearme.repositories.FavoriteVenuesRepository
import home.oleg.placenearme.repositories.SectionRepository
import home.oleg.placenearme.repositories.UserLocationRepository
import home.oleg.placenearme.repositories.VenueHistoryRepository
import home.oleg.placenearme.repositories.VenueRepository
import home.oleg.placesnearme.app.PlacesNearMeApp
import home.oleg.placesnearme.data.dao.DetailedVenueDao
import home.oleg.placesnearme.data.dao.DetailedVenueHistoryDao
import home.oleg.placesnearme.data.provider.CachedLocationsStore
import home.oleg.placesnearme.data.provider.ReactiveLocationStore
import home.oleg.placesnearme.data.repositories.DetailedVenueRepositoryImpl
import home.oleg.placesnearme.data.repositories.DistanceRepositoryImpl
import home.oleg.placesnearme.data.repositories.FavoriteVenueRepositoryImpl
import home.oleg.placesnearme.data.repositories.UserLocationRepositoryImpl
import home.oleg.placesnearme.data.repositories.VenueHistoryRepositoryImpl
import home.oleg.placesnearme.data.repositories.VenueRepositoryImpl
import home.oleg.placesnearme.network.service.IFourSquareAPI

@Module
object RepositoryModule {

    @JvmStatic
    @Provides
    internal fun provideVenueHistoryRepository(
            detailedVenueWithPhotosDao: DetailedVenueDao,
            venueHistoryDao: DetailedVenueHistoryDao): VenueHistoryRepository {
        return VenueHistoryRepositoryImpl(detailedVenueWithPhotosDao, venueHistoryDao)
    }

    @JvmStatic
    @Provides
    internal fun provideFavoriteVenuesRepository(dao: DetailedVenueDao): FavoriteVenuesRepository {
        return FavoriteVenueRepositoryImpl(dao)
    }

    @JvmStatic
    @Provides
    internal fun distanceRepository(): DistanceRepository {
        return DistanceRepositoryImpl()
    }

    @JvmStatic
    @Provides
    internal fun provideDetailedVenueRepo(api: IFourSquareAPI, dao: DetailedVenueDao): DetailedVenueRepository {
        return DetailedVenueRepositoryImpl(api, dao)
    }

    @JvmStatic
    @Provides
    internal fun provideVenueRepo(api: IFourSquareAPI): VenueRepository {
        return VenueRepositoryImpl(api)
    }

    @JvmStatic
    @Provides
    internal fun provideCategoryRepo(): SectionRepository {
        return object : SectionRepository {
            override val mostFrequent: Section
                get() = Section.TOP

        }
    }

    @JvmStatic
    @Provides
    internal fun provideUserLocationRepo(reactiveLocationStore: ReactiveLocationStore, cachedLocationsStore: CachedLocationsStore): UserLocationRepository {
        return UserLocationRepositoryImpl(reactiveLocationStore, cachedLocationsStore)
    }

    @JvmStatic
    @Provides
    internal fun provideLocationStore(app: PlacesNearMeApp): ReactiveLocationStore {
        return ReactiveLocationStore.create(app)
    }

    @JvmStatic
    @Provides
    internal fun provideCachedLocationsStore(sharedPreferences: SharedPreferences): CachedLocationsStore {
        return CachedLocationsStore(sharedPreferences)
    }
}
