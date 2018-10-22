package home.oleg.placesnearme.data;

import android.arch.persistence.room.Room;
import android.content.Context;

import home.oleg.placesnearme.data.database.AppDatabase;
import home.oleg.placesnearme.data.database.DaoProvider;

public final class DaoProviderFactory {

    public static DaoProvider create(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, AppDatabase.NAME)
                .fallbackToDestructiveMigration()
                .build();
    }
}
