package home.oleg.placesnearme.coredata.location

import android.app.Activity
import android.content.Context
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import io.reactivex.Completable
import javax.inject.Inject

class ReactiveLocationSettings @Inject constructor(
        private val activity: Activity
) {

    fun checkSettings(): Completable {
        return Completable.create { emitter ->
            val mLocationRequest = LocationRequest()
            mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            val builder = LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest)

            val task = LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build())

            task.addOnSuccessListener {
                emitter.onComplete()
            }

            task.addOnFailureListener {
                emitter.onError(it)
            }

        }
    }

}