package home.oleg.placesnearme.feature_venues_history.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.smedialink.feature_add_favorite.CreateFavoriteViewModel
import dagger.Module
import dagger.Provides
import home.oleg.placesnearme.core_presentation.recyclerview.ItemsDiffCallback
import home.oleg.placesnearme.feature_venues_history.HistoryVenuesAdapter
import home.oleg.placesnearme.feature_venues_history.VenuesHistoryFragment
import home.oleg.placesnearme.feature_venues_history.VenuesHistoryViewModel

@Module
class VenuesHistoryModule {

    @Provides
    fun provideFavoriteVenuesAdapter(
            fragment: VenuesHistoryFragment): HistoryVenuesAdapter {
        return HistoryVenuesAdapter(ItemsDiffCallback.ITEMS_DIFF_CALLBACK, fragment)
    }

    @Provides
    fun provideFavoritePlacesViewModel(
            fragment: VenuesHistoryFragment,
            factory: ViewModelProvider.Factory): VenuesHistoryViewModel {
        return ViewModelProviders.of(fragment, factory).get(VenuesHistoryViewModel::class.java)
    }

    @Provides
    fun provideCreateFavoriteViewModel(
            fragment: VenuesHistoryFragment,
            factory: ViewModelProvider.Factory): CreateFavoriteViewModel {
        return ViewModelProviders.of(fragment, factory).get(CreateFavoriteViewModel::class.java)
    }
}
