package home.oleg.feature_favorite_venues.di;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import com.smedialink.feature_add_favorite.CreateFavoriteViewModel;

import dagger.Module;
import dagger.Provides;
import home.oleg.feature_favorite_venues.FavoritePlacesFragment;
import home.oleg.feature_favorite_venues.FavoritePlacesViewModel;
import home.oleg.feature_favorite_venues.FavoriteVenuesAdapter;
import home.oleg.placesnearme.core_presentation.recyclerview.VenuesDiffCallback;
import io.reactivex.annotations.NonNull;

@Module
public abstract class FavoriteVenuesFragmentModule {

    @Provides
    @NonNull
    static FavoriteVenuesAdapter provideFavoriteVenuesAdapter(
            FavoritePlacesFragment fragment) {
        return new FavoriteVenuesAdapter(VenuesDiffCallback.VENUES_DIFF_CALLBACK, fragment);
    }

    @Provides
    @NonNull
    static FavoritePlacesViewModel provideFavoritePlacesViewModel(
            FavoritePlacesFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(FavoritePlacesViewModel.class);
    }

    @Provides
    @NonNull
    static CreateFavoriteViewModel provideCreateFavoriteViewModel(
            FavoritePlacesFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(CreateFavoriteViewModel.class);
    }
}
