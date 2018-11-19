package home.oleg.placesnearme.coredata.di


import android.content.Context
import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import home.oleg.placesnearme.coredata.location.CachedLocationsStore
import home.oleg.placesnearme.coredata.location.ReactiveLocationStore
import home.oleg.placesnearme.coredata.repositories.DetailedVenueRepositoryImpl
import home.oleg.placesnearme.coredata.repositories.DistanceRepositoryImpl
import home.oleg.placesnearme.coredata.repositories.FavoriteVenueRepositoryImpl
import home.oleg.placesnearme.coredata.repositories.UserLocationRepositoryImpl
import home.oleg.placesnearme.coredata.repositories.VenueHistoryRepositoryImpl
import home.oleg.placesnearme.coredata.repositories.VenueRepositoryImpl
import home.oleg.placesnearme.coredomain.models.Section
import home.oleg.placesnearme.coredomain.repositories.DetailedVenueRepository
import home.oleg.placesnearme.coredomain.repositories.DistanceRepository
import home.oleg.placesnearme.coredomain.repositories.FavoriteVenuesRepository
import home.oleg.placesnearme.coredomain.repositories.SectionRepository
import home.oleg.placesnearme.coredomain.repositories.UserLocationRepository
import home.oleg.placesnearme.coredomain.repositories.VenueHistoryRepository
import home.oleg.placesnearme.coredomain.repositories.VenueRepository

@Module
abstract class RepositoryModule {

    @Binds
    internal abstract fun provideVenueHistoryRepository(impl: VenueHistoryRepositoryImpl): VenueHistoryRepository

    @Binds
    internal abstract fun provideFavoriteVenuesRepository(impl: FavoriteVenueRepositoryImpl): FavoriteVenuesRepository

    @Binds
    internal abstract fun distanceRepository(impl: DistanceRepositoryImpl): DistanceRepository

    @Binds
    internal abstract fun provideDetailedVenueRepo(impl: DetailedVenueRepositoryImpl): DetailedVenueRepository

    @Binds
    internal abstract fun provideVenueRepo(impl: VenueRepositoryImpl): VenueRepository

    @Binds
    internal abstract fun provideUserLocationRepo(impl: UserLocationRepositoryImpl): UserLocationRepository

    @Module
    companion object {
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
        internal fun provideLocationStore(appContext: Context): ReactiveLocationStore {
            return ReactiveLocationStore.create(appContext)
        }

        @JvmStatic
        @Provides
        internal fun provideCachedLocationsStore(sharedPreferences: SharedPreferences): CachedLocationsStore {
            return CachedLocationsStore(sharedPreferences)
        }
    }

}
