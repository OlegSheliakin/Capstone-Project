package home.oleg.placesnearme.feature_map.presentation

import com.olegsheliakin.statebinder.State
import com.smedialink.common.base.ErrorEvent
import home.oleg.placesnearme.coredomain.models.LatLng
import home.oleg.placesnearme.corepresentation.viewdata.PreviewPlace

data class MapViewState(
        val isVenuesLoading: Boolean,
        val isSearchShown: Boolean,
        val userLatLng: LatLng? = null,
        val places: List<PreviewPlace> = emptyList(),
        val error: ErrorEvent? = null) : State {

    companion object {
        fun initial(): MapViewState {
            return MapViewState(isVenuesLoading = false, isSearchShown = false)
        }
    }
}

