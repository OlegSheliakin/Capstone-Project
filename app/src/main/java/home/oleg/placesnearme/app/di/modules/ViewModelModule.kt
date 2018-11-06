package home.oleg.placesnearme.app.di.modules

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smedialink.feature_add_favorite.CreateFavoriteViewModel
import com.smedialink.feature_add_favorite.FavoriteMessageEventMapper
import com.smedialink.feature_main.viewmodel.MainViewModel
import com.smedialink.feature_venue_detail.venue.viewmodel.VenueViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import home.oleg.feature_add_history.CheckInViewModel
import home.oleg.feature_add_history.interactor.CheckInOut
import home.oleg.feature_favorite_venues.FavoritePlacesViewModel
import home.oleg.placenearme.interactors.CreateVenueFavorite
import home.oleg.placenearme.interactors.EvaluateDistance
import home.oleg.placenearme.interactors.GetDetailedVenue
import home.oleg.placenearme.interactors.GetRecommendedVenues
import home.oleg.placenearme.repositories.FavoriteVenuesRepository
import home.oleg.placenearme.repositories.UserLocationRepository
import home.oleg.placenearme.repositories.VenueHistoryRepository
import home.oleg.placesnearme.app.di.mapkeys.ViewModelKey
import home.oleg.placesnearme.core_presentation.error_handler.ErrorHandler
import home.oleg.placesnearme.core_presentation.provider.ResourceProvider
import home.oleg.placesnearme.feature_map.viewmodel.UserLocationViewModel
import home.oleg.placesnearme.feature_map.viewmodel.VenuesViewModel
import home.oleg.placesnearme.feature_venues_history.VenuesHistoryViewModel
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
    @ViewModelKey(CheckInViewModel::class)
    @Provides
    internal fun provideCheckInViewModel(checkInOut: CheckInOut, resourceProvider: ResourceProvider): ViewModel {
        return CheckInViewModel(checkInOut, resourceProvider)
    }

    @JvmStatic
    @IntoMap
    @ViewModelKey(VenuesHistoryViewModel::class)
    @Provides
    internal fun provideVenuesHistoryViewModel(venueHistoryRepository: VenueHistoryRepository,
                                               evaluateDistance: EvaluateDistance): ViewModel {
        return VenuesHistoryViewModel(venueHistoryRepository::history, evaluateDistance)
    }

    @JvmStatic
    @IntoMap
    @ViewModelKey(CreateFavoriteViewModel::class)
    @Provides
    internal fun provideCreateFavoriteViewModel(createVenueFavorite: CreateVenueFavorite,
                                                mapper: FavoriteMessageEventMapper): ViewModel {
        return CreateFavoriteViewModel(createVenueFavorite, mapper)
    }

    @JvmStatic
    @IntoMap
    @ViewModelKey(FavoritePlacesViewModel::class)
    @Provides
    internal fun provideFavoritePlacesViewModel(favoriteVenuesRepository: FavoriteVenuesRepository): ViewModel {
        return FavoritePlacesViewModel(favoriteVenuesRepository::observeFavorites)
    }

    @JvmStatic
    @IntoMap
    @ViewModelKey(VenueViewModel::class)
    @Provides
    internal fun provideVenueViewModel(errorHanlder: ErrorHandler,
                                       getDetailedVenue: GetDetailedVenue,
                                       evaluateDistance: EvaluateDistance): ViewModel {
        return VenueViewModel(errorHanlder,
                getDetailedVenue::getDetailedVenue,
                getDetailedVenue::getCachedDetailVenue,
                evaluateDistance::evaluateDistance)
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
