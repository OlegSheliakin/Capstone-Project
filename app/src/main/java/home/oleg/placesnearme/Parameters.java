package home.oleg.placesnearme;

import android.location.Location;

import java.util.Locale;


/**
 * Created by Oleg on 16.04.2016.
 */
public class Parameters {

    private Location location;
    private int radius;
    private String section;
    private int limit;
    private int venuesPhoto;
    private int openNow;

    public Parameters setLocation(Location location) {
        this.location = location;
        return this;
    }

    public Parameters setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public Parameters setSection(String section) {
        this.section = section;
        return this;
    }

    public Parameters setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public Parameters setVenuesPhoto(int venuesPhoto) {
        this.venuesPhoto = venuesPhoto;
        return this;
    }

    public Parameters setOpenNow(int openNow) {
        this.openNow = openNow;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public int getRadius() {
        return radius;
    }

    public String getSection() {
        return section;
    }

    public int getLimit() {
        return limit;
    }

    public int getVenuesPhoto() {
        return venuesPhoto;
    }

    public int getOpenNow() {
        return openNow;
    }
}
