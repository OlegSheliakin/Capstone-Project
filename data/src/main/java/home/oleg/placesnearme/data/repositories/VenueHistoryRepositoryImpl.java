package home.oleg.placesnearme.data.repositories;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.models.DetailedVenueWithCreatedDate;
import home.oleg.placenearme.repositories.VenueHistoryRepository;
import home.oleg.placesnearme.data.dao.DetailedVenueDao;
import home.oleg.placesnearme.data.dao.DetailedVenueHistoryDao;
import home.oleg.placesnearme.data.mapper.DetailedVenueMapper;
import home.oleg.placesnearme.data.model.DetailedVenueHistory;
import home.oleg.placesnearme.data.model.DetailedVenueHistoryDbEntity;
import home.oleg.placesnearme.data.model.DetailedVenueWithPhotos;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class VenueHistoryRepositoryImpl implements VenueHistoryRepository {

    private final DetailedVenueDao detailedVenueWithPhotosDao;
    private final DetailedVenueHistoryDao venueHistoryDao;

    public VenueHistoryRepositoryImpl(DetailedVenueDao detailedVenueWithPhotosDao,
                                      DetailedVenueHistoryDao venueHistoryDao) {
        this.detailedVenueWithPhotosDao = detailedVenueWithPhotosDao;
        this.venueHistoryDao = venueHistoryDao;
    }

    @Override
    public Flowable<List<DetailedVenue>> getHistory() {
        return venueHistoryDao.getAllHistory().map(this::map);
    }

    @Override
    public Completable checkOutFromCurrent() {
        return venueHistoryDao
                .getLastCheckIn()
                .flatMapCompletable(this::dropCheckIn);
    }

    @Override
    public Flowable<Boolean> isHereNow(String id) {
        return venueHistoryDao.getLastCheckIn()
                .flatMap(historyDbEntity -> {
                    if (historyDbEntity.getVenueId().equals(id)) {
                        return Flowable.just(true);
                    } else {
                        return Flowable.just(false);
                    }
                    //if last is empty, then will be thrown an error.
                    //convert error to false -> it means is not here now
                }).onErrorReturnItem(false);
    }

    @Override
    public Completable checkIn(DetailedVenue detailedVenue) {
        return Completable.fromAction(() -> {
            //always insert or replace if exist
            DetailedVenueWithPhotos detailedVenueWithPhotos = DetailedVenueMapper.map(detailedVenue);
            detailedVenueWithPhotosDao.insert(detailedVenueWithPhotos);

            DetailedVenueHistoryDbEntity historyDbEntity = new DetailedVenueHistoryDbEntity();
            historyDbEntity.setCreatedAt(System.currentTimeMillis());
            historyDbEntity.setLastCheckIn(true);
            historyDbEntity.setVenueId(detailedVenue.getId());
            venueHistoryDao.insert(historyDbEntity);
        });
    }

    @Override
    public Completable checkOut(String venueId) {
        return venueHistoryDao.getById(venueId).flatMapCompletable(this::dropCheckIn);
    }

    @Override
    public Completable remove(String venueId) {
        return Completable.fromAction(() -> venueHistoryDao.remove(venueId));
    }

    private Completable dropCheckIn(DetailedVenueHistoryDbEntity historyDbEntity) {
        historyDbEntity.setLastCheckIn(false);
        return Completable.fromAction(() -> venueHistoryDao.insert(historyDbEntity));
    }

    private List<DetailedVenue> map(Iterable<DetailedVenueHistory> detailedVenueHistoryList) {
        List<DetailedVenue> result = new ArrayList<>();
        for (DetailedVenueHistory detailedVenueHistory : detailedVenueHistoryList) {
            DetailedVenue detailedVenue = DetailedVenueMapper.map(detailedVenueHistory.getDetailedVenueWithPhotos());
            result.add(detailedVenue);
        }

        return result;
    }
}
