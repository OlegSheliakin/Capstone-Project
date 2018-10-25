package home.oleg.placesnearme.feature_map.di;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import com.smedialink.feature_add_favorite.CreateFavoriteViewModel;
import com.smedialink.feature_venue_detail.venue.viewmodel.VenueViewModel;

import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import home.oleg.feature_add_history.CheckInViewModel;
import home.oleg.placesnearme.feature_map.adapter.CheckedItem;
import home.oleg.placesnearme.feature_map.adapter.SectionsAdapter;
import home.oleg.placesnearme.feature_map.drawable_converter.DrawableConverter;
import home.oleg.placesnearme.feature_map.drawable_converter.DrawableConverterImpl;
import home.oleg.placesnearme.feature_map.sections.SectionProvider;
import home.oleg.placesnearme.feature_map.view.VenuesMapFragment;
import home.oleg.placesnearme.feature_map.viewdata.SectionViewData;
import home.oleg.placesnearme.feature_map.viewmodel.UserLocationViewModel;
import home.oleg.placesnearme.feature_map.viewmodel.VenuesViewModel;
import io.reactivex.annotations.NonNull;

@Module
public abstract class VenuesMapFragmentModule {

    @Provides
    @NonNull
    static Activity provideActivity(VenuesMapFragment fragment) {
        return fragment.getActivity();
    }

    @Provides
    @NonNull
    static CheckInViewModel provideCheckInViewModel(
            VenuesMapFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(CheckInViewModel.class);
    }

    @Provides
    @NonNull
    static CreateFavoriteViewModel provideCreateFavoriteViewModel(
            VenuesMapFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(CreateFavoriteViewModel.class);
    }

    @Provides
    @NonNull
    static VenueViewModel provideVenueViewModel(
            VenuesMapFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(VenueViewModel.class);
    }

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

    @Provides
    @NonNull
    static LifecycleOwner provideLifeCycleOwner(VenuesMapFragment mapFragment) {
        return mapFragment;
    }

    @Binds
    @NonNull
    abstract DrawableConverter provideConverter(DrawableConverterImpl impl);

    @Provides
    public static SectionsAdapter provideSectionsAdapter(VenuesMapFragment venuesMapFragment) {
        SectionProvider sectionProvider = new SectionProvider();
        List<CheckedItem<SectionViewData>> seCheckedItems = CheckedItem.wrap(sectionProvider.getSections());
        return new SectionsAdapter(seCheckedItems, venuesMapFragment);
    }
}
