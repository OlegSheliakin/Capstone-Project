package home.oleg.placesnearme.coredata.mapper

import home.oleg.placesnearme.coredomain.models.Contact

/**
 * Created by Oleg Sheliakin on 09.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
object ContactMapper {

    fun map(contact: home.oleg.placesnearme.corenetwork.models.Contact): Contact {
        return Contact(contact.formattedPhone)
    }

}
