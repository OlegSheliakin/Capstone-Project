package home.oleg.placesnearme;

import android.location.Location;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Oleg on 16.04.2016.
 */
public final class Parameters {

    private static final String LL_KEY = "ll";
    private static final String CLIENT_ID_KEY= "client_id";
    private static final String CLIENT_SECRET_KEY = "client_secret";
    private static final String VERSION_KEY = "v";
    private static final String RADIUS_KEY = "radius";
    private static final String SECTION_KEY = "section";
    private static final String OPEN_NOW_KEY = "openNow";
    private static final String VENUE_PHOHTOS_KEY = "venuePhotos";

    private static final String CLIENT_ID = "BMDK0DP0YBJCG4BTIIC4SOEA2MMT2U4UZLJSBBZY0X2A23GF";
    private static final String CLIENT_SECRET = "MMSC3RSUOFRTQO13LCB5RY0P0WOQ14M0X1GCIUT32GM4D3YN";
    private static final String API_VERSION = "20160416";

    private String locationLL;
    private String section = "topPicks"; // by default
    private int radius;
    private int openNow = 1;
    private int venuePhotos = 1;

    public Parameters setLocation(Location location) {
        locationLL = location.getLatitude() + "," + location.getLongitude();
        return this;
    }

    public Parameters setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public Parameters setSection(String section) {
        if (section != null) {
            this.section = section;
        }
        return this;
    }

    public Parameters setOpenNow(int openNow) {
        this.openNow = openNow;
        return this;
    }

    public Parameters setVenuePhotos(int venuePhotos) {
        this.venuePhotos = venuePhotos;
        return this;
    }
    public Map<String, String> toQueryMap() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(LL_KEY, locationLL);
        queryMap.put(SECTION_KEY, section);
        queryMap.put(RADIUS_KEY, String.valueOf(radius));
        queryMap.put(OPEN_NOW_KEY, String.valueOf(openNow));
        queryMap.put(VENUE_PHOHTOS_KEY, String.valueOf(venuePhotos));
        queryMap.put(CLIENT_ID_KEY, CLIENT_ID);
        queryMap.put(CLIENT_SECRET_KEY, CLIENT_SECRET);
        queryMap.put(VERSION_KEY, API_VERSION);
        return queryMap;
    }


}
