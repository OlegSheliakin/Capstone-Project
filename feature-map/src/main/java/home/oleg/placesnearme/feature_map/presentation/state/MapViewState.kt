package home.oleg.placesnearme.feature_map.presentation.state

import com.smedialink.common.base.ErrorEvent

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

sealed class State

object Loading : State()
data class Error(val error: ErrorEvent) : State()
