package home.oleg.feature_add_history.presentation.viewmodel

import androidx.lifecycle.LiveData
import home.oleg.placesnearme.core_presentation.base.MessageEvent
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData

/**
 * Created by Oleg Sheliakin on 13.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

interface UpdateCheckIn {
    val checkInMesage: LiveData<MessageEvent>
    fun updateCheckIn(venue: VenueViewData)
}