package home.oleg.placesnearme.data.provider

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location

import com.google.android.gms.location.FusedLocationProviderClient

import home.oleg.placenearme.exception.LocationEmptyException
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe

import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class ReactiveLocationStore private constructor(
        private val fusedLocationProviderClient: FusedLocationProviderClient) : LocationStore {

    override fun getLastLocation(): Single<Location> {
        return Single.create(LocationSingleSubscriber())
    }

    private inner class LocationSingleSubscriber : SingleOnSubscribe<Location> {

        @SuppressLint("MissingPermission")
        override fun subscribe(e: SingleEmitter<Location>) {
            val task = fusedLocationProviderClient.lastLocation
            task.addOnSuccessListener { location ->
                if (location == null) {
                    e.onError(LocationEmptyException())
                } else {
                    e.onSuccess(location)
                }
            }
            task.addOnFailureListener(e::onError)
        }
    }

    companion object {

        fun create(context: Context): ReactiveLocationStore {
            val fsProvider = getFusedLocationProviderClient(context)
            return ReactiveLocationStore(fsProvider)
        }
    }

}
