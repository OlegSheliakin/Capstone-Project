package home.oleg.placesnearme.mapper;


import home.oleg.placenearme.models.Contact;
import home.oleg.placenearme.models.DetailedVenue;

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class DetailedVenueMapper {

    private DetailedVenueMapper() {
    }

    public static DetailedVenue map(home.oleg.placesnearme.network.models.DetailedVenue detailedVenue) {
        DetailedVenue result = new DetailedVenue();
        result.setCategories(CategoryMapper.map(detailedVenue.getCategories()));
        result.setBestPhoto(PhotoMapper.map(detailedVenue.getBestPhoto()));
        result.setDescription(detailedVenue.getDescription());
        result.setRating(detailedVenue.getRating());
        result.setPhotos(PhotoMapper.map(detailedVenue.getPhotos()));
        result.setLocation(LocationMapper.map(detailedVenue.getLocation()));
        result.setId(detailedVenue.getId());
        result.setName(detailedVenue.getName());
        result.setHours(HoursMapper.map(detailedVenue.getHours()));

        if(detailedVenue.getContact() != null) {
            Contact contact = ContactMapper.map(detailedVenue.getContact());
            result.setContact(contact);
        }

        return result;
    }

}
