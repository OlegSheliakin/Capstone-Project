package home.oleg.placesnearme.feature_add_favorite.presentation

import home.oleg.placesnearme.baseadd.BaseAddViewModelDelegate
import home.oleg.placesnearme.corepresentation.viewdata.PlaceViewData
import home.oleg.placesnearme.feature_add_favorite.domain.interactor.CreateVenueFavorite
import javax.inject.Inject

class CreateFavoriteViewModelDelegate @Inject constructor(
        addRemoveVenueFavorite: CreateVenueFavorite,
        mapper: FavoriteMessageEventMapper) : BaseAddViewModelDelegate(
        addRemoveVenueFavorite::execute, mapper::map), UpdateFavorite {

    override fun updateFavorite(venue: PlaceViewData) = manage(venue)

}
