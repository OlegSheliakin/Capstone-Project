
package home.oleg.placenearme.domain.models;

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
    private FeaturedPhotos featuredPhotos;

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

    public Location getLocation() {
        return location;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Boolean getVerified() {
        return verified;
    }

    public Stats getStats() {
        return stats;
    }

    public Price getPrice() {
        return price;
    }

    public Boolean getAllowMenuUrlEdit() {
        return allowMenuUrlEdit;
    }

    public VenuesPhoto getPhotos() {
        return photos;
    }

    public HereNow getHereNow() {
        return hereNow;
    }

    public FeaturedPhotos getFeaturedPhotos() {
        return featuredPhotos;
    }
}
