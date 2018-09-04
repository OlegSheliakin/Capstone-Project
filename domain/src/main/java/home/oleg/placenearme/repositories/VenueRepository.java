package home.oleg.placenearme.repositories;

import java.util.List;

import home.oleg.placenearme.models.Section;
import home.oleg.placenearme.models.Venue;
import io.reactivex.Single;

public interface VenueRepository {

    Single<List<Venue>> getRecommendedBySection(Section.Type section, VenueRequestParams requestParams);

    Single<List<Venue>> search(String query, VenueRequestParams requestParams);

}
