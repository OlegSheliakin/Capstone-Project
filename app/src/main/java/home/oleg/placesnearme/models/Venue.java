package home.oleg.placesnearme.models;

import java.util.ArrayList;

/**
 * Created by Oleg on 12.04.2016.
 */
public class Venue {

    private String id;

    private String name;

    private Location location;

    private ArrayList<Category> categories;

    private boolean verified;

    private Statistics stats;

    private HereNow beenHere;

    private HereNow hereNow;

    private long createdAt;

    private Mayor mayor;

    private String timeZone;

    private String canonicalUrl;

    private String shortUrl;

    private boolean dislike;

    private String url;

    private boolean like;

    public String getTimeZone() {
        return timeZone;
    }

    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public boolean isDislike() {
        return dislike;
    }

    public HereNow getHereNow() {
        return hereNow;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public Mayor getMayor() {
        return mayor;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public boolean isVerified() {
        return verified;
    }

    public Statistics getStats() {
        return stats;
    }

    public HereNow getBeenHere() {
        return beenHere;
    }

    public String getUrl() {
        return url;
    }

    public boolean isLike() {
        return like;
    }

}