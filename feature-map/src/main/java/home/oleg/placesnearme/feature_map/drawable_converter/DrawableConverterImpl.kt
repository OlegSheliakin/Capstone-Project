package home.oleg.placesnearme.feature_map.drawable_converter

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 28.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class DrawableConverterImpl @Inject constructor() : DrawableConverter {

    override fun convert(drawable: Drawable): BitmapDescriptor {
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
