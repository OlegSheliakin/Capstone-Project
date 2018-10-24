package home.oleg.placesnearme.core_presentation.viewdata;

import com.smedialink.common.Optional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import home.oleg.placenearme.models.Category;
import home.oleg.placenearme.models.Contact;
import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.models.Hours;
import home.oleg.placenearme.models.Location;
import home.oleg.placenearme.models.Photo;
import home.oleg.placesnearme.core_presentation.mapper.IconViewMapper;

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewData extends PreviewVenueViewData {

    private List<PhotoViewData> photoUrls;
    private String description;
    private String openingHoursStatus;
    private String formattedPhone;
    private boolean isFavorite;
    private boolean isHere;
    private float rating;
    private Boolean open;

    public boolean isHere() {
        return isHere;
    }

    public void setHere(boolean here) {
        isHere = here;
    }

    public Boolean getOpen() {
        return open;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getAdoptedRating() {
        return rating / 2.0f;
    }

    public float getRating() {
        return rating;
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

    public void setOpen(Boolean open) {
        this.open = open;
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

    public DetailedVenue mapToDetailVenue() {
        DetailedVenue detailedVenue = new DetailedVenue();

        detailedVenue.setFavorite(isFavorite());
        detailedVenue.setName(getTitle());
        List<Photo> photoUrls = new ArrayList<>();
        for (PhotoViewData photo : getPhotos()) {
            photoUrls.add(PhotoViewData.map(photo));
        }
        detailedVenue.setPhotos(photoUrls);

        detailedVenue.setId(getId());
        detailedVenue.setDescription(getDescription());
        detailedVenue.setDistance(getDistance());
        detailedVenue.setRating(getRating());

        Location location = new Location();
        location.setLng(getLng());
        location.setLat(getLat());
        location.setAddress(getAddress());
        detailedVenue.setLocation(location);

        Contact contact = new Contact();
        contact.setFormattedPhone(getFormattedPhone());
        detailedVenue.setContact(contact);

        Category category = new Category();
        if (getIconViewData() != null) {
            category.setIconSuffix(getIconViewData().getSuffix());
            category.setIconPrefix(getIconViewData().getPrefix());
        }
        category.setName(getCategoryName());
        detailedVenue.setCategory(category);

        Hours hours = new Hours();
        hours.setIsOpen(isOpen());
        hours.setStatus(getOpeningHoursStatus());
        detailedVenue.setHours(hours);
        detailedVenue.setHereNow(isHere());
        detailedVenue.setDistance(getDistance());
        return detailedVenue;
    }

    public static VenueViewData mapFrom(DetailedVenue venue) {
        VenueViewData venueViewData = new VenueViewData();
        venueViewData.setId(venue.getId());
        venueViewData.setTitle(venue.getName());
        venueViewData.setDistance(venue.getDistance());

        if (venue.getCategory() != null) {
            IconViewData icon = IconViewMapper.map(
                    venue.getCategory().getIconPrefix(),
                    venue.getCategory().getIconSuffix());
            venueViewData.setIconViewData(icon);
            venueViewData.setCategoryName(venue.getCategory().getName());
        }

        venueViewData.setAddress(venue.getLocation().getAddress());
        venueViewData.setLat(venue.getLocation().getLat());
        venueViewData.setLng(venue.getLocation().getLng());
        venueViewData.setDescription(venue.getDescription());
        venueViewData.setRating(venue.getRating());
        venueViewData.setFavorite(venue.isFavorite());
        if (venue.getHours() != null) {
            venueViewData.setOpen(venue.getHours().getIsOpen());
        }

        Optional.of(venue.getContact())
                .ifPresent(contact -> venueViewData.setFormattedPhone(contact.getFormattedPhone()));
        Optional.of(venue.getHours())
                .ifPresent(hours -> venueViewData.setOpeningHoursStatus(hours.getStatus()));

        List<Photo> photos = venue.getPhotos();

        List<PhotoViewData> photoUrls = new ArrayList<>();
        for (Photo photo : photos) {
            photoUrls.add(PhotoViewData.map(photo));
        }
        venueViewData.setPhotoUrls(photoUrls);
        venueViewData.setHere(venue.isHereNow());

        return venueViewData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VenueViewData that = (VenueViewData) o;
        return isFavorite == that.isFavorite &&
                Float.compare(that.rating, rating) == 0 &&
                Objects.equals(photoUrls, that.photoUrls) &&
                Objects.equals(description, that.description) &&
                Objects.equals(openingHoursStatus, that.openingHoursStatus) &&
                Objects.equals(formattedPhone, that.formattedPhone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(photoUrls, description, openingHoursStatus, formattedPhone, isFavorite, rating);
    }

    public Boolean isOpen() {
        return open;
    }
}
