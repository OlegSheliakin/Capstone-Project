package home.oleg.placesnearme.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.oleg.placenearme.models.Venue;
import home.oleg.placenearme.repositories.Category;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placesnearme.service.IFourSquareAPI;
import io.reactivex.Observable;

public class VenueRepositoryImpl implements VenueRepository {

    private final IFourSquareAPI api;
    private static final String LL_KEY = "ll";
    private static final String RADIUS_KEY = "radius";
    private static final String SECTION_KEY = "section";
    private static final String OPEN_NOW_KEY = "openNow";
    private static final String VENUE_PHOHTOS_KEY = "venuePhotos";

    public VenueRepositoryImpl(IFourSquareAPI api) {
        this.api = api;
    }


    @Override
    public Observable<List<Venue>> getRecommendedByCategory(Category category) {
        return api.explore(toQueryParam())
                .map(fullResponse -> fullResponse.getResponse().getVenues());
    }

    @Override
    public Observable<List<Venue>> search(String query) {
        return Observable.empty();
    }

    private Map<String, String> toQueryParam() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(LL_KEY, 37.4219983 + "," + -122.084);
        queryMap.put(SECTION_KEY, "topPicks");
        queryMap.put(RADIUS_KEY, String.valueOf(1000));
        queryMap.put(OPEN_NOW_KEY, String.valueOf(1));
        queryMap.put(VENUE_PHOHTOS_KEY, "1");
        return queryMap;
    }

}
