package home.oleg.placesnearme;

import android.location.Location;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Oleg on 16.04.2016.
 */
public final class Parameters {

    public static final String CLIENT_ID = "BMDK0DP0YBJCG4BTIIC4SOEA2MMT2U4UZLJSBBZY0X2A23GF";
    public static final String CLIENT_SECRET = "MMSC3RSUOFRTQO13LCB5RY0P0WOQ14M0X1GCIUT32GM4D3YN";
    public static final String API_VERSION = "20160416";
    private String locationLL;
    private String radius;
    private String section;
    private String openNow;

    {
        // initialization by default
        section = "topPicks";
        openNow = "0";
    }

    public Parameters setLocation(Location location) {
        locationLL = location.getLatitude() + "," + location.getLongitude();
        return this;
    }

    public Parameters setRadius(int radius) {
        this.radius = String.valueOf(radius);
        return this;
    }

    public Parameters setSection(String section) {
        this.section = section;
        return this;
    }

    public Parameters setOpenNow(int openNow) {
        this.openNow = String.valueOf(openNow);
        return this;
    }

    public String getLocationLL() {
        return locationLL;
    }

    public String getRadius() {
        return radius;
    }

    public String getSection() {
        return section;
    }

    public String getOpenNow() {
        return openNow;
    }

    public Map<String,String> toQueryMap(){
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put(Constants.LL_KEY, getLocationLL());
        queryMap.put(Constants.SECTION_KEY, getSection());
        queryMap.put(Constants.RADIUS_KEY, getRadius());
        queryMap.put(Constants.OPEN_NOW_KEY, getOpenNow());
        queryMap.put(Constants.CLIENT_ID_KEY, CLIENT_ID);
        queryMap.put(Constants.CLIENT_SECRET_KEY, CLIENT_SECRET);
        queryMap.put(Constants.VERSION_KEY, API_VERSION);
        return queryMap;
    }
}
