package com.smedialink.feature_venue_detail.state

import home.oleg.placesnearme.core_presentation.base.ErrorEvent
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData

class VenueViewState
private constructor(
        val isLoading: Boolean,
        val errorEvent: ErrorEvent?,
        val venueViewData: VenueViewData?) {

    companion object {

        fun loading(): VenueViewState {
            return VenueViewState(true, null, null)
        }

        fun error(errorEvent: ErrorEvent): VenueViewState {
            return VenueViewState(false, errorEvent, null)
        }

        fun success(venueViewData: VenueViewData): VenueViewState {
            return VenueViewState(false, null, venueViewData)
        }
    }
}
