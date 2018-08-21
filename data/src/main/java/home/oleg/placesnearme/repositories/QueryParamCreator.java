package home.oleg.placesnearme.repositories;

import java.util.HashMap;
import java.util.Map;

import home.oleg.placenearme.repositories.Category;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placenearme.repositories.VenueRequestParams;

public class QueryParamCreator {

    private static final String LL_KEY = "ll";
    private static final String RADIUS_KEY = "radius";
    private static final String SECTION_KEY = "section";
    private static final String QUERY = "query";

    public Map<String, String> create(Category category,VenueRequestParams filter) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(LL_KEY, filter.getLat() + "," + filter.getLng());
        queryMap.put(SECTION_KEY, category.getValue());
        queryMap.put(RADIUS_KEY, String.valueOf(filter.getRadius()));

        return queryMap;
    }

    public Map<String, String> create(String query, VenueRequestParams filter) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(LL_KEY, filter.getLat() + "," + filter.getLng());
        queryMap.put(QUERY, query);
        queryMap.put(RADIUS_KEY, String.valueOf(filter.getRadius()));

        return queryMap;
    }

}
