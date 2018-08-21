package home.oleg.placenearme.repositories;

/**
 * Created by Oleg Sheliakin on 21.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

public final class VenueRequestParams {

    private final int radius;
    private final double lat;
    private final double lng;

    public VenueRequestParams(int radius, double lat, double lng){
        this.radius = radius;
        this.lat = lat;
        this.lng = lng;
    }

    public static VenueRequestParams withLocation(double lat, double lng) {
        return new VenueRequestParams(1000, lat, lng);
    }

    public int getRadius() {
        return radius;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
