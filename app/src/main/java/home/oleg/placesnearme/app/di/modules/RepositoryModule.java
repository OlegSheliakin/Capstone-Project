package home.oleg.placesnearme.app.di.modules;


import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import home.oleg.placenearme.models.Section;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placenearme.repositories.DistanceRepository;
import home.oleg.placenearme.repositories.FavoriteVenuesRepository;
import home.oleg.placenearme.repositories.SectionRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueHistoryRepository;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placesnearme.app.PlacesNearMeApp;
import home.oleg.placesnearme.data.dao.DetailedVenueDao;
import home.oleg.placesnearme.data.dao.DetailedVenueHistoryDao;
import home.oleg.placesnearme.data.provider.ReactiveLocationStore;
import home.oleg.placesnearme.data.repositories.DetailedVenueRepositoryImpl;
import home.oleg.placesnearme.data.repositories.DistanceRepositoryImpl;
import home.oleg.placesnearme.data.repositories.FavoriteVenueRepositoryImpl;
import home.oleg.placesnearme.data.repositories.UserLocationRepositoryImpl;
import home.oleg.placesnearme.data.repositories.VenueHistoryRepositoryImpl;
import home.oleg.placesnearme.data.repositories.VenueRepositoryImpl;
import home.oleg.placesnearme.network.service.IFourSquareAPI;

@Module
public final class RepositoryModule {

    @Provides
    public VenueHistoryRepository provideVenueHistoryRepository(
            DetailedVenueDao detailedVenueWithPhotosDao,
            DetailedVenueHistoryDao venueHistoryDao) {
        return new VenueHistoryRepositoryImpl(detailedVenueWithPhotosDao, venueHistoryDao);
    }

    @Provides
    public FavoriteVenuesRepository provideFavoriteVenuesRepository(DetailedVenueDao dao) {
        return new FavoriteVenueRepositoryImpl(dao);
    }

    @Provides
    public DistanceRepository distanceRepository() {
        return new DistanceRepositoryImpl();
    }

    @Provides
    public DetailedVenueRepository provideDetailedVenueRepo(IFourSquareAPI api, DetailedVenueDao dao) {
        return new DetailedVenueRepositoryImpl(api, dao);
    }

    @Provides
    public VenueRepository provideVenueRepo(IFourSquareAPI api) {
        return new VenueRepositoryImpl(api);
    }

    //TODO create real repo
    @Provides
    public SectionRepository provideCategoryRepo() {
        return () -> Section.TOP;
    }

    @Provides
    @NonNull
    static ReactiveLocationStore provideLocationStore(PlacesNearMeApp app) {
        return ReactiveLocationStore.create(app);
    }

    @Provides
    public UserLocationRepository provideUserLocationRepo(ReactiveLocationStore reactiveLocationStore) {
        return new UserLocationRepositoryImpl(reactiveLocationStore);
    }
}
