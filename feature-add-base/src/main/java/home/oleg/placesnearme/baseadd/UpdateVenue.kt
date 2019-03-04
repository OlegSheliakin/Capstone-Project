package home.oleg.placesnearme.baseadd

import androidx.lifecycle.LiveData
import com.smedialink.common.base.MessageEvent
import home.oleg.placesnearme.corepresentation.viewdata.VenueViewData

/**
 * Created by Oleg Sheliakin on 13.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

interface UpdateVenue {
    val messages: LiveData<MessageEvent>
    fun update(venue: VenueViewData)
}