package home.oleg.feature_favorite_venues.di

import com.smedialink.feature_add_favorite.CreateFavoriteViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import home.oleg.feature_favorite_venues.FavoritePlacesFragment
import home.oleg.feature_favorite_venues.FavoritePlacesViewModel
import home.oleg.feature_favorite_venues.FavoriteVenuesAdapter
import home.oleg.placesnearme.core_presentation.recyclerview.VenuesDiffCallback
import io.reactivex.annotations.NonNull

@Module
class FavoriteVenuesFragmentModule {

    @Provides
    internal fun provideFavoriteVenuesAdapter(
            fragment: FavoritePlacesFragment): FavoriteVenuesAdapter {
        return FavoriteVenuesAdapter(VenuesDiffCallback.VENUES_DIFF_CALLBACK, fragment)
    }

    @Provides
    internal fun provideFavoritePlacesViewModel(
            fragment: FavoritePlacesFragment,
            factory: ViewModelProvider.Factory): FavoritePlacesViewModel {
        return ViewModelProviders.of(fragment, factory).get(FavoritePlacesViewModel::class.java)
    }

    @Provides
    internal fun provideCreateFavoriteViewModel(
            fragment: FavoritePlacesFragment,
            factory: ViewModelProvider.Factory): CreateFavoriteViewModel {
        return ViewModelProviders.of(fragment, factory).get(CreateFavoriteViewModel::class.java)
    }
}
