package home.oleg.placesnearme.core_presentation.provider

import android.graphics.drawable.Drawable

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.annotation.StringRes

/**
 * Created by Oleg Sheliakin on 28.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
interface ResourceProvider {

    fun getDrawable(@DrawableRes resId: Int): Drawable?

    fun getString(@StringRes resId: Int): String

    fun getColor(@ColorRes resId: Int): Int

}
