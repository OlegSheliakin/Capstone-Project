package home.oleg.placesnearme.data.repositories;

import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.repositories.VenueHistoryRepository;
import home.oleg.placesnearme.data.dao.DetailedVenueWithPhotosDao;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class VenueHistoryRepositoryImpl implements VenueHistoryRepository {

    private final DetailedVenueWithPhotosDao dao;

    public VenueHistoryRepositoryImpl(DetailedVenueWithPhotosDao dao) {
        this.dao = dao;
    }

    @Override
    public Flowable<List<DetailedVenue>> getHistory() {
        return null;
    }

    @Override
    public Completable dropCurrentVenue() {
        return null;
    }

    @Override
    public Single<Boolean> isHereNow(DetailedVenue detailedVenue) {
        return null;
    }

    @Override
    public Completable checkIn(DetailedVenue detailedVenue) {
        return null;
    }

    @Override
    public Completable remove(DetailedVenue detailedVenue) {
        return null;
    }
}
