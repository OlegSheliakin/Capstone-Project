package home.oleg.placesnearme.coredata.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface BaseDao<ENTITY> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replace(entity: Entity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replace(entities: List<Entity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: Entity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entities: List<Entity>): List<Long>

    @Update
    fun update(entity: Entity)

    @Update
    fun update(entities: List<Entity>)

    @Transaction
    fun upsert(entity: Entity) {
        val id = insert(entity)
        if (id == -1L) {
            update(entity)
        }
    }

    @Transaction
    fun upsert(entities: List<Entity>) {
        val insertResult = insert(entities)

        val updateList = entities.filterIndexed { i, _ ->
            insertResult[i] == -1L
        }

        if (!updateList.isEmpty()) {
            update(updateList)
        }
    }

    @Delete
    fun delete(entity: Entity)

    @Delete
    fun delete(entities: List<Entity>)

}