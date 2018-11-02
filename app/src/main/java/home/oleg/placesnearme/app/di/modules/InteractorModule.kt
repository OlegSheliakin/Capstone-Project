package home.oleg.placesnearme.app.di.modules

import dagger.Module
import dagger.Provides
import home.oleg.feature_add_history.interactor.CheckInOut
import home.oleg.placenearme.interactors.CreateVenueFavorite
import home.oleg.placenearme.interactors.EvaluateDistance
import home.oleg.placenearme.interactors.GetDetailedVenue
import home.oleg.placenearme.interactors.GetRecommendedVenues
import home.oleg.placenearme.repositories.DetailedVenueRepository
import home.oleg.placenearme.repositories.DistanceRepository
import home.oleg.placenearme.repositories.FavoriteVenuesRepository
import home.oleg.placenearme.repositories.SectionRepository
import home.oleg.placenearme.repositories.UserLocationRepository
import home.oleg.placenearme.repositories.VenueHistoryRepository
import home.oleg.placenearme.repositories.VenueRepository

@Module
object InteractorModule {

    @JvmStatic
    @Provides
    internal fun provideEvaluateDistance(userLocationRepository: UserLocationRepository,
                                distanceRepository: DistanceRepository): EvaluateDistance {
        return EvaluateDistance(userLocationRepository, distanceRepository)
    }

    @JvmStatic
    @Provides
    internal fun provideCheckInOut(venueHistoryRepository: VenueHistoryRepository): CheckInOut {
        return CheckInOut(venueHistoryRepository)
    }

    @JvmStatic
    @Provides
    internal fun provideAddRemoveVenueFavorite(favoriteVenuesRepository: FavoriteVenuesRepository): CreateVenueFavorite {
        return CreateVenueFavorite(favoriteVenuesRepository)
    }

    @JvmStatic
    @Provides
    internal fun provideGetVenuesInteractor(userLocationRepository: UserLocationRepository,
                                   venueRepository: VenueRepository,
                                   categoryRepository: SectionRepository): GetRecommendedVenues {
        return GetRecommendedVenues(venueRepository, userLocationRepository, categoryRepository)
    }

    @JvmStatic
    @Provides
    internal fun provideGetDetailedVenueInteractor(detailedVenueRepository: DetailedVenueRepository,
                                          venueHistoryRepository: VenueHistoryRepository): GetDetailedVenue {
        return GetDetailedVenue(detailedVenueRepository, venueHistoryRepository)
    }

}
