package home.oleg.placesnearme.coredata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import home.oleg.placesnearme.coredata.model.PlaceEntity
import home.oleg.placesnearme.coredata.model.DetailedVenueHistoryDbEntity
import home.oleg.placesnearme.coredata.model.PhotoEntity

@Database(entities = [
    PlaceEntity::class,
    PhotoEntity::class,
    DetailedVenueHistoryDbEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(), DaoProvider {

    fun clear() {
        detailedVenueWithPhotosDao.deleteAll()
    }

    companion object {
        val NAME = "places_near_me_db"
    }

}
