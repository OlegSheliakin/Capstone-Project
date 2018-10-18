package home.oleg.placenearme.repositories;

import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface FavoriteVenuesRepository {

    Flowable<List<DetailedVenue>> observeFavorites();

    Completable addToFavorite(DetailedVenue venue);

    Completable deleteFromFavorite(DetailedVenue venue);

}
