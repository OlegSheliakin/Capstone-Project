package home.oleg.placesnearme.coredata.repositories

import home.oleg.placesnearme.coredata.dao.PlacesDao
import home.oleg.placesnearme.coredata.mapper.DetailedVenueMapper
import home.oleg.placesnearme.coredomain.models.Place
import home.oleg.placesnearme.coredomain.repositories.DetailedVenueRepository
import home.oleg.placesnearme.corenetwork.service.IFourSquareAPI
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class DetailedVenueRepositoryImpl @Inject constructor(
        private val api: IFourSquareAPI,
        private val dao: PlacesDao) : DetailedVenueRepository {

    override fun getDetailedVenueById(venueId: String): Flowable<Place> {
        return Flowable.merge(
                fetch(venueId).toFlowable(),
                stream(venueId).skip(1)
        )
    }

    override fun stream(venueId: String): Flowable<Place> {
        return dao.streamById(venueId).map { DetailedVenueMapper.map(it) }
    }

    override fun fetch(venueId: String): Single<Place> {
        return getFromNetwork(venueId)
    }

    private fun getFromNetwork(venueId: String): Single<Place> {
        return api.getDetail(venueId)
                .map { it.response }
                .map { it.venue }
                .map { DetailedVenueMapper.map(it) }
                .flatMap(::refreshPlace)
    }

    private fun refreshPlace(place: Place): Single<Place> {
        val placeEntity = dao.getPlaceById(place.id)
        return if (placeEntity != null) {
            val fetchedVenue = place.copy(isFavorite = placeEntity.isFavorite)

            val placeAndPhotos = DetailedVenueMapper.map(fetchedVenue)
            dao.update(placeAndPhotos.venue, placeAndPhotos.photos)
            return Single.never<Place>()
        } else {
            Single.just(place)
        }
    }

}
