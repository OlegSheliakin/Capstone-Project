
package home.oleg.placenearme.models;

import java.util.List;

public class DetailedVenue {

    private Photo bestPhoto;
    private List<Category> categories;
    private Contact contact;
    private Long createdAt;
    private String description;
    private Hours hours;
    private String id;
    private Location location;
    private String name;
    private List<Photo> photos;
    private Double rating;
    private Long ratingSignals;

    public Photo getBestPhoto() {
        return bestPhoto;
    }

    public void setBestPhoto(Photo bestPhoto) {
        this.bestPhoto = bestPhoto;
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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getRatingSignals() {
        return ratingSignals;
    }

    public void setRatingSignals(Long ratingSignals) {
        this.ratingSignals = ratingSignals;
    }
}
