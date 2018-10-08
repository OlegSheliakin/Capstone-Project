package home.oleg.placesnearme.di.modules;

import dagger.Module;
import dagger.Provides;
import home.oleg.placenearme.interactors.GetDetailedVenue;
import home.oleg.placenearme.interactors.GetRecommendedVenues;
import home.oleg.placenearme.interactors.GetUserLocation;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placenearme.repositories.SectionRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueRepository;

@Module
public class InteractorModule {

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
    public GetDetailedVenue provideGetDetailedVenueInteractor(DetailedVenueRepository detailedVenueRepository) {
        return new GetDetailedVenue(detailedVenueRepository);
    }

}
