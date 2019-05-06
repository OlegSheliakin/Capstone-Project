package home.oleg.placesnearme.coredata.location

import android.content.SharedPreferences
import android.location.Location
import com.google.gson.Gson

import home.oleg.placesnearme.coredomain.exception.LocationEmptyException
import home.oleg.placesnearme.coredomain.models.LatLng
import io.reactivex.Single

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class CachedLocationsStore(private val sharedPreferences: SharedPreferences) : LocationStore {

    private val gson = Gson()

    override fun getLastLocation(): Single<Location> {
        val lastLocation = Location("")

        val s = sharedPreferences.getString(KEY_LAT_LNG, "")
        val latLng = gson.fromJson(s, LatLng::class.java)
        return if (latLng == null) {
            Single.error(LocationEmptyException())
        } else {
            lastLocation.latitude = latLng.lat
            lastLocation.longitude = latLng.lng
            Single.just(lastLocation)
        }
    }

    fun save(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        sharedPreferences.edit()
                .putString(KEY_LAT_LNG, gson.toJson(latLng))
                .apply()
    }

    companion object {
        private val KEY_LAT_LNG = "key_lat_lng"
    }

}
