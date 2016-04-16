package home.oleg.placesnearme;

import android.location.Location;


/**
 * Created by Oleg on 16.04.2016.
 */
public class Parameters {

    private final String clientId = "BMDK0DP0YBJCG4BTIIC4SOEA2MMT2U4UZLJSBBZY0X2A23GF";
    private final String clientSecret = "MMSC3RSUOFRTQO13LCB5RY0P0WOQ14M0X1GCIUT32GM4D3YN";
    private final String version = "20160416";
    private String locationLL;
    private String radius;
    private String section;
    private String limit;
    private String venuesPhoto;
    private String openNow;

    public Parameters setLocation(Location location) {
        locationLL = location.getLatitude() + "," + location.getLongitude();
        return this;
    }

    public Parameters setOpenNow(int openNow) {
        this.openNow = String.valueOf(openNow);
        return this;
    }

    public Parameters setSection(String section) {
        this.section = section;
        return this;
    }

    public Parameters setRadius(int radius) {
        this.radius = String.valueOf(radius);
        return this;
    }

    public Parameters setLimit(int limit) {
        this.limit = String.valueOf(limit);
        return this;
    }

    public Parameters setVenuesPhoto(int venuesPhoto) {
        this.venuesPhoto = String.valueOf(venuesPhoto);
        return this;
    }


    public String getLocationLL() {
        return locationLL;
    }

    public String getRadius() {
        return radius;
    }

    public String getClientId() { return clientId; }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getVersion() { return version; }

    public String getSection() {
        return section;
    }

    public String getLimit() {
        return limit;
    }

    public String getVenuesPhoto() {
        return venuesPhoto;
    }

    public String getOpenNow() {
        return openNow;
    }
}
