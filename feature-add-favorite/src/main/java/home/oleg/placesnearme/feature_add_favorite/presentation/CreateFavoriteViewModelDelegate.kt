package home.oleg.placesnearme.feature_add_favorite.presentation

import androidx.lifecycle.LiveData
import com.smedialink.common.base.MessageEvent
import home.oleg.placesnearme.baseadd.BaseAddViewModelDelegate
import home.oleg.placesnearme.corepresentation.viewdata.VenueViewData
import home.oleg.placesnearme.feature_add_favorite.domain.interactor.CreateVenueFavorite
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
