package home.oleg.placesnearme.app.di.modules;

import dagger.Module;
import dagger.Provides;
import home.oleg.feature_add_history.interactor.CheckInOut;
import home.oleg.placenearme.interactors.CreateVenueFavorite;
import home.oleg.placenearme.interactors.EvaluateDistance;
import home.oleg.placenearme.interactors.GetAllHistory;
import home.oleg.placenearme.interactors.GetDetailedVenue;
import home.oleg.placenearme.interactors.GetFavoriteVenues;
import home.oleg.placenearme.interactors.GetRecommendedVenues;
import home.oleg.placenearme.interactors.GetUserLocation;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placenearme.repositories.DistanceRepository;
import home.oleg.placenearme.repositories.FavoriteVenuesRepository;
import home.oleg.placenearme.repositories.SectionRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueHistoryRepository;
import home.oleg.placenearme.repositories.VenueRepository;

@Module
public final class InteractorModule {

    @Provides
    public EvaluateDistance provideEvaluateDistance(UserLocationRepository userLocationRepository,
                                                    DistanceRepository distanceRepository) {
        return new EvaluateDistance(userLocationRepository, distanceRepository);
    }

    @Provides
    public CheckInOut provideCheckInOut(VenueHistoryRepository venueHistoryRepository) {
        return new CheckInOut(venueHistoryRepository);
    }

    @Provides
    public GetAllHistory provideGetAllHistory(VenueHistoryRepository venueHistoryRepository) {
        return new GetAllHistory(venueHistoryRepository);
    }

    @Provides
    public CreateVenueFavorite provideAddRemoveVenueFavorite(FavoriteVenuesRepository favoriteVenuesRepository) {
        return new CreateVenueFavorite(favoriteVenuesRepository);
    }

    @Provides
    public GetRecommendedVenues provideGetVenuesInteractor(UserLocationRepository userLocationRepository,
                                                           VenueRepository venueRepository,
                                                           SectionRepository categoryRepository) {
        return new GetRecommendedVenues(venueRepository, userLocationRepository, categoryRepository);
    }

    @Provides
    public GetUserLocation provideGetUserLocationInteractor(UserLocationRepository userLocationRepository) {
        return new GetUserLocation(userLocationRepository);
    }

    @Provides
    public GetDetailedVenue provideGetDetailedVenueInteractor(DetailedVenueRepository detailedVenueRepository,
                                                              VenueHistoryRepository venueHistoryRepository) {
        return new GetDetailedVenue(detailedVenueRepository, venueHistoryRepository);
    }

    @Provides
    public GetFavoriteVenues provideGetFavoriteVenues(FavoriteVenuesRepository favoriteVenuesRepository) {
        return new GetFavoriteVenues(favoriteVenuesRepository);
    }

}
