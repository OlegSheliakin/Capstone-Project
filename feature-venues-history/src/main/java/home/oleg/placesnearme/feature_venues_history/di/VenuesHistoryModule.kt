package home.oleg.placesnearme.feature_venues_history.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.smedialink.common.recyclerview.ItemsDiffCallback
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import home.oleg.placesnearme.coredi.mapkeys.ViewModelKey
import home.oleg.placesnearme.feature_add_favorite.presentation.CreateFavoriteViewModelDelegate
import home.oleg.placesnearme.feature_venues_history.domain.interactor.ObserveHistory
import home.oleg.placesnearme.feature_venues_history.presentation.VenuesHistoryViewModel
import home.oleg.placesnearme.feature_venues_history.presentation.ui.HistoryVenuesAdapter
import home.oleg.placesnearme.feature_venues_history.presentation.ui.VenuesHistoryFragment

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
            createFavoriteViewModelDelegate: CreateFavoriteViewModelDelegate,
            observeHistory: ObserveHistory): ViewModel {
        return VenuesHistoryViewModel(createFavoriteViewModelDelegate, observeHistory)
    }

    @JvmStatic
    @Provides
    internal fun provideFavoritePlacesViewModel(
            fragment: VenuesHistoryFragment,
            factory: ViewModelProvider.Factory): VenuesHistoryViewModel {
        return ViewModelProviders.of(fragment, factory).get(VenuesHistoryViewModel::class.java)
    }

}
