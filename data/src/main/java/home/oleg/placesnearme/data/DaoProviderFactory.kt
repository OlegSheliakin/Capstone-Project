package home.oleg.placesnearme.data

import android.content.Context
import androidx.room.Room

import home.oleg.placesnearme.data.database.AppDatabase
import home.oleg.placesnearme.data.database.DaoProvider

object DaoProviderFactory {

    fun create(context: Context): DaoProvider {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
