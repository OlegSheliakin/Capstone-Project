package home.oleg.placesnearme.data.repositories

import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placenearme.repositories.DetailedVenueRepository
import home.oleg.placesnearme.data.dao.DetailedVenueDao
import home.oleg.placesnearme.data.mapper.DetailedVenueMapper
import home.oleg.placesnearme.data.model.DetailedVenueDbEntity
import home.oleg.placesnearme.data.model.DetailedVenueWithPhotos
import home.oleg.placesnearme.network.models.reposnses.Response
import home.oleg.placesnearme.network.models.reposnses.VenueDetailResponse
import home.oleg.placesnearme.network.service.IFourSquareAPI
import io.reactivex.Flowable
import io.reactivex.Single

class DetailedVenueRepositoryImpl(private val api: IFourSquareAPI, private val dao: DetailedVenueDao) : DetailedVenueRepository {

    override fun getDetailedVenueById(venueId: String): Flowable<DetailedVenue> {
        return Flowable.merge(
                fetch(venueId).toFlowable(),
                stream(venueId).skip(1)
        )
    }

    override fun stream(venueId: String): Flowable<DetailedVenue> {
        return dao.observeVenue(venueId).map { DetailedVenueMapper.map(it) }
    }

    override fun fetch(venueId: String): Single<DetailedVenue> {
        return getFromNetwork(venueId)
    }

    private fun getFromNetwork(venueId: String): Single<DetailedVenue> {
        return api.getDetail(venueId)
                .map { it.response }
                .map { it.venue }
                .map { DetailedVenueMapper.map(it) }
                .flatMap { detailedVenue ->
                    val detailedVenueDbEntity = dao.getDetailedVenueById(detailedVenue.id)
                    if (detailedVenueDbEntity != null) {
                        val venue =
                                DetailedVenueMapper.map(detailedVenue
                                        .copy(isFavorite = detailedVenueDbEntity.isFavorite))
                        dao.update(venue.venue, venue.photos)
                        return@flatMap Single.never<DetailedVenue>()
                    }

                    Single.just(detailedVenue)
                }
    }

}
