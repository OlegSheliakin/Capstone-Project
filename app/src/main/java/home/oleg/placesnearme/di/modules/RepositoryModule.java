package home.oleg.placesnearme.di.modules;

import dagger.Module;
import dagger.Provides;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placesnearme.repositories.VenueRepositoryImpl;
import home.oleg.placesnearme.service.IFourSquareAPI;

@Module
public class RepositoryModule {

    @Provides
    public VenueRepository provideVenueRepo(IFourSquareAPI api) {
        return new VenueRepositoryImpl(api);
    }
}
