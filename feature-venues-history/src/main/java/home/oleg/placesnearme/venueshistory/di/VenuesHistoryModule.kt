package home.oleg.placesnearme.venueshistory.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.smedialink.common.recyclerview.ItemsDiffCallback
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import home.oleg.placesnearme.coredi.mapkeys.ViewModelKey
import home.oleg.placesnearme.feature_add_history.presentation.viewmodel.CheckInViewModelDelegate
import home.oleg.placesnearme.venueshistory.domain.interactor.ObserveHistory
import home.oleg.placesnearme.venueshistory.presentation.VenuesHistoryViewModel
import home.oleg.placesnearme.venueshistory.presentation.ui.HistoryVenuesAdapter
import home.oleg.placesnearme.venueshistory.presentation.ui.VenuesHistoryFragment

@Module
object VenuesHistoryModule {

    @JvmStatic
    @Provides
    internal fun provideFavoriteVenuesAdapter(
            fragment: VenuesHistoryFragment): HistoryVenuesAdapter {
        return HistoryVenuesAdapter(ItemsDiffCallback.ITEMS_DIFF_CALLBACK, fragment)
    }

    @JvmStatic
    @IntoMap
    @ViewModelKey(VenuesHistoryViewModel::class)
    @Provides
    internal fun provideViewModel(
            checkInViewModelDelegate: CheckInViewModelDelegate,
            observeHistory: ObserveHistory): ViewModel {
        return VenuesHistoryViewModel(checkInViewModelDelegate, observeHistory)
    }

    @JvmStatic
    @Provides
    internal fun provideFavoritePlacesViewModel(
            fragment: VenuesHistoryFragment,
            factory: ViewModelProvider.Factory): VenuesHistoryViewModel {
        return ViewModelProviders.of(fragment, factory).get(VenuesHistoryViewModel::class.java)
    }

}
