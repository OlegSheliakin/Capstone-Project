package home.oleg.placesnearme.feature_map.view_action;

import android.support.annotation.NonNull;

import com.smedialink.common.Optional;
import com.smedialink.common.function.Action;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData;
import home.oleg.placesnearme.feature_map.view.VenuesView;

/**
 * Created by Oleg Sheliakin on 21.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class ShowVenuesAction<VIEW extends VenuesView> implements Action<VIEW> {

    private final List<PreviewVenueViewData> data;

    private ShowVenuesAction(List<PreviewVenueViewData> data) {
        this.data = new ArrayList<>(data);
    }

    public static <VIEW extends VenuesView> ShowVenuesAction<VIEW> create(List<PreviewVenueViewData> data) {
        return new ShowVenuesAction<>(data);
    }

    @Override
    public void perform(@NonNull VIEW view) {
        Optional.of(data).ifPresent(view::show);
    }
}
