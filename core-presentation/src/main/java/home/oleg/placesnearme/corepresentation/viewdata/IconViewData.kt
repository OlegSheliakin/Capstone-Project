package home.oleg.placesnearme.corepresentation.viewdata

import home.oleg.placesnearme.coredomain.models.Icon

/**
 * Created by Oleg Sheliakin on 21.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
data class IconViewData(
        val prefix: String? = null,
        val suffix: String? = null) {

    val iconUrlGray: String
        get() = prefix + "bg_" + SIZE + suffix

    companion object {

        val SIZE = 64

        fun map(icon: Icon) = IconViewData(
                prefix = icon.prefix,
                suffix = icon.suffix
        )
    }

}
