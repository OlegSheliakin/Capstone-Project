package home.oleg.placesnearme.repositories;

import home.oleg.placesnearme.core_network.service.IFourSquareAPI;
import home.oleg.placesnearme.core_network.models.reposnses.ExploreResponse;
import home.oleg.placesnearme.core_network.models.reposnses.Response;
import home.oleg.placesnearme.core_network.models.reposnses.VenuesResponse;

import java.util.List;
import java.util.Map;

import home.oleg.placenearme.models.Section;
import home.oleg.placenearme.models.Venue;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placenearme.repositories.VenueRequestParams;
import home.oleg.placesnearme.mapper.VenueMapper;
import io.reactivex.Single;

public class VenueRepositoryImpl implements VenueRepository {

    private final IFourSquareAPI api;
    private final QueryParamCreator queryParamCreator = new QueryParamCreator();

    public VenueRepositoryImpl(IFourSquareAPI api) {
        this.api = api;
    }

    @Override
    public Single<List<Venue>> getRecommendedBySection(Section section, VenueRequestParams filter) {
        Map<String, String> queryMap = queryParamCreator.create(section, filter);

        return api.explore(queryMap)
                .map(Response::getResponse)
                .map(ExploreResponse::getVenues)
                .map(VenueMapper::map);
    }

    @Override
    public Single<List<Venue>> search(String query, VenueRequestParams filter) {
        Map<String, String> queryMap = queryParamCreator.create(query, filter);
        return api.search(queryMap)
                .map(Response::getResponse)
                .map(VenuesResponse::getVenues)
                .map(VenueMapper::map);
    }

}
