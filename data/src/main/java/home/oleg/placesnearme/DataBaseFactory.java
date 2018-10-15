package home.oleg.placesnearme;

import android.arch.persistence.room.Room;
import android.content.Context;

import home.oleg.placesnearme.dao.DetailedVenueWithPhotosDao;
import home.oleg.placesnearme.database.AppDatabase;
import home.oleg.placesnearme.database.DaoProvider;

public final class DataBaseFactory {

    public static DaoProvider create(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, AppDatabase.NAME)
                .fallbackToDestructiveMigration()
                .build();
    }
}
