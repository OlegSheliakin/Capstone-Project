package home.oleg.placesnearme.coredata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import home.oleg.placesnearme.coredata.model.DetailedVenueDbEntity
import home.oleg.placesnearme.coredata.model.DetailedVenueHistoryDbEntity
import home.oleg.placesnearme.coredata.model.PhotoDbEntity

@Database(entities = [
    DetailedVenueDbEntity::class,
    PhotoDbEntity::class,
    DetailedVenueHistoryDbEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(), DaoProvider {

    fun clear() {
        detailedVenueWithPhotosDao.deleteAll()
    }

    companion object {
        val NAME = "places_near_me_db"
    }

}
