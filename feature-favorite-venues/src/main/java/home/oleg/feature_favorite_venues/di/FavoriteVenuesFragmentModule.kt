package home.oleg.feature_favorite_venues.di

import com.smedialink.feature_add_favorite.presentation.CreateFavoriteViewModelDelegate

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import home.oleg.feature_favorite_venues.presentation.ui.FavoritePlacesFragment
import home.oleg.feature_favorite_venues.presentation.FavoritePlacesViewModel
import home.oleg.feature_favorite_venues.presentation.ui.FavoriteVenuesAdapter
import home.oleg.placesnearme.core_presentation.extensions.viewModel
import home.oleg.placesnearme.core_presentation.recyclerview.ItemsDiffCallback

@Module
object FavoriteVenuesFragmentModule {

    @JvmStatic
    @Provides
    internal fun provideFavoriteVenuesAdapter(
            fragment: FavoritePlacesFragment): FavoriteVenuesAdapter {
        return FavoriteVenuesAdapter(ItemsDiffCallback.ITEMS_DIFF_CALLBACK, fragment)
    }

    @JvmStatic
    @Provides
    internal fun provideFavoritePlacesViewModel(
            fragment: FavoritePlacesFragment,
            factory: ViewModelProvider.Factory): FavoritePlacesViewModel = fragment.viewModel(factory)

}
