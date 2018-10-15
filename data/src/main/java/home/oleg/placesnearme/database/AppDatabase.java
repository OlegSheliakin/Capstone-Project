package home.oleg.placesnearme.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import home.oleg.placesnearme.dao.DetailedVenueWithPhotosDao;
import home.oleg.placesnearme.model.DetailedVenueDbEntity;
import home.oleg.placesnearme.model.PhotoDbEntity;

@Database(entities = {DetailedVenueDbEntity.class, PhotoDbEntity.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase implements DaoProvider {
    public static final String NAME = "places_near_me_db";

    public void clear() {
        getDetailedVenueWithPhotosDao().deleteAll();
    }
}
