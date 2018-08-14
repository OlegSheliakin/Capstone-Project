package home.oleg.placesnearme.presentation.viewobjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import home.oleg.placenearme.models.Venue;

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewObject {

    private String title;
    private String addres;
    private String thumbUrl;
    private double lat;
    private double lng;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public static List<VenueViewObject> mapFrom(Collection<Venue> venues) {
        if (venues.isEmpty()) {
            return Collections.emptyList();
        }

        List<VenueViewObject> list = new ArrayList<>();
        for (Venue venue : venues) {
            list.add(VenueViewObject.mapFrom(venue));
        }
        return list;
    }

    public static VenueViewObject mapFrom(Venue venue) {
        VenueViewObject venueViewObject = new VenueViewObject();
        venueViewObject.setTitle(venue.getName());
        venueViewObject.setAddres(venue.getLocation().getAddress());
        venueViewObject.setLat(venue.getLocation().getLat());
        venueViewObject.setLng(venue.getLocation().getLng());

        /*List<FeaturedPhotos.ItemsPhoto> itemsPhotos = venue.getFeaturedPhotos().getItems();
        if (itemsPhotos != null && !itemsPhotos.isEmpty()) {
            FeaturedPhotos.ItemsPhoto itemsPhoto = itemsPhotos.get(0);
            if (itemsPhoto != null) {
                venueViewObject.setThumbUrl(itemsPhoto.getPhotoURL());
            }
        }*/

        return venueViewObject;
    }
}
