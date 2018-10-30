package home.oleg.placesnearme.app.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.smedialink.feature_add_favorite.CreateFavoriteViewModel;
import com.smedialink.feature_main.viewmodel.MainViewModel;
import com.smedialink.feature_venue_detail.venue.viewmodel.VenueViewModel;

import java.util.Map;

import javax.inject.Provider;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import home.oleg.feature_add_history.CheckInViewModel;
import home.oleg.feature_add_history.interactor.CheckInOut;
import home.oleg.feature_favorite_venues.FavoritePlacesViewModel;
import home.oleg.placenearme.interactors.CreateVenueFavorite;
import home.oleg.placenearme.interactors.EvaluateDistance;
import home.oleg.placenearme.interactors.GetAllHistory;
import home.oleg.placenearme.interactors.GetDetailedVenue;
import home.oleg.placenearme.interactors.GetFavoriteVenues;
import home.oleg.placenearme.interactors.GetRecommendedVenues;
import home.oleg.placenearme.interactors.GetUserLocation;
import home.oleg.placesnearme.app.di.mapkeys.ViewModelKey;
import home.oleg.placesnearme.core_presentation.error_handler.ErrorHanlder;
import home.oleg.placesnearme.core_presentation.provider.ResourceProvider;
import home.oleg.placesnearme.feature_map.manager.NetworkConnectivityManager;
import home.oleg.placesnearme.feature_map.viewmodel.UserLocationViewModel;
import home.oleg.placesnearme.feature_map.viewmodel.VenuesViewModel;
import home.oleg.placesnearme.feature_venues_history.VenuesHistoryViewModel;

/**
 * Created by Oleg Sheliakin on 21.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
@Module
public final class ViewModelModule {

    @IntoMap
    @ViewModelKey(CheckInViewModel.class)
    @Provides
    @NonNull
    public static ViewModel provideCheckInViewModel(CheckInOut checkInOut, ResourceProvider resourceProvider) {
        return new CheckInViewModel(checkInOut, resourceProvider);
    }

    @IntoMap
    @ViewModelKey(VenuesHistoryViewModel.class)
    @Provides
    @NonNull
    public static ViewModel provideVenuesHistoryViewModel(GetAllHistory getAllHistory, EvaluateDistance evaluateDistance) {
        return new VenuesHistoryViewModel(getAllHistory, evaluateDistance);
    }

    @IntoMap
    @ViewModelKey(CreateFavoriteViewModel.class)
    @Provides
    @NonNull
    public static ViewModel provideCreateFavoriteViewModel(CreateVenueFavorite createVenueFavorite, ResourceProvider resourceProvider) {
        return new CreateFavoriteViewModel(createVenueFavorite, resourceProvider);
    }

    @IntoMap
    @ViewModelKey(FavoritePlacesViewModel.class)
    @Provides
    @NonNull
    public static ViewModel provideFavoritePlacesViewModel(GetFavoriteVenues getFavoriteVenues) {
        return new FavoritePlacesViewModel(getFavoriteVenues);
    }

    @IntoMap
    @ViewModelKey(VenueViewModel.class)
    @Provides
    @NonNull
    public static ViewModel provideVenueViewModel(ErrorHanlder errorHanlder,
                                                  GetDetailedVenue getDetailedVenue,
                                                  EvaluateDistance evaluateDistance) {
        return new VenueViewModel(errorHanlder, getDetailedVenue, evaluateDistance);
    }

    @IntoMap
    @ViewModelKey(VenuesViewModel.class)
    @Provides
    @NonNull
    public static ViewModel provideMapViewModel(ErrorHanlder errorHanlder,
                                                GetRecommendedVenues interactor,
                                                ResourceProvider resourceProvider,
                                                NetworkConnectivityManager connectivityManager) {
        return new VenuesViewModel(errorHanlder, interactor, resourceProvider, connectivityManager);
    }

    @IntoMap
    @ViewModelKey(UserLocationViewModel.class)
    @Provides
    @NonNull
    public static ViewModel provideUserLocationViewModel(GetUserLocation getUserLocationInteractor) {
        return new UserLocationViewModel(getUserLocationInteractor);
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
    public ViewModelProvider.Factory provideFactory(Map<Class<? extends ViewModel>,
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
