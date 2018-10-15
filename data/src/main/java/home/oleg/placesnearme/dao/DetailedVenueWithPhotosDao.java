package home.oleg.placesnearme.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placesnearme.model.DetailedVenueDbEntity;
import home.oleg.placesnearme.model.DetailedVenueWithPhotos;
import home.oleg.placesnearme.model.PhotoDbEntity;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public abstract class DetailedVenueWithPhotosDao {

    @Transaction
    @Query("SELECT * FROM detailed_venue")
    public abstract Flowable<List<DetailedVenueWithPhotos>> getAll();

    @Transaction
    @Query("SELECT * FROM detailed_venue WHERE id LIKE :id")
    public abstract Single<DetailedVenueWithPhotos> getById(String id);

    @Query("DELETE FROM detailed_venue WHERE id LIKE :id")
    public abstract void deleteById(String id);

    @Transaction
    public void insert(DetailedVenueWithPhotos detailedVenueWithPhotos) {
        insertDetailedVenue(detailedVenueWithPhotos.getVenue());
        insertPhotos(detailedVenueWithPhotos.getPhotos());
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertDetailedVenue(DetailedVenueDbEntity detailedVenueDbEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertPhotos(List<PhotoDbEntity> photoDbEntityList);

    @Query("DELETE FROM detailed_venue")
    public abstract void deleteAll();

}
