package home.oleg.placesnearme.mapper;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placenearme.models.Category;
import home.oleg.placenearme.models.Location;
import home.oleg.placenearme.models.Venue;

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class VenueMapper {

    private VenueMapper() {
    }

    public static Venue map(home.oleg.placesnearme.core_network.models.Venue venue) {
        Venue result = new Venue();

        List<Category> categories = new ArrayList<>();
        for (home.oleg.placesnearme.core_network.models.Category category : venue.getCategories()) {
            categories.add(CategoryMapper.map(category));
        }

        result.setCategories(categories);
        result.setId(venue.getId());

        Location location = new Location();
        location.setAddress(venue.getLocation().getAddress());
        location.setCc(venue.getLocation().getCc());
        location.setCity(venue.getLocation().getCity());
        location.setCountry(venue.getLocation().getCountry());
        location.setCrossStreet(venue.getLocation().getCrossStreet());
        location.setFormattedAddress(venue.getLocation().getFormattedAddress());
        location.setLat(venue.getLocation().getLat());
        location.setLng(venue.getLocation().getLng());

        result.setLocation(location);
        result.setName(venue.getName());

        return result;
    }

    public static List<Venue> map(List<home.oleg.placesnearme.core_network.models.Venue> venues) {
        List<Venue> result = new ArrayList<>();

        for (home.oleg.placesnearme.core_network.models.Venue venue : venues) {
            result.add(map(venue));
        }

        return result;
    }

}
