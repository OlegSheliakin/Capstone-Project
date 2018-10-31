package home.oleg.placesnearme.feature_venues_history.state

import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData

data class VenuesHistoryState(
        val data: List<VenueViewItem>
)