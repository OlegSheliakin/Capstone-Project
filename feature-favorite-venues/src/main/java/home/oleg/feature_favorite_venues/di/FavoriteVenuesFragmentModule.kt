package home.oleg.feature_favorite_venues.di

import com.smedialink.feature_add_favorite.CreateFavoriteViewModel

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import home.oleg.feature_favorite_venues.FavoritePlacesFragment
import home.oleg.feature_favorite_venues.FavoritePlacesViewModel
import home.oleg.feature_favorite_venues.FavoriteVenuesAdapter
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

    @JvmStatic
    @Provides
    internal fun provideCreateFavoriteViewModel(
            fragment: FavoritePlacesFragment,
            factory: ViewModelProvider.Factory): CreateFavoriteViewModel = fragment.viewModel(factory)
}
