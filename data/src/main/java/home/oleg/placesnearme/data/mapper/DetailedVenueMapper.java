package home.oleg.placesnearme.data.mapper;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placenearme.models.Contact;
import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.models.Photo;
import home.oleg.placesnearme.data.model.DetailedVenueDbEntity;
import home.oleg.placesnearme.data.model.DetailedVenueWithPhotos;

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class DetailedVenueMapper {

    private DetailedVenueMapper() {
    }

    public static DetailedVenue map(home.oleg.placesnearme.network.models.DetailedVenue detailedVenue) {
        DetailedVenue result = new DetailedVenue();

        if (detailedVenue.getCategories() != null && !detailedVenue.getCategories().isEmpty()) {
            result.setCategory(CategoryMapper.map(detailedVenue.getCategories().get(0)));
        }

        result.setDescription(detailedVenue.getDescription());
        result.setRating(detailedVenue.getRating());
        result.setPhotos(PhotoMapper.map(detailedVenue.getPhotos()));
        result.setLocation(LocationMapper.map(detailedVenue.getLocation()));
        result.setId(detailedVenue.getId());
        result.setName(detailedVenue.getName());
        result.setHours(HoursMapper.map(detailedVenue.getHours()));

        if (detailedVenue.getContact() != null) {
            Contact contact = ContactMapper.map(detailedVenue.getContact());
            result.setContact(contact);
        }

        return result;
    }

    public static DetailedVenueWithPhotos map(DetailedVenue venue) {
        DetailedVenueWithPhotos detailedVenueWithPhotos = new DetailedVenueWithPhotos();

        DetailedVenueDbEntity detailedVenueDbEntity = new DetailedVenueDbEntity();
        detailedVenueDbEntity.setId(venue.getId());
        detailedVenueDbEntity.setTitle(venue.getName());
        detailedVenueDbEntity.setRating(venue.getRating());
        detailedVenueDbEntity.setContact(venue.getContact());

        detailedVenueDbEntity.setCategory(venue.getCategory());
        detailedVenueDbEntity.setLocation(venue.getLocation());
        detailedVenueDbEntity.setDescription(venue.getDescription());
        detailedVenueDbEntity.setFavorite(venue.isFavorite());

        detailedVenueWithPhotos.setVenue(detailedVenueDbEntity);
        detailedVenueWithPhotos.setPhotos(PhotoMapper.mapToDb(venue.getPhotos()));

        return detailedVenueWithPhotos;
    }

    public static List<DetailedVenue> map(List<DetailedVenueWithPhotos> detailedVenueWithPhotos) {
        List<DetailedVenue> detailedVenues = new ArrayList<>();

        for (DetailedVenueWithPhotos detailedVenueWithPhoto : detailedVenueWithPhotos) {
            detailedVenues.add(map(detailedVenueWithPhoto));
        }

        return detailedVenues;
    }

    public static DetailedVenue map(DetailedVenueWithPhotos detailedVenueWithPhotos) {
        DetailedVenueDbEntity venueDbEntity = detailedVenueWithPhotos.getVenue();

        DetailedVenue detailedVenue = new DetailedVenue();
        detailedVenue.setId(venueDbEntity.getId());
        detailedVenue.setName(venueDbEntity.getTitle());
        detailedVenue.setRating(venueDbEntity.getRating());

        detailedVenue.setContact(venueDbEntity.getContact());
        detailedVenue.setLocation(detailedVenue.getLocation());

        detailedVenue.setDescription(venueDbEntity.getDescription());
        detailedVenue.setFavorite(venueDbEntity.isFavorite());

        List<Photo> photoList = PhotoMapper.mapFromDb(detailedVenueWithPhotos.getPhotos());
        detailedVenue.setPhotos(photoList);

        return detailedVenue;
    }
}
