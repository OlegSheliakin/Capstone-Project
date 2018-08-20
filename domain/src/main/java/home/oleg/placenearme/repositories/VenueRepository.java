package home.oleg.placenearme.repositories;

import com.google.auto.value.AutoValue;

import java.util.List;
import java.util.Objects;

import home.oleg.placenearme.models.Venue;
import io.reactivex.Single;

public interface VenueRepository {

    Single<List<Venue>> getRecommendedByCategory(Category category, RequestParams requestParams);

    Single<List<Venue>> search(String query, RequestParams requestParams);

    @AutoValue
    abstract class RequestParams {

        abstract int getRadius();

        abstract double getLat();

        abstract double getLng();

    }

}
