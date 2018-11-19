package home.oleg.placesnearme.coredata.mapper

import home.oleg.placesnearme.coredomain.models.Hours

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
object HoursMapper {

    fun map(hours: home.oleg.placesnearme.corenetwork.models.Hours) = Hours(
            isOpen = hours.isOpen,
            status = hours.status
    )
}
