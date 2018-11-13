package home.oleg.placesnearme.data.mapper

import home.oleg.placenearme.models.Contact

/**
 * Created by Oleg Sheliakin on 09.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
object ContactMapper {

    fun map(contact: home.oleg.placesnearme.network.models.Contact): Contact {
        return Contact(contact.formattedPhone)
    }

}
