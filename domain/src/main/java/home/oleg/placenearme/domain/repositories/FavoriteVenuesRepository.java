package home.oleg.placenearme.domain.repositories;

import home.oleg.placenearme.domain.models.Venue;

public interface FavoriteVenuesRepository {

    void addToFavorite(Venue venue);

    void deleteFromFavorite(long id);

}
