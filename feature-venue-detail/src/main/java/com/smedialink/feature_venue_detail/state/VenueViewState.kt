package com.smedialink.feature_venue_detail.state

import home.oleg.placesnearme.core_presentation.base.ErrorEvent
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData

data class VenueViewState constructor(
        val isLoading: Boolean,
        val errorEvent: ErrorEvent?,
        val venueViewData: VenueViewData?)
