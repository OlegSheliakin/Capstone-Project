
package home.oleg.placesnearme.network.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class DetailedVenue {

    private Photo bestPhoto;
    private String canonicalUrl;
    private List<Category> categories;
    private Contact contact;
    private Long createdAt;
    private String description;
    private Hours hours;
    private String id;
    private Likes likes;
    private Location location;
    private String name;
    private PhotosGroup photos;
    private Double rating;
    private String ratingColor;
    private Long ratingSignals;
    private String shortUrl;
    private Stats stats;
    private String storeId;
    private String timeZone;
    private String url;
    private Boolean verified;

    public Photo getBestPhoto() {
        return bestPhoto;
    }

    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public void setCanonicalUrl(String canonicalUrl) {
        this.canonicalUrl = canonicalUrl;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PhotosGroup getPhotoGroup() {
        return photos;
    }

    @NonNull
    public List<Photo> getPhotos() {
        if (photos.getGroups() == null) {
            return Collections.emptyList();
        }

        List<Photo> res = new ArrayList<>();

        for (Group<Photo> photoGroup : photos.getGroups()) {
            res.addAll(photoGroup.getItems());
        }

        return res;
    }

    public void setPhotos(PhotosGroup photos) {
        this.photos = photos;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getRatingColor() {
        return ratingColor;
    }

    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }

    public Long getRatingSignals() {
        return ratingSignals;
    }

    public void setRatingSignals(Long ratingSignals) {
        this.ratingSignals = ratingSignals;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

}
