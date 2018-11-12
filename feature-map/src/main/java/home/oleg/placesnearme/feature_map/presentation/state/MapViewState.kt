package home.oleg.placesnearme.feature_map.presentation.state

import home.oleg.placesnearme.core_presentation.base.ErrorEvent

data class MapViewState(
        val isVenuesLoading: Boolean,
        val isSearchShown: Boolean,
        val error: ErrorEvent?) {

    companion object {

        fun initial(): MapViewState {
            return MapViewState(false, false, null)
        }
    }
}
