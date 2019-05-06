package home.oleg.placesnearme.coredata.dao

import androidx.annotation.VisibleForTesting
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import home.oleg.placesnearme.coredata.database.AppDatabase
import home.oleg.placesnearme.coredata.model.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class PlacesDao : CrudDao<PlaceEntity>(tableName = PLACES_TABLE_NAME) {

    @Transaction
    @Query("SELECT * FROM $PLACES_TABLE_NAME WHERE id LIKE :id LIMIT 1")
    abstract fun streamById(id: String): Flowable<PlaceAndPhotos>

    @Transaction
    @Query("SELECT * FROM $PLACES_TABLE_NAME")
    abstract fun streamPlaces(): Flowable<List<PlaceAndPhotos>>

    @Transaction
    @Query("SELECT * FROM $PLACES_TABLE_NAME WHERE isFavorite = 1")
    abstract fun streamFavorites(): Flowable<List<PlaceAndPhotos>>

    @Query("SELECT * FROM $PLACES_TABLE_NAME WHERE id LIKE :id")
    abstract fun getPlaceById(id: String): PlaceEntity?

    @Transaction
    @Query("SELECT * FROM $PLACES_TABLE_NAME WHERE id LIKE :id")
    abstract fun getById(id: String): Single<PlaceAndPhotos>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrReplace(placeEntity: PlaceEntity, photoEntityList: List<PhotoEntity>)

    @Update
    abstract fun update(placeEntity: PlaceEntity, photoEntityList: List<PhotoEntity>)

    @Query("DELETE FROM $PLACES_TABLE_NAME WHERE id LIKE :id")
    abstract fun deleteById(id: String)

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    @Query("SELECT * FROM $PHOTOS_TABLE_NAME")
    abstract fun streamPhotos(): Flowable<List<PhotoEntity>>

}
