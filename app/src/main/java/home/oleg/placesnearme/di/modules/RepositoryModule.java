package home.oleg.placesnearme.di.modules;

import dagger.Module;
import dagger.Provides;
import home.oleg.placenearme.domain.repositories.VenueRepository;
import home.oleg.placesnearme.data.repositories.VenueRepositoryImpl;
import home.oleg.placesnearme.data.service.IFourSquareAPI;

@Module
public class RepositoryModule {

    @Provides
    public VenueRepository provideVenueRepo(IFourSquareAPI api) {
        return new VenueRepositoryImpl(api);
    }
}
