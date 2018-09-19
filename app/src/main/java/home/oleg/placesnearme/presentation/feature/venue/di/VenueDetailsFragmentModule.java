package home.oleg.placesnearme.presentation.feature.venue.di;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.widget.NestedScrollView;

import com.mahc.custombottomsheetbehavior.BottomSheetBehaviorGoogleMapsLike;
import com.smedialink.common.Optional;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.presentation.feature.map.view.VenuesMapFragment;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.UserLocationViewModel;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.VenuesViewModel;
import home.oleg.placesnearme.presentation.feature.venue.view.VenueDetailsFragment;
import home.oleg.placesnearme.presentation.feature.venue.view.VenueViewDelegate;
import home.oleg.placesnearme.presentation.feature.venue.viewmodel.VenueViewModel;
import io.reactivex.annotations.NonNull;

@Module
public final class VenueDetailsFragmentModule {

    @Provides
    @NonNull
    static VenueViewModel provideMapViewModel(
            VenuesMapFragment fragment,
            ViewModelProvider.Factory factory, VenuesViewModel venuesViewModel) {
        VenueViewModel venuesViewModel1 = ViewModelProviders.of(fragment, factory).get(VenueViewModel.class);
        venuesViewModel1.attachSelector(venuesViewModel);
        return venuesViewModel1;
    }

}
