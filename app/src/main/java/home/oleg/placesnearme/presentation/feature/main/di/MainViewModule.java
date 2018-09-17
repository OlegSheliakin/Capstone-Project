package home.oleg.placesnearme.presentation.feature.main.di;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.presentation.feature.main.view.MainActivity;
import home.oleg.placesnearme.presentation.feature.main.viewmodel.MainViewModel;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.VenueViewModel;
import io.reactivex.annotations.NonNull;

@Module
public final class MainViewModule {

    @Provides
    @NonNull
    static VenueViewModel provideMapViewModel(MainActivity mainActivity, ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(mainActivity, factory).get(VenueViewModel.class);
    }

    @Provides
    @NonNull
    static MainViewModel provideMainViewModel(MainActivity mainActivity, ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(mainActivity, factory).get(MainViewModel.class);
    }

}
