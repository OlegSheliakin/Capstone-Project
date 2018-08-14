
package home.oleg.placenearme.models;

import java.util.List;

public class Venue {

    private List<Category> categories;
    private String id;
    private Location location;
    private String name;
    private VenuePage venuePage;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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

    public VenuePage getVenuePage() {
        return venuePage;
    }

    public void setVenuePage(VenuePage venuePage) {
        this.venuePage = venuePage;
    }

}
