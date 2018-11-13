package com.smedialink.feature_add_favorite.presentation

import androidx.lifecycle.LiveData
import com.easymedium.olegsheliakin.baseadd.BaseAddViewModelDelegate
import com.smedialink.feature_add_favorite.domain.interactor.CreateVenueFavorite
import home.oleg.placesnearme.core_presentation.base.MessageEvent
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import javax.inject.Inject

class CreateFavoriteViewModelDelegate @Inject constructor(
        addRemoveVenueFavorite: CreateVenueFavorite,
        mapper: FavoriteMessageEventMapper) : BaseAddViewModelDelegate(
        addRemoveVenueFavorite::execute, mapper::map), UpdateFavorite {

    override val favoriteMessage: LiveData<MessageEvent> = state

    override fun updateFavorite(venue: VenueViewData) {
        manage(venue)
    }

}
