package home.oleg.placesnearme.di.modules;


import dagger.Module;
import dagger.Provides;
import home.oleg.placenearme.models.Section;
import home.oleg.placenearme.repositories.SectionRepository;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placesnearme.PlacesNearMeApp;
import home.oleg.placesnearme.network.service.IFourSquareAPI;
import home.oleg.placesnearme.provider.ReactiveLocationStore;
import home.oleg.placesnearme.repositories.DetailedVenueRepositoryImpl;
import home.oleg.placesnearme.repositories.UserLocationRepositoryImpl;
import home.oleg.placesnearme.repositories.VenueRepositoryImpl;
import io.reactivex.annotations.NonNull;

@Module
public class RepositoryModule {

    /* TODO В репо impl лучше добавить @Inject в конструктор, а здесь как-то так
    (чтобы не было return new для имплементаций интерфейсов):

    @Provides
    public DetailedVenueRepository provideDetailedVenueRepo(DetailedVenueRepositoryImpl repoImpl) {
        return repoImpl;
    }
    ...

    А чтоб не тащить весь даггер в data, можно добавить только аннотацию:

    implementation "javax.inject:javax.inject:1"

    */

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
