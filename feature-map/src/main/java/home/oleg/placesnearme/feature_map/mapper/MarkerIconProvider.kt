package home.oleg.placesnearme.feature_map.mapper

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable

import com.google.android.gms.maps.model.BitmapDescriptor

import java.util.HashMap
import java.util.Objects

import javax.inject.Inject

import home.oleg.placenearme.models.Section
import home.oleg.placesnearme.core_presentation.provider.ResourceProvider
import home.oleg.placesnearme.feature_map.R
import home.oleg.placesnearme.feature_map.drawable_converter.DrawableConverter
import io.reactivex.annotations.NonNull
import io.reactivex.annotations.Nullable

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class MarkerIconProvider @Inject constructor(private val resourceProvider: ResourceProvider,
                                             private val drawableConverter: DrawableConverter,
                                             private val colors: Map<Section, Int>) {

    fun getIconByCategory(section: Section): BitmapDescriptor {
        val drawable = resourceProvider.getDrawable(R.drawable.ic_place_marker)!!

        val color = getColor(section)
        changeColor(drawable, color)

        return drawableConverter.convert(drawable)
    }

    private fun getColor(section: Section): Int {
        val colorResId = colors[section]!!
        return resourceProvider.getColor(colorResId)
    }

    private fun changeColor(drawable: Drawable, color: Int) {
        if (drawable is GradientDrawable) {
            drawable.setColor(color)
        } else {
            throw IllegalStateException("Drawable should be GradientDrawable")
        }
    }

}
