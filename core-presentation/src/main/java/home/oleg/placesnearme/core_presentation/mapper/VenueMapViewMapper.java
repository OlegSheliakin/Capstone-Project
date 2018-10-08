package home.oleg.placesnearme.core_presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placenearme.models.Section;
import home.oleg.placenearme.models.Venue;
import home.oleg.placesnearme.core_presentation.viewdata.VenueMapViewData;

public final class VenueMapViewMapper {

    public static VenueMapViewData map(Section section, Venue venue) {
        VenueMapViewData venueMapView = new VenueMapViewData();
        venueMapView.setId(venue.getId());
        venueMapView.setName(venue.getName());
        venueMapView.setAddress(venue.getLocation().getAddress());
        venueMapView.setLat(venue.getLocation().getLat());
        venueMapView.setLng(venue.getLocation().getLng());
        venueMapView.setSectionType(section);

        if(venue.getCategory() != null && venue.getCategory().getIcon() != null) {
            venueMapView.setIconViewData(IconViewMapper.map(venue.getCategory().getIcon()));
        }

        return venueMapView;
    }

    public static List<VenueMapViewData> map(Section section, List<Venue> venues) {
        List<VenueMapViewData> venueMapViewDataList = new ArrayList<>();

        for (Venue venue : venues) {
            venueMapViewDataList.add(map(section, venue));
        }

        return venueMapViewDataList;
    }
}
