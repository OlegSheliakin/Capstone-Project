package home.oleg.placenearme.repositories;

import home.oleg.placenearme.models.Venue;

public interface FavoriteVenuesRepository {

    void addToFavorite(Venue venue);

    void deleteFromFavorite(long id);

}
