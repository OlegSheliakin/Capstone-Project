package home.oleg.placesnearme.presentation.feature.map.di;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.presentation.feature.main.view.MainActivity;
import home.oleg.placesnearme.presentation.feature.main.viewmodel.MainViewModel;
import home.oleg.placesnearme.presentation.feature.map.view.PlacesMapFragment;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.MapViewModel;
import io.reactivex.annotations.NonNull;

@Module
public final class PlacesMapFragmentModule {

    @Provides
    @NonNull
    static MapViewModel provideMapViewModel(
            PlacesMapFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(MapViewModel.class);
    }

}
