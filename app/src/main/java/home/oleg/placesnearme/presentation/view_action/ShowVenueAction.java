package home.oleg.placesnearme.presentation.view_action;

import android.support.annotation.NonNull;

import com.smedialink.common.Optional;
import com.smedialink.common.function.Action;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placesnearme.presentation.feature.map.view.VenuesView;
import home.oleg.placesnearme.presentation.feature.venue.view.VenueView;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;

/**
 * Created by Oleg Sheliakin on 21.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class ShowVenueAction<VIEW extends VenueView> implements Action<VIEW> {

    private final VenueViewData data;

    private ShowVenueAction(VenueViewData data) {
        this.data = data;
    }

    public static <VIEW extends VenueView> ShowVenueAction<VIEW> create(VenueViewData data) {
        return new ShowVenueAction<>(data);
    }

    @Override
    public void perform(@NonNull VIEW view) {
        Optional.of(data).ifPresent(view::showVenue);
    }
}
