package home.oleg.placesnearme.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;
import android.support.annotation.VisibleForTesting;

import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placesnearme.data.model.DetailedVenueDbEntity;
import home.oleg.placesnearme.data.model.DetailedVenueHistory;
import home.oleg.placesnearme.data.model.DetailedVenueHistoryDbEntity;
import home.oleg.placesnearme.data.model.DetailedVenueWithPhotos;
import home.oleg.placesnearme.data.model.PhotoDbEntity;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public abstract class DetailedVenueDao {

    @VisibleForTesting
    @Transaction
    @Query("SELECT * FROM photodbentity")
    public abstract Flowable<List<PhotoDbEntity>> getAllPhotos();

    @Transaction
    @Query("SELECT * FROM detailed_venue WHERE id LIKE :venueId LIMIT 1")
    public abstract Flowable<DetailedVenueWithPhotos> observeVenue(String venueId);

    @Transaction
    @Query("SELECT * FROM detailed_venue")
    public abstract Flowable<List<DetailedVenueWithPhotos>> getAll();

    @Transaction
    @Query("SELECT * FROM detailed_venue WHERE id LIKE :id")
    public abstract Single<DetailedVenueWithPhotos> getById(String id);

    @Transaction
    @Query("SELECT * FROM detailed_venue WHERE isFavorite = 1")
    public abstract Flowable<List<DetailedVenueWithPhotos>> getAllFavorites();

    @Transaction
    public void insert(DetailedVenueWithPhotos detailedVenueWithPhotos) {
        DetailedVenueDbEntity detailedVenueDbEntity = detailedVenueWithPhotos.getVenue();
        List<PhotoDbEntity> photoDbEntities = detailedVenueWithPhotos.getPhotos();
        for (PhotoDbEntity photoDbEntity : photoDbEntities) {
            photoDbEntity.setVenueId(detailedVenueDbEntity.getId());
        }

        insertDetailedVenue(detailedVenueDbEntity, photoDbEntities);
    }

    @Query("SELECT * FROM detailed_venue WHERE id LIKE :id")
    public abstract DetailedVenueDbEntity getDetailedVenueById(String id);

    @Transaction
    @Update
    public abstract void update(DetailedVenueDbEntity venueDbEntity, List<PhotoDbEntity> photoDbEntityList);

    @Update
    public abstract void update(DetailedVenueDbEntity venueDbEntity);

    @Update
    public abstract void update(List<PhotoDbEntity> photoDbEntityList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertDetailedVenue(DetailedVenueDbEntity detailedVenueDbEntity,
                                             List<PhotoDbEntity> photoDbEntityList);

    @Query("DELETE FROM detailed_venue")
    public abstract void deleteAll();

}