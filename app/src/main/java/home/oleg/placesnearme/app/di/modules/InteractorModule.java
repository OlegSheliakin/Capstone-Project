package home.oleg.placesnearme.app.di.modules;

import dagger.Module;
import dagger.Provides;
import home.oleg.placenearme.interactors.CreateVenueFavorite;
import home.oleg.placenearme.interactors.GetDetailedVenue;
import home.oleg.placenearme.interactors.GetFavoriteVenues;
import home.oleg.placenearme.interactors.GetRecommendedVenues;
import home.oleg.placenearme.interactors.GetUserLocation;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placenearme.repositories.DistanceRepository;
import home.oleg.placenearme.repositories.FavoriteVenuesRepository;
import home.oleg.placenearme.repositories.SectionRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueRepository;

@Module
public class InteractorModule {

    @Provides
    public CreateVenueFavorite provideAddRemoveVenueFavorite(FavoriteVenuesRepository favoriteVenuesRepository) {
        return new CreateVenueFavorite(favoriteVenuesRepository);
    }

    @Provides
    public GetRecommendedVenues provideGetVenuesInteractor(UserLocationRepository userLocationRepository,
                                                           VenueRepository venueRepository,
                                                           SectionRepository categoryRepository,
                                                           DistanceRepository distanceRepository) {
        return new GetRecommendedVenues(venueRepository, userLocationRepository, distanceRepository, categoryRepository);
    }

    @Provides
    public GetUserLocation provideGetUserLocationInteractor(UserLocationRepository userLocationRepository) {
        return new GetUserLocation(userLocationRepository);
    }

    @Provides
    public GetDetailedVenue provideGetDetailedVenueInteractor(DetailedVenueRepository detailedVenueRepository) {
        return new GetDetailedVenue(detailedVenueRepository);
    }

    @Provides
    public GetFavoriteVenues provideGetFavoriteVenues(FavoriteVenuesRepository favoriteVenuesRepository) {
        return new GetFavoriteVenues(favoriteVenuesRepository);
    }

}
