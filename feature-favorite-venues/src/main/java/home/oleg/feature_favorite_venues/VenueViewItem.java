package home.oleg.feature_favorite_venues;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

public class VenueViewItem implements ItemViewType {

    public static int VIEW_TYPE = 1;

    private final VenueViewData venueViewData;

    public VenueViewItem(VenueViewData venueViewData) {
        this.venueViewData = venueViewData;
    }

    public VenueViewData getVenueViewData() {
        return venueViewData;
    }

    @Override
    public int getViewType() {
        return VIEW_TYPE;
    }

    public static List<VenueViewItem> map(List<VenueViewData> venueViewDataList) {
        List<VenueViewItem> venueViewItems = new ArrayList<>();

        for (VenueViewData venueViewData : venueViewDataList) {
            venueViewItems.add(new VenueViewItem(venueViewData));
        }

        return venueViewItems;
    }


}
