package home.oleg.placenearme.repositories;

import java.util.List;

import home.oleg.placenearme.models.Venue;
import io.reactivex.Single;

public interface VenueRepository {

    Single<List<Venue>> getRecommendedByCategory(Section category, VenueRequestParams requestParams);

    Single<List<Venue>> search(String query, VenueRequestParams requestParams);

}
