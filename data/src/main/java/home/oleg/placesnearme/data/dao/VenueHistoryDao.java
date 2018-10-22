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
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public abstract class VenueHistoryDao {
    @Transaction
    @Query("SELECT detailed_venue.*, venue_history.createdAt FROM detailed_venue INNER JOIN venue_history ON venue_history.venueId = detailed_venue.id")
    public abstract Flowable<List<DetailedVenueHistory>> getAllHistory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(DetailedVenueHistoryDbEntity detailedVenueHistoryDbEntity);

    @Query("SELECT detailed_venue.*, MAX(venue_history.createdAt) as createdAt FROM detailed_venue INNER JOIN venue_history ON venue_history.venueId = detailed_venue.id")
    public abstract Single<DetailedVenueWithPhotos> getCurrent();

    @Query("DELETE FROM venue_history WHERE venue_history.venueId = :venueId")
    public abstract void remove(String venueId);
}
