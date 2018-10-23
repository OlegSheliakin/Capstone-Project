package home.oleg.placesnearme.feature_venues_history.di;

import dagger.BindsInstance;
import dagger.Component;
import home.oleg.placesnearme.AppApiProvider;
import home.oleg.placesnearme.api.AppApi;
import home.oleg.placesnearme.feature_venues_history.VenuesHistoryFragment;
import home.oleg.placesnearme.scopes.FeatureScope;

@FeatureScope
@Component(dependencies = AppApi.class, modules = VenuesHistoryModule.class)
public interface VenueHistoryComponent {
    void inject(VenuesHistoryFragment target);

    @Component.Builder
    interface Builder {
        VenueHistoryComponent.Builder appComponent(AppApi appApi);

        @BindsInstance
        VenueHistoryComponent.Builder bind(VenuesHistoryFragment target);

        VenueHistoryComponent build();
    }

    final class Injector {
        private Injector() {
        }

        public static void inject(VenuesHistoryFragment fragment) {
            AppApi appApi = AppApiProvider.Initializer.getAppApi(fragment);
            DaggerVenueHistoryComponent.builder()
                    .appComponent(appApi)
                    .bind(fragment)
                    .build()
                    .inject(fragment);
        }
    }
}
