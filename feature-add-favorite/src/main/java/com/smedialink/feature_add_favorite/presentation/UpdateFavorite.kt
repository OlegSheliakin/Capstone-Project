package com.smedialink.feature_add_favorite.presentation

import androidx.lifecycle.LiveData
import home.oleg.placesnearme.core_presentation.base.MessageEvent
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData

/**
 * Created by Oleg Sheliakin on 13.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

interface UpdateFavorite {
    val favoriteMessage: LiveData<MessageEvent>
    fun updateFavorite(venue: VenueViewData)
}