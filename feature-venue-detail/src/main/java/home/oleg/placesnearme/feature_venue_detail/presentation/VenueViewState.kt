package home.oleg.placesnearme.feature_venue_detail.presentation

import com.olegsheliakin.statebinder.State
import com.smedialink.common.base.ErrorEvent
import com.smedialink.common.base.MessageEvent
import home.oleg.placesnearme.corepresentation.viewdata.PlaceViewData

data class PlaceViewState(
        val isLoading: Boolean,
        val error: ErrorEvent?,
        val place: PlaceViewData?,
        val message: MessageEvent?
) : State {

    companion object {
        fun initial() = PlaceViewState(
                isLoading = false,
                error = null,
                place = null,
                message = null)
    }
}

sealed class VenueViewState {
    object Initial : VenueViewState()
    object Loading : VenueViewState()
    data class Success(val venue: PlaceViewData) : VenueViewState()
    data class Error(val errorEvent: ErrorEvent) : VenueViewState()
}
