package home.oleg.placesnearme.core_presentation.recyclerview;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VenueViewItem that = (VenueViewItem) o;
        return Objects.equals(venueViewData, that.venueViewData);
    }

    @Override
    public int hashCode() {

        return Objects.hash(venueViewData);
    }
}
