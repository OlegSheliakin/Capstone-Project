package home.oleg.placesnearme.feature_venues_history.di;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.util.DiffUtil;

import com.smedialink.feature_add_favorite.CreateFavoriteViewModel;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.core_presentation.recyclerview.ItemViewType;
import home.oleg.placesnearme.core_presentation.recyclerview.VenuesDiffCallback;
import home.oleg.placesnearme.feature_venues_history.HistoryVenuesAdapter;
import home.oleg.placesnearme.feature_venues_history.VenuesHistoryViewModel;
import home.oleg.placesnearme.feature_venues_history.VenuesHistoryFragment;
import io.reactivex.annotations.NonNull;

@Module
public abstract class VenuesHistoryModule {

    @Provides
    @NonNull
    static HistoryVenuesAdapter provideFavoriteVenuesAdapter(
            VenuesHistoryFragment fragment) {
        return new HistoryVenuesAdapter(VenuesDiffCallback.VENUES_DIFF_CALLBACK, fragment);
    }

    @Provides
    @NonNull
    static VenuesHistoryViewModel provideFavoritePlacesViewModel(
            VenuesHistoryFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(VenuesHistoryViewModel.class);
    }

    @Provides
    @NonNull
    static CreateFavoriteViewModel provideCreateFavoriteViewModel(
            VenuesHistoryFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(CreateFavoriteViewModel.class);
    }

}
