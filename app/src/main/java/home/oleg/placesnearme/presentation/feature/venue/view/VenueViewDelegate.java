package home.oleg.placesnearme.presentation.feature.venue.view;

import android.view.View;

import javax.inject.Inject;

import dagger.Lazy;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;
import io.reactivex.annotations.NonNull;

/**
 * Created by Oleg Sheliakin on 19.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewDelegate implements VenueView {

    private final Lazy<VenueDetailsFragment> venueDetailsFragmentProvider;
    private final Lazy<VenueViewDelegate.ShowHandler> showHandlerProvider;

    private VenueDetailsFragment fragment;
    private VenueViewDelegate.ShowHandler showHandler;

    public interface ShowHandler {
        boolean isShown();

        void show();
    }

    @Inject
    public VenueViewDelegate(Lazy<VenueDetailsFragment> venueDetailsFragmentProvider,
                             Lazy<VenueViewDelegate.ShowHandler> showHnalderProvider) {
        this.venueDetailsFragmentProvider = venueDetailsFragmentProvider;
        this.showHandlerProvider = showHnalderProvider;
    }

    public void onViewCreated(@NonNull View view) {
        fragment = venueDetailsFragmentProvider.get();
        showHandler = showHandlerProvider.get();
    }

    @Override
    public void showVenue(VenueViewData venueViewData) {
        if (showHandler == null || fragment == null) {
            return;
        }

        if (!showHandler.isShown()) {
            showHandler.show();
        }

        fragment.showVenue(venueViewData);
    }

}
