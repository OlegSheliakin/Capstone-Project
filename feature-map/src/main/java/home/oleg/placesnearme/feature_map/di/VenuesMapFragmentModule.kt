package home.oleg.placesnearme.feature_map.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import home.oleg.placesnearme.coredi.mapkeys.ViewModelKey
import home.oleg.placesnearme.coredomain.interactors.GetRecommendedVenues
import home.oleg.placesnearme.coredomain.repositories.UserLatLngRepository
import home.oleg.placesnearme.corettools.error_handler.ErrorHandler
import home.oleg.placesnearme.feature_add_favorite.presentation.CreateFavoriteViewModelDelegate
import home.oleg.placesnearme.feature_add_history.presentation.viewmodel.CheckInViewModelDelegate
import home.oleg.placesnearme.feature_map.drawable_converter.DrawableConverter
import home.oleg.placesnearme.feature_map.drawable_converter.DrawableConverterImpl
import home.oleg.placesnearme.feature_map.sections.SectionProvider
import home.oleg.placesnearme.feature_map.presentation.MapFragment
import home.oleg.placesnearme.feature_map.presentation.adapter.CheckedItem
import home.oleg.placesnearme.feature_map.presentation.adapter.SectionsAdapter
import home.oleg.placesnearme.feature_map.presentation.MapViewModel
import home.oleg.placesnearme.feature_venue_detail.domain.GetDetailedVenue
import home.oleg.placesnearme.feature_venue_detail.presentation.VenueViewModel

@Module
abstract class VenuesMapFragmentModule {

    @Binds
    internal abstract fun provideConverter(impl: DrawableConverterImpl): DrawableConverter

    @Module
    companion object {

        @JvmStatic
        @Provides
        internal fun provideActivity(fragment: MapFragment): Activity {
            return fragment.activity!!
        }

        @JvmStatic
        @IntoMap
        @ViewModelKey(VenueViewModel::class)
        @Provides
        internal fun provideVenueViewModel_(errorHanlder: ErrorHandler,
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
        @ViewModelKey(MapViewModel::class)
        @Provides
        internal fun provideMapViewModel_(errorHandler: ErrorHandler,
                                          interactor: GetRecommendedVenues,
                                          userLocationRepository: UserLatLngRepository): ViewModel {
            return MapViewModel(errorHandler, userLocationRepository, interactor)
        }

        @JvmStatic
        @Provides
        internal fun provideVenueViewModel(
                fragment: MapFragment,
                factory: ViewModelProvider.Factory): VenueViewModel {
            return ViewModelProviders.of(fragment, factory).get(VenueViewModel::class.java)
        }

        @JvmStatic
        @Provides
        internal fun provideMapViewModel(
                fragment: MapFragment,
                factory: ViewModelProvider.Factory): MapViewModel {
            return ViewModelProviders.of(fragment, factory).get(MapViewModel::class.java)
        }

        @JvmStatic
        @Provides
        internal fun provideSectionsAdapter(venuesMapFragment: MapFragment): SectionsAdapter {
            val sectionProvider = SectionProvider()
            val seCheckedItems = CheckedItem.wrap(sectionProvider.sections)
            return SectionsAdapter(seCheckedItems, venuesMapFragment)
        }
    }
}
