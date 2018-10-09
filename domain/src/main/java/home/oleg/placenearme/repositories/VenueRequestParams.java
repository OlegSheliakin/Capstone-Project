package home.oleg.placenearme.repositories;

import java.util.Objects;

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
        return new VenueRequestParams(300, lat, lng);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VenueRequestParams that = (VenueRequestParams) o;
        return radius == that.radius &&
                Double.compare(that.lat, lat) == 0 &&
                Double.compare(that.lng, lng) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(radius, lat, lng);
    }
}
