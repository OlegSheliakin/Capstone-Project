package home.oleg.placesnearme.feature_add_history.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.smedialink.common.base.MessageEvent
import home.oleg.placesnearme.baseadd.BaseAddViewModelDelegate
import home.oleg.placesnearme.corepresentation.viewdata.VenueViewData
import home.oleg.placesnearme.feature_add_history.domain.interactor.CheckInOut
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 23.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class CheckInViewModelDelegate @Inject constructor(checkInOut: CheckInOut, mapper: CheckInMessageEventMapper)
    : BaseAddViewModelDelegate(checkInOut::execute, mapper::map), UpdateCheckIn {

    override val checkInMesage: LiveData<MessageEvent> = state

    override fun updateCheckIn(venue: VenueViewData) {
        manage(venue)
    }

}
