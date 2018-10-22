package home.oleg.placesnearme.core_presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placenearme.models.Section;
import home.oleg.placenearme.models.Venue;
import home.oleg.placesnearme.core_presentation.viewdata.IconViewData;
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData;

public final class VenueMapViewMapper {

    private VenueMapViewMapper() {
    }

    public static PreviewVenueViewData map(Section section, Venue venue) {
        PreviewVenueViewData venueMapView = new PreviewVenueViewData();
        venueMapView.setId(venue.getId());
        venueMapView.setTitle(venue.getName());
        venueMapView.setAddress(venue.getLocation().getAddress());
        venueMapView.setLat(venue.getLocation().getLat());
        venueMapView.setLng(venue.getLocation().getLng());
        venueMapView.setSectionType(section);
        venueMapView.setDistance(venue.getDistance());

        if (venue.getCategory() != null) {
            IconViewData icon = IconViewMapper.map(
                    venue.getCategory().getIconPrefix(),
                    venue.getCategory().getIconSuffix());
            venueMapView.setIconViewData(icon);
            venueMapView.setCategoryName(venue.getCategory().getName());
        }

        return venueMapView;
    }

    public static List<PreviewVenueViewData> map(Section section, List<Venue> venues) {
        List<PreviewVenueViewData> venueMapViewDataList = new ArrayList<>();

        for (Venue venue : venues) {
            venueMapViewDataList.add(map(section, venue));
        }

        return venueMapViewDataList;
    }
}
