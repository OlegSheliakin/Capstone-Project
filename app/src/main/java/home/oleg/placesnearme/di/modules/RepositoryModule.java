package home.oleg.placesnearme.di.modules;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import home.oleg.placenearme.models.Section;
import home.oleg.placenearme.models.UserLocation;
import home.oleg.placenearme.repositories.SectionRepository;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placesnearme.repositories.DetailedVenueRepositoryImpl;
import home.oleg.placesnearme.repositories.UserLocationRepositoryImpl;
import home.oleg.placesnearme.repositories.VenueRepositoryImpl;
import home.oleg.placesnearme.service.IFourSquareAPI;
import io.reactivex.Single;

@Module
public class RepositoryModule {

    @Provides
    public UserLocationRepository provideUserLocationRepo() {
        return new UserLocationRepositoryImpl();
    }

    @Provides
    public DetailedVenueRepository provideDetailedVenueRepo(IFourSquareAPI api) {
        return new DetailedVenueRepositoryImpl(api);
    }

    @Provides
    public VenueRepository provideVenueRepo(IFourSquareAPI api) {
        return new VenueRepositoryImpl(api);
    }

    //TODO create real repo
    @Provides
    public SectionRepository provideCategoryRepo() {
        return () -> Section.Type.TOP;
    }
}
