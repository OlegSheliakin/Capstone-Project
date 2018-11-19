package home.oleg.placesnearme.feature_favorite_venues.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smedialink.common.ext.viewModel
import com.smedialink.common.recyclerview.ItemsDiffCallback
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import home.oleg.placesnearme.coredi.mapkeys.ViewModelKey
import home.oleg.placesnearme.coredomain.repositories.FavoriteVenuesRepository
import home.oleg.placesnearme.feature_add_favorite.presentation.CreateFavoriteViewModelDelegate
import home.oleg.placesnearme.feature_favorite_venues.presentation.FavoritePlacesViewModel
import home.oleg.placesnearme.feature_favorite_venues.presentation.ui.FavoritePlacesFragment
import home.oleg.placesnearme.feature_favorite_venues.presentation.ui.FavoriteVenuesAdapter

@Module
object FavoriteVenuesFragmentModule {

    @JvmStatic
    @Provides
    internal fun provideFavoriteVenuesAdapter(
            fragment: FavoritePlacesFragment): FavoriteVenuesAdapter {
        return FavoriteVenuesAdapter(ItemsDiffCallback.ITEMS_DIFF_CALLBACK, fragment)
    }

    @Provides
    @JvmStatic
    @IntoMap
    @ViewModelKey(FavoritePlacesViewModel::class)
    internal fun provideViewModel(
            createFavoriteViewModelDelegate: CreateFavoriteViewModelDelegate,
            favoriteVenuesRepository: FavoriteVenuesRepository
    ): ViewModel {
        return FavoritePlacesViewModel(createFavoriteViewModelDelegate, favoriteVenuesRepository)
    }

    @JvmStatic
    @Provides
    internal fun provideFavoritePlacesViewModel(
            fragment: FavoritePlacesFragment,
            factory: ViewModelProvider.Factory): FavoritePlacesViewModel = fragment.viewModel(factory)

}
