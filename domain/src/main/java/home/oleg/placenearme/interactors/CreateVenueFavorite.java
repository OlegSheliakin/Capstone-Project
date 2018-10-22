package home.oleg.placenearme.interactors;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.repositories.FavoriteVenuesRepository;
import io.reactivex.Completable;
import io.reactivex.Single;

public class CreateVenueFavorite {

    private final FavoriteVenuesRepository favoriteVenuesRepository;

    public CreateVenueFavorite(FavoriteVenuesRepository favoriteVenuesRepository) {
        this.favoriteVenuesRepository = favoriteVenuesRepository;
    }

    public Single<Boolean> execute(DetailedVenue detailedVenue) {
        if (detailedVenue.isFavorite()) {
            return favoriteVenuesRepository.deleteFromFavorite(detailedVenue)
                    .andThen(Single.just(false));
        } else {
            return favoriteVenuesRepository.addToFavorite(detailedVenue)
                    .andThen(Single.just(true));
        }
    }
}
