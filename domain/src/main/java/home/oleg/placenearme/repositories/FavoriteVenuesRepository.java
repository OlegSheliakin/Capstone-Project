package home.oleg.placenearme.repositories;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.models.Venue;
import io.reactivex.Completable;

public interface FavoriteVenuesRepository {

    Completable addToFavorite(DetailedVenue venue);

    Completable deleteFromFavorite(String id);

}
