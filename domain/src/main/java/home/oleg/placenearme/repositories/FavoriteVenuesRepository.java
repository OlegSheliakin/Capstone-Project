package home.oleg.placenearme.repositories;

import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.models.Venue;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface FavoriteVenuesRepository {

    Single<List<DetailedVenue>> getFavorites();

    Completable addToFavorite(DetailedVenue venue);

    Completable deleteFromFavorite(String id);

}
