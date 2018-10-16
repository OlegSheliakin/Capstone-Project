package home.oleg.placesnearme.repositories;

import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.repositories.FavoriteVenuesRepository;
import home.oleg.placesnearme.dao.DetailedVenueWithPhotosDao;
import io.reactivex.Completable;
import io.reactivex.Single;

public class FavoriteVenueRepositoryImpl implements FavoriteVenuesRepository {

    private final DetailedVenueWithPhotosDao dao;

    public FavoriteVenueRepositoryImpl(DetailedVenueWithPhotosDao dao) {
        this.dao = dao;
    }

    @Override
    public Single<List<DetailedVenue>> getFavorites() {
        return null;
    }

    @Override
    public Completable addToFavorite(DetailedVenue venue) {
        return Completable.complete();
    }

    @Override
    public Completable deleteFromFavorite(String id) {
        return Completable.complete();
    }

}
