package home.oleg.placesnearme.repositories;

import java.util.List;
import java.util.Map;

import home.oleg.placenearme.models.Venue;
import home.oleg.placenearme.repositories.Section;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placenearme.repositories.VenueRequestParams;
import home.oleg.placesnearme.service.IFourSquareAPI;
import io.reactivex.Single;

public class VenueRepositoryImpl implements VenueRepository {

    private final IFourSquareAPI api;
    private final QueryParamCreator queryParamCreator = new QueryParamCreator();

    public VenueRepositoryImpl(IFourSquareAPI api) {
        this.api = api;
    }

    @Override
    public Single<List<Venue>> getRecommendedBySection(Section category, VenueRequestParams filter) {
        Map<String, String> queryMap = queryParamCreator.create(category, filter);

        return api.explore(queryMap).map(fullResponse -> fullResponse.getResponse().getVenues());
    }

    @Override
    public Single<List<Venue>> search(String query, VenueRequestParams filter) {
        Map<String, String> queryMap = queryParamCreator.create(query, filter);
        return api.search(queryMap).map(fullResponse -> fullResponse.getResponse().getVenues());
    }

}
