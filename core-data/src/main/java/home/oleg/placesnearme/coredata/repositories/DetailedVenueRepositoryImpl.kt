package home.oleg.placesnearme.coredata.repositories

import home.oleg.placesnearme.coredata.dao.PlacesDao
import home.oleg.placesnearme.coredata.mapper.DetailedVenueMapper
import home.oleg.placesnearme.coredomain.models.Place
import home.oleg.placesnearme.coredomain.repositories.DetailedVenueRepository
import home.oleg.placesnearme.corenetwork.service.IFourSquareAPI
import io.reactivex.Flowable
import io.reactivex.Single
import org.intellij.lang.annotations.Flow
import javax.inject.Inject

class DetailedVenueRepositoryImpl @Inject constructor(
        private val api: IFourSquareAPI,
        private val dao: PlacesDao) : DetailedVenueRepository {

    override fun getPlaceById(placeId: String): Flowable<Place> {
        return Flowable.defer {
            val place = dao.getPlaceById(placeId)
            return@defer if (place != null) {
                dao.streamById(placeId).map(DetailedVenueMapper::map)
            } else {
                Flowable.merge(
                        getFromNetwork(placeId).toFlowable(),
                        dao.streamById(placeId).map(DetailedVenueMapper::map))
            }
        }
    }

    private fun getFromNetwork(placeId: String): Single<Place> {
        return api.getDetail(placeId)
                .map { DetailedVenueMapper.map(it.response.venue) }
                .flatMap(::refreshPlace)
    }

    private fun refreshPlace(place: Place): Single<Place> {
        val placeAndPhotos = DetailedVenueMapper.map(place)
        dao.insertOrReplace(placeAndPhotos.place, placeAndPhotos.photos)
        return Single.never()
    }

}
