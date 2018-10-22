package home.oleg.placenearme.interactors;

import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.repositories.FavoriteVenuesRepository;
import io.reactivex.Flowable;

public class GetFavoriteVenues {

    private final FavoriteVenuesRepository favoriteVenuesRepository;

    public GetFavoriteVenues(FavoriteVenuesRepository favoriteVenuesRepository) {
        this.favoriteVenuesRepository = favoriteVenuesRepository;
    }

    public Flowable<List<DetailedVenue>> execute() {
        return favoriteVenuesRepository.observeFavorites();
    }
}
