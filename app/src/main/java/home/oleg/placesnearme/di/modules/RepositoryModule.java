package home.oleg.placesnearme.di.modules;

import dagger.Module;
import dagger.Provides;
import home.oleg.placenearme.models.UserLocation;
import home.oleg.placenearme.repositories.Category;
import home.oleg.placenearme.repositories.CategoryRepository;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placesnearme.repositories.DetailedVenueRepositoryImpl;
import home.oleg.placesnearme.repositories.QueryParamCreator;
import home.oleg.placesnearme.repositories.VenueRepositoryImpl;
import home.oleg.placesnearme.service.IFourSquareAPI;
import io.reactivex.Single;

@Module
public class RepositoryModule {

    //TODO create real repo
    @Provides
    public UserLocationRepository provideUserLocationRepo() {
        return () -> Single.just(new UserLocation(34.7576236, 39.5017049));
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
    public CategoryRepository provideCategoryRepo() {
        return () -> Category.TOP;
    }
}
