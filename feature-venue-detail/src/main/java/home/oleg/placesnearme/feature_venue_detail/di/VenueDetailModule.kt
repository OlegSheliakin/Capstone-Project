package home.oleg.placesnearme.feature_venue_detail.di

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import home.oleg.placesnearme.coredi.mapkeys.ViewModelKey
import home.oleg.placesnearme.corettools.error_handler.ErrorHandler
import home.oleg.placesnearme.corettools.logger.Logger
import home.oleg.placesnearme.feature_add_favorite.presentation.CreateFavoriteViewModelDelegate
import home.oleg.placesnearme.feature_add_history.presentation.viewmodel.CheckInViewModelDelegate
import home.oleg.placesnearme.feature_venue_detail.domain.GetDetailedVenue
import home.oleg.placesnearme.feature_venue_detail.presentation.LocalVenueViewModel
import home.oleg.placesnearme.feature_venue_detail.presentation.VenueViewModel
import home.oleg.placesnearme.feature_venue_detail.presentation.ui.VenueFragment

@Module
object VenueDetailModule {

    @JvmStatic
    @IntoMap
    @ViewModelKey(VenueViewModel::class)
    @Provides
    internal fun provideViewModel(errorHandler: ErrorHandler,
                                  createFavoriteViewModelDelegate: CreateFavoriteViewModelDelegate,
                                  checkInViewModelDelegate: CheckInViewModelDelegate,
                                  getDetailedVenue: GetDetailedVenue): ViewModel {
        return VenueViewModel(createFavoriteViewModelDelegate,
                checkInViewModelDelegate,
                errorHandler,
                getDetailedVenue)
    }

    @JvmStatic
    @IntoMap
    @ViewModelKey(LocalVenueViewModel::class)
    @Provides
    internal fun provideLocalVenueViewModel_(logger: Logger,
                                             createFavoriteViewModelDelegate: CreateFavoriteViewModelDelegate,
                                             checkInViewModelDelegate: CheckInViewModelDelegate,
                                             getDetailedVenue: GetDetailedVenue): ViewModel {
        return LocalVenueViewModel(createFavoriteViewModelDelegate,
                checkInViewModelDelegate,
                logger,
                getDetailedVenue)
    }

    @JvmStatic
    @Provides
    fun provideVenueViewModel(
            fragment: VenueFragment,
            factory: ViewModelProvider.Factory): VenueViewModel {
        return ViewModelProviders.of(fragment, factory).get(VenueViewModel::class.java)
    }

    @JvmStatic
    @Provides
    fun provideLocalVenueViewModel(
            fragment: VenueFragment,
            factory: ViewModelProvider.Factory): LocalVenueViewModel {
        return ViewModelProviders.of(fragment, factory).get(LocalVenueViewModel::class.java)
    }


    @JvmStatic
    @Provides
    fun provideActivity(fragment: VenueFragment): Activity = fragment.activity!!

    @JvmStatic
    @Provides
    fun provideLifecycleOwner(fragment: VenueFragment): LifecycleOwner = fragment.viewLifecycleOwner

}
