package com.smedialink.feature_main.di;

import com.smedialink.feature_main.view.MainActivity;
import com.smedialink.feature_venue_detail.venue.di.DaggerVenueDetailComponent;
import com.smedialink.feature_venue_detail.venue.di.VenueDetailComponent;
import com.smedialink.feature_venue_detail.venue.view.VenueFragment;

import dagger.BindsInstance;
import dagger.Component;
import home.oleg.placesnearme.AppApiProvider;
import home.oleg.placesnearme.api.AppApi;
import home.oleg.placesnearme.scopes.FeatureScope;

@FeatureScope
@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {

    void inject(MainActivity target);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainActivityComponent.Builder bind(MainActivity target);

        MainActivityComponent build();
    }

    final class Injector {
        private Injector() {
        }

        public static void inject(MainActivity activity) {
            DaggerMainActivityComponent.builder()
                    .bind(activity)
                    .build()
                    .inject(activity);
        }
    }
}
