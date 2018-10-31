package home.oleg.placesnearme.feature_map.viewdata

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

import home.oleg.placenearme.models.Section

/**
 * Created by Oleg Sheliakin on 25.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
data class SectionViewData(val section: Section,
                           @StringRes
                           val sectionNameRes: Int,
                           @DrawableRes
                           val sectionDrawableRes: Int)
