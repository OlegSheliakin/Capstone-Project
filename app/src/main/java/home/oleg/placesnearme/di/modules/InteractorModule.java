package home.oleg.placesnearme.di.modules;

import dagger.Module;
import dagger.Provides;
import home.oleg.placenearme.interactors.GetVenuesInteractor;
import home.oleg.placenearme.repositories.CategoryRepository;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueRepository;

@Module
public class InteractorModule {

    @Provides
    public GetVenuesInteractor provideGetVenuesInteractor(UserLocationRepository userLocationRepository,
                                                          VenueRepository venueRepository,
                                                          DetailedVenueRepository detailedVenueRepository,
                                                          CategoryRepository categoryRepository) {
        return new GetVenuesInteractor(venueRepository, detailedVenueRepository, userLocationRepository, categoryRepository);
    }
}
