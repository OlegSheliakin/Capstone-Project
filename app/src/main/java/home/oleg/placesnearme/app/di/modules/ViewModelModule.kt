package home.oleg.placesnearme.app.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.home.olegsheliakin.corettools.error_handler.ErrorHandler
import com.smedialink.feature_add_favorite.presentation.CreateFavoriteViewModelDelegate
import com.smedialink.feature_main.viewmodel.MainViewModel
import com.smedialink.feature_venue_detail.domain.GetDetailedVenue
import com.smedialink.feature_venue_detail.presentation.VenueViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import home.oleg.feature_add_history.presentation.viewmodel.CheckInViewModelDelegate
import home.oleg.feature_favorite_venues.presentation.FavoritePlacesViewModel
import home.oleg.placenearme.interactors.EvaluateDistance
import home.oleg.placenearme.interactors.GetRecommendedVenues
import home.oleg.placenearme.repositories.FavoriteVenuesRepository
import home.oleg.placenearme.repositories.UserLocationRepository
import home.oleg.placenearme.repositories.VenueHistoryRepository
import home.oleg.placesnearme.app.di.mapkeys.ViewModelKey
import home.oleg.placesnearme.feature_map.presentation.viewmodel.UserLocationViewModel
import home.oleg.placesnearme.feature_map.presentation.viewmodel.VenuesViewModel
import home.oleg.placesnearme.feature_venues_history.domain.interactor.ObserveHistory
import home.oleg.placesnearme.feature_venues_history.presentation.VenuesHistoryViewModel
import javax.inject.Provider

/**
 * Created by Oleg Sheliakin on 21.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
@Module
object ViewModelModule {

    @JvmStatic
    @Provides
    internal fun provideFactory(map: MutableMap<Class<out ViewModel>, Provider<ViewModel>>): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val viewModelProvider = map[modelClass]
                        ?: throw IllegalArgumentException("model class provider"
                                + modelClass
                                + " not found")

                return viewModelProvider.get() as T
            }
        }
    }

    @JvmStatic
    @IntoMap
    @ViewModelKey(VenuesHistoryViewModel::class)
    @Provides
    internal fun provideVenuesHistoryViewModel(
            createFavoriteViewModelDelegate: CreateFavoriteViewModelDelegate,
            observeHistory: ObserveHistory): ViewModel {
        return VenuesHistoryViewModel(createFavoriteViewModelDelegate, observeHistory)
    }

    @JvmStatic
    @IntoMap
    @ViewModelKey(FavoritePlacesViewModel::class)
    @Provides
    internal fun provideFavoritePlacesViewModel(
            createFavoriteViewModelDelegate: CreateFavoriteViewModelDelegate,
            favoriteVenuesRepository: FavoriteVenuesRepository): ViewModel {
        return FavoritePlacesViewModel(createFavoriteViewModelDelegate, favoriteVenuesRepository::observeFavorites)
    }

    @JvmStatic
    @IntoMap
    @ViewModelKey(VenueViewModel::class)
    @Provides
    internal fun provideVenueViewModel(errorHanlder: ErrorHandler,
                                       createFavoriteViewModelDelegate: CreateFavoriteViewModelDelegate,
                                       checkInViewModelDelegate: CheckInViewModelDelegate,
                                       getDetailedVenue: GetDetailedVenue): ViewModel {
        return VenueViewModel(createFavoriteViewModelDelegate,
                checkInViewModelDelegate,
                errorHanlder,
                getDetailedVenue)
    }

    @JvmStatic
    @IntoMap
    @ViewModelKey(VenuesViewModel::class)
    @Provides
    internal fun provideMapViewModel(errorHandler: ErrorHandler,
                                     interactor: GetRecommendedVenues): ViewModel {
        return VenuesViewModel(errorHandler, interactor::getRecommendedSection)
    }

    @JvmStatic
    @IntoMap
    @ViewModelKey(UserLocationViewModel::class)
    @Provides
    internal fun provideUserLocationViewModel(userLocationRepository: UserLocationRepository): ViewModel {
        return UserLocationViewModel(userLocationRepository::location)
    }

    @JvmStatic
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Provides
    internal fun provideMainViewModel(): ViewModel {
        return MainViewModel()
    }

}
