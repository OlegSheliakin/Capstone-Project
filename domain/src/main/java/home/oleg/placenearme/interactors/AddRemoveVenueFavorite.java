package home.oleg.placenearme.interactors;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.repositories.FavoriteVenuesRepository;
import io.reactivex.Completable;
import io.reactivex.Single;

public class AddRemoveVenueFavorite {

    private final FavoriteVenuesRepository favoriteVenuesRepository;

    public AddRemoveVenueFavorite(FavoriteVenuesRepository favoriteVenuesRepository) {
        this.favoriteVenuesRepository = favoriteVenuesRepository;
    }

    public Single<DetailedVenue> execute(DetailedVenue detailedVenue) {
        if (detailedVenue.isFavorite()) {
            return favoriteVenuesRepository.deleteFromFavorite(detailedVenue.getId())
                    .andThen(Single.just(detailedVenue))
                    .doOnSuccess(dv -> dv.setFavorite(false));
        } else {
            return favoriteVenuesRepository.addToFavorite(detailedVenue)
                    .andThen(Single.just(detailedVenue))
                    .doOnSuccess(dv -> dv.setFavorite(true));
        }
    }
}
