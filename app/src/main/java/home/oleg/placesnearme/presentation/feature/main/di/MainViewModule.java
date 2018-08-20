package home.oleg.placesnearme.presentation.feature.main.di;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.SupportMapFragment;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.presentation.feature.main.view.MainActivity;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.MapViewModel;
import io.reactivex.annotations.NonNull;

@Module
public final class MainViewModule {

    @Provides
    @NonNull
    static MapViewModel provideMapViewModel(MainActivity mainActivity) {
        return ViewModelProviders.of(mainActivity).get(MapViewModel.class);
    }

    @Provides
    @NonNull
    static LifecycleOwner provideLifecycleOwner(MainActivity mainActivity) {
        return mainActivity;
    }

    @Provides
    @NonNull
    static SupportMapFragment provideMapFragment(MainActivity mainActivity) {
        Fragment fragment = mainActivity.getSupportFragmentManager().findFragmentById(R.id.map);
        return (SupportMapFragment) fragment;
    }
}
