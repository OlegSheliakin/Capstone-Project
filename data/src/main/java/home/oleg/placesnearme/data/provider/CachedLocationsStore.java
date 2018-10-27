package home.oleg.placesnearme.data.provider;

import android.content.SharedPreferences;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import home.oleg.placenearme.exception.LocationEmptyException;
import home.oleg.placenearme.models.LatLng;
import io.reactivex.Single;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class CachedLocationsStore implements LocationStore {

    private final static String KEY_LAT_LNG = "key_lat_lng";

    private final SharedPreferences sharedPreferences;
    private final Gson gson = new Gson();

    public CachedLocationsStore(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Single<Location> getLastLocation() {
        Location lastLocation = new Location("");

        String s = sharedPreferences.getString(KEY_LAT_LNG, "");
        LatLng latLng = gson.fromJson(s, LatLng.class);
        if (latLng == null) {
            return Single.error(new LocationEmptyException());
        } else {
            return Single.just(lastLocation);
        }
    }

    public void save(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        sharedPreferences.edit()
                .putString(KEY_LAT_LNG, gson.toJson(latLng))
                .apply();
    }

}
