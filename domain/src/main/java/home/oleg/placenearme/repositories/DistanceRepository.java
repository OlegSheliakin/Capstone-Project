package home.oleg.placenearme.repositories;

import home.oleg.placenearme.models.LatLng;

/**
 * Created by Oleg Sheliakin on 09.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public interface DistanceRepository {
    double evaluate(LatLng from, LatLng to);
}
