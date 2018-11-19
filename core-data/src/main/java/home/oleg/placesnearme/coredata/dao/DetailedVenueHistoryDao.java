package home.oleg.placesnearme.coredata.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import home.oleg.placesnearme.coredata.model.DetailedVenueHistory;
import home.oleg.placesnearme.coredata.model.DetailedVenueHistoryDbEntity;
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
            "INNER JOIN venue_history ON venue_history.isLastCheckIn = 1")
    Single<DetailedVenueHistory> getCurrent();

    @Query("SELECT * FROM venue_history WHERE venue_history.venueId = :venueId")
    Flowable<DetailedVenueHistoryDbEntity> observeById(String venueId);

    @Query("SELECT * FROM venue_history WHERE isLastCheckIn = 1")
    Maybe<DetailedVenueHistoryDbEntity> getLastCheckIn();

    @Query("DELETE FROM venue_history WHERE venue_history.venueId = :venueId")
    void remove(String venueId);

    @Query("SELECT * FROM venue_history WHERE venue_history.venueId = :venueId")
    Single<DetailedVenueHistoryDbEntity> getById(String venueId);
}
