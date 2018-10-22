package home.oleg.placesnearme.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import home.oleg.placesnearme.data.model.DetailedVenueDbEntity;
import home.oleg.placesnearme.data.model.DetailedVenueHistoryDbEntity;
import home.oleg.placesnearme.data.model.PhotoDbEntity;


@Database(entities = {
        DetailedVenueDbEntity.class,
        PhotoDbEntity.class,
        DetailedVenueHistoryDbEntity.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase implements DaoProvider {
    public static final String NAME = "places_near_me_db";

    public void clear() {
        getDetailedVenueWithPhotosDao().deleteAll();
    }

}
