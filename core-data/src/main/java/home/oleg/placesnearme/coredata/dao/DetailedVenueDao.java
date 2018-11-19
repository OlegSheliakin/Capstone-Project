package home.oleg.placesnearme.coredata.dao;

import java.util.List;

import androidx.annotation.VisibleForTesting;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import home.oleg.placesnearme.coredata.model.DetailedVenueDbEntity;
import home.oleg.placesnearme.coredata.model.DetailedVenueWithPhotos;
import home.oleg.placesnearme.coredata.model.PhotoDbEntity;
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
