package home.oleg.placesnearme.feature_venue_detail.presentation

import com.smedialink.common.base.ErrorEvent
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData

sealed class VenueViewState {
    object Initial : VenueViewState()
    object Loading : VenueViewState()
    data class Success(val venue: VenueViewData) : VenueViewState()
    data class Error(val errorEvent: ErrorEvent) : VenueViewState()
}
