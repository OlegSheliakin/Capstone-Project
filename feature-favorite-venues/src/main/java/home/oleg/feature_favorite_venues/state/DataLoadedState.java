package home.oleg.feature_favorite_venues.state;

import java.util.List;

import home.oleg.feature_favorite_venues.FavoriteView;
import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem;

public class DataLoadedState implements State<FavoriteView> {

    private final List<VenueViewItem> venueViewItemList;

    public DataLoadedState(List<VenueViewItem> venueViewItemList) {
        this.venueViewItemList = venueViewItemList;
    }

    @Override
    public void apply(FavoriteView favoriteView) {
        if (venueViewItemList.isEmpty()) {
            favoriteView.showEmpty();
        } else {
            favoriteView.showData(venueViewItemList);
        }
    }
}
