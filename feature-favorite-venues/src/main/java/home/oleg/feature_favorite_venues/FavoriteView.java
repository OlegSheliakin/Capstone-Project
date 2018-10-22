package home.oleg.feature_favorite_venues;

import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placesnearme.core_presentation.base.DataView;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

public interface FavoriteView extends DataView<List<VenueViewItem>> {
    void showEmpty();
}
