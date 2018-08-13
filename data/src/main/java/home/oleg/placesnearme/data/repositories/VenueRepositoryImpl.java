package home.oleg.placesnearme.data.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.oleg.placenearme.domain.interactors.MapInteractor;
import home.oleg.placenearme.domain.models.FullResponse;
import home.oleg.placenearme.domain.models.Item;
import home.oleg.placenearme.domain.models.Venue;
import home.oleg.placenearme.domain.repositories.VenueRepository;
import home.oleg.placesnearme.data.service.IFourSquareAPI;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

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
    public Observable<List<Venue>> explore(MapInteractor.Parameters parameters) {
        return api.getItems(toQueryParam(parameters)).map(fullResponse -> {
            List<Item> items = fullResponse.getResponse().getGroups().get(0).getItems();
            List<Venue> venues = new ArrayList<>();
            for (Item item : items) {
                venues.add(item.getVenue());
            }
            return venues;
        });
    }

    @Override
    public Observable<List<Venue>> search(String query) {
        return Observable.empty();
    }

    private Map<String, String> toQueryParam(MapInteractor.Parameters parameters) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(LL_KEY, parameters.getLatitude() + "," + parameters.getLongitude());
        queryMap.put(SECTION_KEY, parameters.getSection());
        queryMap.put(RADIUS_KEY, String.valueOf(parameters.getRadius()));
        queryMap.put(OPEN_NOW_KEY, String.valueOf(parameters.getOpenNow()));
        queryMap.put(VENUE_PHOHTOS_KEY, String.valueOf(parameters.getVenuePhotos()));
        return queryMap;
    }

}
