
package home.oleg.placesnearme.models;

import java.util.ArrayList;
import java.util.List;

public class Venue {

    private String id;
    private String name;
    private Contact contact;
    private Location location;
    private List<Category> categories = new ArrayList<>();
    private Boolean verified;
    private Stats stats;
    private Price price;
    private Boolean allowMenuUrlEdit;
    private VenuesPhoto photos;
    private HereNow hereNow;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Boolean getAllowMenuUrlEdit() {
        return allowMenuUrlEdit;
    }

    public void setAllowMenuUrlEdit(Boolean allowMenuUrlEdit) {
        this.allowMenuUrlEdit = allowMenuUrlEdit;
    }

    public VenuesPhoto getPhotos() {
        return photos;
    }

    public void setPhotos(VenuesPhoto venuesPhoto) {
        this.photos = venuesPhoto;
    }

    public HereNow getHereNow() {
        return hereNow;
    }

    public void setHereNow(HereNow hereNow) {
        this.hereNow = hereNow;
    }

}
