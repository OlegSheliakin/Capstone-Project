package home.oleg.placesnearme.presentation.feature.main.di;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.presentation.feature.main.view.MainActivity;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.MapViewModel;
import io.reactivex.annotations.NonNull;

@Module
public final class MainViewModule {

    @Provides
    @NonNull
    static MapViewModel provideMapViewModel(MainActivity mainActivity, ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(mainActivity, factory).get(MapViewModel.class);
    }
}
