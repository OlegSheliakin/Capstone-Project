package home.oleg.placesnearme.core_presentation.viewdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.models.Photo;

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewData {

    private String title;
    private String address;
    private double lat;
    private double lng;
    private List<PhotoViewData> photoUrls;
    private String description;
    private String openingHoursStatus;

    public List<PhotoViewData> getPhotoUrls() {
        return photoUrls;
    }

    public String getOpeningHoursStatus() {
        return openingHoursStatus;
    }

    public void setOpeningHoursStatus(String openingHoursStatus) {
        this.openingHoursStatus = openingHoursStatus;
    }

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

    public PhotoViewData getBestPhoto() {
        if (photoUrls != null && !photoUrls.isEmpty()) {
            return photoUrls.get(0);
        }

        return null;
    }

    public List<PhotoViewData> getPhotos() {
        if (photoUrls == null) {
            return Collections.emptyList();
        } else {
            return new ArrayList<>(photoUrls);
        }
    }

    public void setPhotoUrls(List<PhotoViewData> photoUrls) {
        this.photoUrls = new ArrayList<>(photoUrls);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<VenueViewData> mapFrom(List<DetailedVenue> detailedVenues) {
        if (detailedVenues.isEmpty()) {
            return Collections.emptyList();
        }

        List<VenueViewData> list = new ArrayList<>();
        for (DetailedVenue venue : detailedVenues) {
            list.add(VenueViewData.mapFrom(venue));
        }
        return list;
    }

    public static VenueViewData mapFrom(DetailedVenue venue) {
        VenueViewData venueViewObject = new VenueViewData();
        venueViewObject.setTitle(venue.getName());
        venueViewObject.setAddress(venue.getLocation().getAddress());
        venueViewObject.setLat(venue.getLocation().getLat());
        venueViewObject.setLng(venue.getLocation().getLng());
        venueViewObject.setDescription(venue.getDescription());

        if (venue.getHours() != null) {
            venueViewObject.setOpeningHoursStatus(venue.getHours().getStatus());
        }

        List<Photo> photos = venue.getPhotos();

        List<PhotoViewData> photoUrls = new ArrayList<>();
        for (Photo photo : photos) {
            photoUrls.add(PhotoViewData.map(photo));
        }
        venueViewObject.setPhotoUrls(photoUrls);

        return venueViewObject;
    }

    public VenueViewData() {
    }

}
