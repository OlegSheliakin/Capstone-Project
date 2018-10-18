package home.oleg.placesnearme.data;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placenearme.models.Contact;
import home.oleg.placenearme.models.Location;
import home.oleg.placenearme.models.Photo;
import home.oleg.placesnearme.data.model.DetailedVenueDbEntity;
import home.oleg.placesnearme.data.model.PhotoDbEntity;

public class FakesStore {

    public static DetailedVenueDbEntity getVenue(String id) {
        DetailedVenueDbEntity detailedVenue = new DetailedVenueDbEntity();
        detailedVenue.setId(id);

        Contact contact = new Contact();
        contact.setFormattedPhone("00000");
        detailedVenue.setContact(contact);

        Location location = new Location();
        location.setLng(45.);
        location.setLng(45.);
        detailedVenue.setLocation(location);

        detailedVenue.setDescription("test");
        detailedVenue.setRating(5);
        detailedVenue.setTitle("test");
        return detailedVenue;
    }

    public static List<PhotoDbEntity> getListPhotos() {
        List<PhotoDbEntity> photoDbEntities = new ArrayList<>();
        PhotoDbEntity photoDbEntity = new PhotoDbEntity();

        Photo photo = new Photo();
        photo.setHeight(200L);
        photo.setWidth(200L);
        photo.setPrefix("test");
        photo.setSuffix("test");
        photoDbEntity.setPhoto(photo);

        photoDbEntities.add(photoDbEntity);
        return photoDbEntities;
    }

}
