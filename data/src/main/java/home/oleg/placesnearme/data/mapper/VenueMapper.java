package home.oleg.placesnearme.data.mapper;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placenearme.models.Location;
import home.oleg.placenearme.models.Venue;
import home.oleg.placesnearme.network.models.VenueDto;

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class VenueMapper {

    private VenueMapper() {
    }

    public static Venue map(VenueDto venue) {
        Venue result = new Venue();

        for (home.oleg.placesnearme.network.models.Category category : venue.getCategories()) {
            if (category.getPrimary()) {
                result.setCategory(CategoryMapper.INSTANCE.map(category));
            }
        }

        result.setId(venue.getId());

        Location location = LocationMapper.map(venue.getLocation());

        result.setLocation(location);
        result.setName(venue.getName());

        return result;
    }

    public static List<Venue> map(List<VenueDto> venues) {
        List<Venue> result = new ArrayList<>();

        for (VenueDto venue : venues) {
            result.add(map(venue));
        }

        return result;
    }

}
