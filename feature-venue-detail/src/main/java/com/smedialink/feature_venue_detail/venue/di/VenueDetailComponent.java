package com.smedialink.feature_venue_detail.venue.di;

import com.smedialink.feature_venue_detail.venue.view.VenueFragment;

import dagger.BindsInstance;
import dagger.Component;
import home.oleg.placesnearme.AppApiProvider;
import home.oleg.placesnearme.api.AppApi;
import home.oleg.placesnearme.scopes.FeatureScope;

@FeatureScope
@Component(dependencies = AppApi.class, modules = VenueDetailModule.class)
public interface VenueDetailComponent {
    void inject(VenueFragment target);

    @Component.Builder
    interface Builder {
        VenueDetailComponent.Builder appComponent(AppApi appApi);

        @BindsInstance
        VenueDetailComponent.Builder bind(VenueFragment target);

        VenueDetailComponent build();
    }

    final class Injector {
        private Injector() {
        }

        public static void inject(VenueFragment fragment) {
            AppApi appApi = AppApiProvider.Initializer.getAppApi(fragment);
            DaggerVenueDetailComponent.builder()
                    .appComponent(appApi)
                    .bind(fragment)
                    .build()
                    .inject(fragment);
        }
    }
}
