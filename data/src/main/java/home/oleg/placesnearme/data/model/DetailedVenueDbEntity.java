package home.oleg.placesnearme.data.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import home.oleg.placenearme.models.Category;
import home.oleg.placenearme.models.Contact;
import home.oleg.placenearme.models.Location;

@Entity(tableName = "detailed_venue")
public class DetailedVenueDbEntity {

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String description;
    private float rating;
    private boolean isFavorite;

    @Embedded
    private Location location;

    @Embedded
    private Contact contact;

    @Embedded
    private Category category;

    @NonNull
    public String getId() {
        return id;
    }

    public boolean isFavorite() {
        return isFavorite;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
