package home.oleg.placesnearme.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Provider;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import home.oleg.placenearme.interactors.GetRecomendedVenuesInteractor;
import home.oleg.placenearme.interactors.GetUserLocationInteractor;
import home.oleg.placesnearme.di.mapkeys.ViewModelKey;
import home.oleg.placesnearme.presentation.feature.main.viewmodel.MainViewModel;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.VenueViewModel;

/**
 * Created by Oleg Sheliakin on 21.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
@Module
public final class ViewModelModule {

    @IntoMap
    @ViewModelKey(VenueViewModel.class)
    @Provides
    @NonNull
    public static ViewModel provideMapViewModel(GetRecomendedVenuesInteractor interactor, GetUserLocationInteractor getUserLocationInteractor) {
        return new VenueViewModel(interactor, getUserLocationInteractor);
    }

    @IntoMap
    @ViewModelKey(MainViewModel.class)
    @Provides
    @NonNull
    public static ViewModel provideMainViewModel() {
        return new MainViewModel();
    }

    @Provides
    @NonNull
    public static ViewModelProvider.Factory provideFactory(Map<Class<? extends ViewModel>,
            Provider<ViewModel>> map) {
        return new ViewModelProvider.Factory() {

            @SuppressWarnings("unchecked")
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                Provider<ViewModel> viewModelProvider = map.get(modelClass);

                if (viewModelProvider == null) {
                    throw new IllegalArgumentException("model class provider"
                            + modelClass
                            + " not found");
                }

                return (T) viewModelProvider.get();
            }
        };
    }

}
