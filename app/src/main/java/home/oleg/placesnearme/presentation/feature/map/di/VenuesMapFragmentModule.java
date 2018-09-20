package home.oleg.placesnearme.presentation.feature.map.di;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;

import com.mahc.custombottomsheetbehavior.BottomSheetBehaviorGoogleMapsLike;
import com.mahc.custombottomsheetbehavior.CustomBottomSheetBehavior;
import com.smedialink.common.Optional;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.presentation.feature.map.view.VenuesMapFragment;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.UserLocationViewModel;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.VenuesViewModel;
import home.oleg.placesnearme.presentation.feature.venue.view.VenueDetailsFragment;
import home.oleg.placesnearme.presentation.feature.venue.view.VenueViewDelegate;
import io.reactivex.annotations.NonNull;

@Module
public final class VenuesMapFragmentModule {

    @Provides
    @NonNull
    static VenuesViewModel provideMapViewModel(
            VenuesMapFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(VenuesViewModel.class);
    }

    @Provides
    @NonNull
    static UserLocationViewModel provideUserLocationViewModel(
            VenuesMapFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(UserLocationViewModel.class);
    }

  /*  @Provides
    @NonNull
    static VenueDetailsFragment provideVenueDetailFragment(VenuesMapFragment mapFragment) {
        return Optional.of(mapFragment.getChildFragmentManager())
                .map(fragmentManager ->
                        (VenueDetailsFragment) fragmentManager.findFragmentById(R.id.venueDetailFragment))
                .getOrElseThrow(new IllegalStateException("cannot find venue detail fragment"));
    }*/

    @Provides
    @NonNull
    static VenueViewDelegate.ShowHandler provideShowHandler(VenuesMapFragment mapFragment) {
        NestedScrollView nestedScrollView = mapFragment.getView().findViewById(R.id.nestedScrollView);

        CustomBottomSheetBehavior<NestedScrollView> bottomSheetBehaviorGoogleMapsLike = CustomBottomSheetBehavior.from(nestedScrollView);
        bottomSheetBehaviorGoogleMapsLike.setState(BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN);

        return new VenuesMapFragmentModule.MyShowHandler(bottomSheetBehaviorGoogleMapsLike);
    }

    private static final class MyShowHandler implements VenueViewDelegate.ShowHandler {
        private final CustomBottomSheetBehavior<NestedScrollView> bottomSheetBehaviorGoogleMapsLike;

        private MyShowHandler(CustomBottomSheetBehavior<NestedScrollView> bottomSheetBehaviorGoogleMapsLike) {
            this.bottomSheetBehaviorGoogleMapsLike = bottomSheetBehaviorGoogleMapsLike;
        }

        @Override
        public boolean isShown() {
            return bottomSheetBehaviorGoogleMapsLike.getState() != BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN;
        }

        @Override
        public void show() {
            bottomSheetBehaviorGoogleMapsLike.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
        }
    }
}
