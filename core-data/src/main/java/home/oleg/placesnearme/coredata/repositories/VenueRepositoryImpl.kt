package home.oleg.placesnearme.coredata.repositories

import home.oleg.placesnearme.coredata.mapper.VenueMapper
import home.oleg.placesnearme.coredomain.models.Section
import home.oleg.placesnearme.coredomain.models.Venue
import home.oleg.placesnearme.coredomain.repositories.VenueRepository
import home.oleg.placesnearme.coredomain.repositories.VenueRequestParams
import home.oleg.placesnearme.corenetwork.service.IFourSquareAPI
import io.reactivex.Single
import javax.inject.Inject

class VenueRepositoryImpl @Inject constructor(private val api: IFourSquareAPI) : VenueRepository {
    private val queryParamCreator = QueryParamCreator()

    override fun getRecommendedBySection(section: Section, requestParams: VenueRequestParams): Single<List<Venue>> {
        val queryMap = queryParamCreator.create(section, requestParams)

        return api.explore(queryMap)
                .map { it.response }
                .map { it.venues }
                .map { VenueMapper.map(it) }
    }

    override fun search(query: String, requestParams: VenueRequestParams): Single<List<Venue>> {
        val queryMap = queryParamCreator.create(query, requestParams)
        return api.search(queryMap)
                .map { it.response }
                .map { it.venues }
                .map { VenueMapper.map(it) }
    }

}
