package home.oleg.feature_favorite_venues.di;

import dagger.BindsInstance;
import dagger.Component;
import home.oleg.feature_favorite_venues.FavoritePlacesFragment;
import home.oleg.placesnearme.AppApiProvider;
import home.oleg.placesnearme.api.AppApi;
import home.oleg.placesnearme.scopes.FeatureScope;

@FeatureScope
@Component(dependencies = AppApi.class, modules = FavoriteVenuesFragmentModule.class)
public interface FavoriteVenuesComponent {
    void inject(FavoritePlacesFragment target);

    @Component.Builder
    interface Builder {
        FavoriteVenuesComponent.Builder appComponent(AppApi appApi);

        @BindsInstance
        FavoriteVenuesComponent.Builder bind(FavoritePlacesFragment target);

        FavoriteVenuesComponent build();
    }

    final class Injector {
        private Injector() {
        }

        public static void inject(FavoritePlacesFragment fragment) {
            AppApi appApi = AppApiProvider.Initializer.getAppApi(fragment);
            DaggerFavoriteVenuesComponent.builder()
                    .appComponent(appApi)
                    .bind(fragment)
                    .build()
                    .inject(fragment);
        }
    }
}
