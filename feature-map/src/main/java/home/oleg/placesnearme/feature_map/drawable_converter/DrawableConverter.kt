package home.oleg.placesnearme.feature_map.drawable_converter

import android.graphics.drawable.Drawable

import com.google.android.gms.maps.model.BitmapDescriptor

import io.reactivex.annotations.NonNull

/**
 * Created by Oleg Sheliakin on 28.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
interface DrawableConverter {
    fun convert(drawable: Drawable): BitmapDescriptor
}
