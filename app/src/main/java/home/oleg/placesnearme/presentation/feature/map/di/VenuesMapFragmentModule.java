package home.oleg.placesnearme.presentation.feature.map.di;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import home.oleg.placenearme.models.User;
import home.oleg.placesnearme.presentation.feature.map.view.VenuesMapFragment;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.UserLocationViewModel;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.VenueViewModel;
import io.reactivex.annotations.NonNull;

@Module
public final class VenuesMapFragmentModule {

    @Provides
    @NonNull
    static VenueViewModel provideMapViewModel(
            VenuesMapFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(VenueViewModel.class);
    }

    @Provides
    @NonNull
    static UserLocationViewModel provideUserLocationViewModel(
            VenuesMapFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(UserLocationViewModel.class);
    }

}
