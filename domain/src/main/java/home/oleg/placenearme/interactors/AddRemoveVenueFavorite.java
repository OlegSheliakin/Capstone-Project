package home.oleg.placenearme.interactors;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.repositories.FavoriteVenuesRepository;
import io.reactivex.Completable;

public class AddRemoveVenueFavorite {

    private final FavoriteVenuesRepository favoriteVenuesRepository;

    public AddRemoveVenueFavorite(FavoriteVenuesRepository favoriteVenuesRepository) {
        this.favoriteVenuesRepository = favoriteVenuesRepository;
    }

    public Completable execute(DetailedVenue detailedVenue) {
        if (detailedVenue.isFavorite()) {
            return favoriteVenuesRepository.addToFavorite(detailedVenue);
        } else {
            return favoriteVenuesRepository.deleteFromFavorite(detailedVenue);
        }
    }
}
