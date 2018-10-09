package home.oleg.placesnearme.feature_map.di;

import com.smedialink.feature_venue_detail.venue.di.VenueDetailModule;

import dagger.BindsInstance;
import dagger.Component;
import home.oleg.placesnearme.AppApiProvider;
import home.oleg.placesnearme.api.AppApi;
import home.oleg.placesnearme.scopes.FeatureScope;
import home.oleg.placesnearme.feature_map.view.VenuesMapFragment;

@FeatureScope
@Component(dependencies = AppApi.class, modules = {
        VenuesMapFragmentModule.class,
        ResourceModule.class})
public interface PlacesMapFragmentComponent {

    void inject(VenuesMapFragment target);

    @Component.Builder
    interface Builder {
        PlacesMapFragmentComponent.Builder appComponent(AppApi appApi);

        @BindsInstance
        PlacesMapFragmentComponent.Builder bind(VenuesMapFragment mapFragment);

        PlacesMapFragmentComponent build();
    }

    final class Injector {
        private Injector() {
        }

        public static void inject(VenuesMapFragment fragment) {
            AppApi appApi = AppApiProvider.Initializer.getAppApi(fragment);
            DaggerPlacesMapFragmentComponent.builder()
                    .appComponent(appApi)
                    .bind(fragment)
                    .build()
                    .inject(fragment);
        }
    }

}
