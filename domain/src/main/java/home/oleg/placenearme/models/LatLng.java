package home.oleg.placenearme.models;

/**
 * Created by Oleg Sheliakin on 09.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class LatLng {
    private final double lat;
    private final double lng;

    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
