package home.oleg.placesnearme.feature_place_detail.di

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import home.oleg.placesnearme.coredi.mapkeys.ViewModelKey
import home.oleg.placesnearme.coredomain.repositories.DetailedVenueRepository
import home.oleg.placesnearme.corettools.error_handler.ErrorHandler
import home.oleg.placesnearme.feature_add_favorite.presentation.CreateFavoriteViewModelDelegate
import home.oleg.placesnearme.feature_add_history.presentation.viewmodel.CheckInViewModelDelegate
import home.oleg.placesnearme.feature_place_detail.domain.GetDetailedVenue
import home.oleg.placesnearme.feature_place_detail.presentation.VenueViewModel
import home.oleg.placesnearme.feature_place_detail.presentation.ui.VenueFragment

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
        return VenueViewModel(
                createFavoriteViewModelDelegate,
                checkInViewModelDelegate,
                errorHandler,
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
    fun provideActivity(fragment: VenueFragment): Activity = fragment.activity!!

    @JvmStatic
    @Provides
    fun provideLifecycleOwner(fragment: VenueFragment): LifecycleOwner = fragment.viewLifecycleOwner

}
