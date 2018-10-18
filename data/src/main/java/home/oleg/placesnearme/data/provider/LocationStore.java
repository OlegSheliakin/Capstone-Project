package home.oleg.placesnearme.data.provider;

import android.location.Location;

import io.reactivex.Single;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public interface LocationStore {
    Single<Location> getLastLocation();
}
