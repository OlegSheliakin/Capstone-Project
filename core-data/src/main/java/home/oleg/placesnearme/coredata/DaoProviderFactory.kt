package home.oleg.placesnearme.coredata

import android.content.Context
import androidx.room.Room

import home.oleg.placesnearme.coredata.database.AppDatabase
import home.oleg.placesnearme.coredata.database.DaoProvider

object DaoProviderFactory {

    fun create(context: Context): DaoProvider {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
