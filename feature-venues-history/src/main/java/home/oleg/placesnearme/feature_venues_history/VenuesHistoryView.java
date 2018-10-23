package home.oleg.placesnearme.feature_venues_history;

import java.util.List;

import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem;
import home.oleg.placesnearme.core_presentation.base.View;

/**
 * Created by Oleg Sheliakin on 23.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public interface VenuesHistoryView extends View {
    void showData(List<VenueViewItem> venueViewItemList);

    void showEmpty();
}
