package home.oleg.placesnearme.data.mapper

import home.oleg.placenearme.models.Hours

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
object HoursMapper {

    fun map(hours: home.oleg.placesnearme.network.models.Hours) = Hours(
            isOpen = hours.isOpen,
            status = hours.status
    )
}
