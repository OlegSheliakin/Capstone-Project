package home.oleg.placesnearme.di.modules;

import dagger.Module;
import dagger.Provides;
import home.oleg.placenearme.interactors.GetRecomendedVenuesInteractor;
import home.oleg.placenearme.interactors.GetUserLocationInteractor;
import home.oleg.placenearme.repositories.SectionRepository;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueRepository;

@Module
public class InteractorModule {

    @Provides
    public GetRecomendedVenuesInteractor provideGetVenuesInteractor(UserLocationRepository userLocationRepository,
                                                                    VenueRepository venueRepository,
                                                                    DetailedVenueRepository detailedVenueRepository,
                                                                    SectionRepository categoryRepository) {
        return new GetRecomendedVenuesInteractor(venueRepository, detailedVenueRepository, userLocationRepository, categoryRepository);
    }

    @Provides
    public GetUserLocationInteractor provideGetUserLocationInteractor(UserLocationRepository userLocationRepository) {
        return new GetUserLocationInteractor(userLocationRepository);
    }

}
