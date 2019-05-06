package home.oleg.placesnearme.feature_add_history.presentation.viewmodel

import com.smedialink.common.base.MessageEvent
import home.oleg.placesnearme.corepresentation.viewdata.PlaceViewData
import io.reactivex.Single

/**
 * Created by Oleg Sheliakin on 13.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

interface UpdateCheckIn {
    fun updateCheckIn(venue: PlaceViewData) : Single<MessageEvent>
}