package home.oleg.feature_favorite_venues;

import java.util.List;

import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem;
import home.oleg.placesnearme.core_presentation.base.DataView;

public interface FavoriteView extends DataView<List<VenueViewItem>> {
    void showEmpty();
}
