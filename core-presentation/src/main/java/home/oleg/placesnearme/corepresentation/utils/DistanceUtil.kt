package home.oleg.placesnearme.corepresentation.utils

import android.content.Context

import home.oleg.placesnearme.corepresentation.R

/**
 * Created by Oleg Sheliakin on 23.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
object DistanceUtil {

    private const val KILOMETER = 1000.0

    fun convertDistanceToString(distance: Double, context: Context): String {
        if (distance >= 1000) {
            val tempKm = distance / KILOMETER
            return context.getString(R.string.kms, tempKm)
        } else {
            return context.getString(R.string.meters, distance.toInt())
        }
    }

}
