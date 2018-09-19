package home.oleg.placesnearme.presentation.view_action;

import android.support.annotation.NonNull;

import com.smedialink.common.Optional;
import com.smedialink.common.function.Action;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placesnearme.presentation.feature.map.view.VenuesView;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;

/**
 * Created by Oleg Sheliakin on 21.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class ShowVenuesAction<VIEW extends VenuesView> implements Action<VIEW> {

    private final List<VenueViewData> data;

    private ShowVenuesAction(List<VenueViewData> data) {
        this.data = new ArrayList<>(data);
    }

    public static <VIEW extends VenuesView> ShowVenuesAction<VIEW> create(List<VenueViewData> data) {
        return new ShowVenuesAction<>(data);
    }

    @Override
    public void perform(@NonNull VIEW view) {
        Optional.of(data).ifPresent(view::showVenues);
    }
}
