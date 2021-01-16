package home.oleg.placesnearme.coredata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import home.oleg.placesnearme.coredata.database.AppDatabase
import home.oleg.placesnearme.coredata.model.DetailedVenueHistory
import home.oleg.placesnearme.coredata.model.DetailedVenueHistoryDbEntity
import home.oleg.placesnearme.coredata.model.PLACES_HISTORY_TABLE_NAME
import home.oleg.placesnearme.coredata.model.PLACES_TABLE_NAME
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
abstract class DetailedVenueHistoryDao : CrudDao<DetailedVenueHistoryDbEntity>(PLACES_HISTORY_TABLE_NAME) {
    @Transaction
    @Query("SELECT $PLACES_TABLE_NAME.*, $PLACES_HISTORY_TABLE_NAME.createdAt " +
            "FROM $PLACES_TABLE_NAME " +
            "INNER JOIN $PLACES_HISTORY_TABLE_NAME ON $PLACES_HISTORY_TABLE_NAME.placeId = $PLACES_TABLE_NAME.id")
    abstract fun allHistory(): Flowable<List<DetailedVenueHistory>>

    @Query("SELECT * FROM $PLACES_HISTORY_TABLE_NAME WHERE isLastCheckIn = 1")
    abstract fun lastCheckIn(): Maybe<DetailedVenueHistoryDbEntity>

    @Query("SELECT * FROM $PLACES_HISTORY_TABLE_NAME WHERE $PLACES_HISTORY_TABLE_NAME.placeId = :venueId")
    abstract fun observeById(venueId: String): Flowable<DetailedVenueHistoryDbEntity>

    @Query("DELETE FROM $PLACES_HISTORY_TABLE_NAME WHERE $PLACES_HISTORY_TABLE_NAME.placeId = :venueId")
    abstract fun remove(venueId: String)

    @Query("SELECT * FROM $PLACES_HISTORY_TABLE_NAME WHERE $PLACES_HISTORY_TABLE_NAME.placeId = :venueId")
    abstract fun getById(venueId: String): Single<DetailedVenueHistoryDbEntity>

    @Transaction
    @Query("SELECT $PLACES_TABLE_NAME.*, $PLACES_HISTORY_TABLE_NAME.createdAt FROM $PLACES_TABLE_NAME" +
            " INNER JOIN $PLACES_HISTORY_TABLE_NAME ON $PLACES_HISTORY_TABLE_NAME.isLastCheckIn = 1")
    abstract fun current(): Single<DetailedVenueHistory>

    @Transaction
    open fun checkIn(placeId: String) {
        val historyDbEntity = DetailedVenueHistoryDbEntity(
                createdAt = System.currentTimeMillis(),
                isLastCheckIn = true,
                placeId = placeId
        )
        dropCheckIns()
        insertOrReplace(historyDbEntity)
    }

    @Query("UPDATE $PLACES_HISTORY_TABLE_NAME SET isLastCheckIn = 0 WHERE isLastCheckIn = 1")
    abstract fun dropCheckIns() : Long

}
