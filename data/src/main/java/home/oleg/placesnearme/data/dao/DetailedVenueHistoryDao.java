package home.oleg.placesnearme.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import home.oleg.placesnearme.data.model.DetailedVenueHistory;
import home.oleg.placesnearme.data.model.DetailedVenueHistoryDbEntity;
import home.oleg.placesnearme.data.model.DetailedVenueWithPhotos;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface DetailedVenueHistoryDao {
    @Transaction
    @Query("SELECT detailed_venue.*, venue_history.createdAt FROM detailed_venue " +
            "INNER JOIN venue_history ON venue_history.venueId = detailed_venue.id")
    Flowable<List<DetailedVenueHistory>> getAllHistory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DetailedVenueHistoryDbEntity detailedVenueHistoryDbEntity);

    @Transaction
    @Query("SELECT detailed_venue.*, venue_history.createdAt FROM detailed_venue " +
            "INNER JOIN venue_history ON venue_history.lastCheckIn = 1")
    Single<DetailedVenueHistory> getCurrent();

    @Query("SELECT * FROM venue_history WHERE lastCheckIn = 1")
    Flowable<DetailedVenueHistoryDbEntity> getLastCheckIn();

    @Query("DELETE FROM venue_history WHERE venue_history.venueId = :venueId")
    void remove(String venueId);

    @Query("SELECT * FROM venue_history WHERE venue_history.venueId = :venueId")
    Single<DetailedVenueHistoryDbEntity> getById(String venueId);
}
