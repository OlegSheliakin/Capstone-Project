package home.oleg.placenearme.repositories;

import java.util.List;

import home.oleg.placenearme.models.Venue;
import io.reactivex.Observable;

public interface VenueRepository {

    Observable<List<Venue>> getRecommendedByCategory(Category category);

    Observable<List<Venue>> search(String query);

}
