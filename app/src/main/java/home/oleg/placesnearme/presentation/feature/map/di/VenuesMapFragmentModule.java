package home.oleg.placesnearme.presentation.feature.map.di;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.presentation.feature.map.view.VenuesMapFragment;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.UserLocationViewModel;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.VenuesViewModel;
import home.oleg.placesnearme.presentation.feature.venue.viewmodel.VenueViewModel;
import io.reactivex.annotations.NonNull;

@Module
public final class VenuesMapFragmentModule {

    @Provides
    @NonNull
    static VenueViewModel provideVenueViewModel(
            VenuesMapFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(VenueViewModel.class);
    }

    @Provides
    @NonNull
    static VenuesViewModel provideMapViewModel(
            VenuesMapFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(VenuesViewModel.class);
    }

    @Provides
    @NonNull
    static UserLocationViewModel provideUserLocationViewModel(
            VenuesMapFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(UserLocationViewModel.class);
    }

    @Provides
    @NonNull
    static LifecycleOwner provideLifeCycleOwner(VenuesMapFragment mapFragment) {
        return mapFragment;
    }

}
