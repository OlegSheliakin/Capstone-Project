package home.oleg.placesnearme.core_presentation.viewdata;

import android.support.annotation.NonNull;

import com.smedialink.common.Optional;
import com.smedialink.common.function.Action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import home.oleg.placenearme.models.Category;
import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.models.Hours;
import home.oleg.placenearme.models.Photo;
import home.oleg.placesnearme.core_presentation.mapper.IconViewMapper;

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewData extends ShortVenueViewData {

    private List<PhotoViewData> photoUrls;
    private String description;
    private String openingHoursStatus;
    private String formattedPhone;
    private boolean isFavorite;
    private float rating;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getRating() {
        return rating / 2.0f;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public List<PhotoViewData> getPhotoUrls() {
        return photoUrls;
    }

    public String getOpeningHoursStatus() {
        return openingHoursStatus;
    }

    public void setOpeningHoursStatus(String openingHoursStatus) {
        this.openingHoursStatus = openingHoursStatus;
    }

    public String getFormattedPhone() {
        if (formattedPhone == null) {
            return "-";
        }

        return formattedPhone;
    }

    public void setFormattedPhone(String formattedPhone) {
        this.formattedPhone = formattedPhone;
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
        if (description == null) {
            return "-";
        }

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
        venueViewObject.setDistance(venue.getDistance());

        if (venue.getCategory() != null && venue.getCategory().getIcon() != null) {
            venueViewObject.setIconViewData(IconViewMapper.map(venue.getCategory().getIcon()));
            venueViewObject.setCategoryName(venue.getCategory().getName());
        }

        venueViewObject.setAddress(venue.getLocation().getAddress());
        venueViewObject.setLat(venue.getLocation().getLat());
        venueViewObject.setLng(venue.getLocation().getLng());
        venueViewObject.setDescription(venue.getDescription());
        venueViewObject.setRating(venue.getRating());
        venueViewObject.setFavorite(venue.isFavorite());

        Optional.of(venue.getContact())
                .ifPresent(contact -> venueViewObject.setFormattedPhone(contact.getFormattedPhone()));
        Optional.of(venue.getHours())
                .ifPresent(hours -> venueViewObject.setOpeningHoursStatus(hours.getStatus()));

        List<Photo> photos = venue.getPhotos();

        List<PhotoViewData> photoUrls = new ArrayList<>();
        for (Photo photo : photos) {
            photoUrls.add(PhotoViewData.map(photo));
        }
        venueViewObject.setPhotoUrls(photoUrls);

        return venueViewObject;
    }

}
