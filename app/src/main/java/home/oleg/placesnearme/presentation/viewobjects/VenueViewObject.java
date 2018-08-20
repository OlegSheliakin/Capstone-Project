package home.oleg.placesnearme.presentation.viewobjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.models.Photo;

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewObject {

    private String title;
    private String address;
    private double lat;
    private double lng;
    private List<String> photoUrls;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public static List<VenueViewObject> mapFrom(Collection<DetailedVenue> venues) {
        if (venues.isEmpty()) {
            return Collections.emptyList();
        }

        List<VenueViewObject> list = new ArrayList<>();
        for (DetailedVenue venue : venues) {
            list.add(VenueViewObject.mapFrom(venue));
        }
        return list;
    }

    public static VenueViewObject mapFrom(DetailedVenue venue) {
        VenueViewObject venueViewObject = new VenueViewObject();
        venueViewObject.setTitle(venue.getName());
        venueViewObject.setAddress(venue.getLocation().getAddress());
        venueViewObject.setLat(venue.getLocation().getLat());
        venueViewObject.setLng(venue.getLocation().getLng());

        List<Photo> photos = venue.getPhotos();

        List<String> photoUrls = new ArrayList<>();
        for (Photo photo : photos) {
            photoUrls.add(photo.getImageUrl());
        }
        venueViewObject.setPhotoUrls(photoUrls);

        return venueViewObject;
    }
}
