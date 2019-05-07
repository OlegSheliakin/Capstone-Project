package home.oleg.placesnearme.coredata.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import home.oleg.placesnearme.coredata.FakesStore
import home.oleg.placesnearme.coredata.database.AppDatabase
import home.oleg.placesnearme.coredata.model.DetailedVenueHistoryDbEntity
import home.oleg.placesnearme.coredata.model.PlaceAndPhotos
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VenueHistoryDaoTest {

    private lateinit var detailedVenueWithPhotosDao: PlacesDao
    private lateinit var dao: DetailedVenueHistoryDao

    @Before
    fun setUp() {
        val appDatabase = Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase::class.java)
                .build()
        dao = appDatabase.venueHistoryDao
        detailedVenueWithPhotosDao = appDatabase.detailedVenueWithPhotosDao
    }

    @Test
    fun shouldAddToHistory() {
        val detailedVenue = FakesStore.getVenue("1")
        val detailedVenueWithPhotos = PlaceAndPhotos(detailedVenue)
        val photoDbEntities = FakesStore.listPhotos(detailedVenue.id)
        detailedVenueWithPhotos.photos = photoDbEntities

        detailedVenueWithPhotosDao.insertOrReplace(detailedVenueWithPhotos.place, detailedVenueWithPhotos.photos)

        val detailedVenueHistoryDbEntity = DetailedVenueHistoryDbEntity(
                placeId = detailedVenue.id,
                createdAt = System.currentTimeMillis(),
                isLastCheckIn = false
        )
        dao.insertOrReplace(detailedVenueHistoryDbEntity)

        val history = dao.allHistory().blockingFirst()

        assertEquals(1, history.size.toLong())

        val actual = history[0].detailedVenueWithPhotos!!.place
        assertTrue(detailedVenueWithPhotos.place == actual)
    }

    @Test
    fun shouldRemoveFromHistory() {
        val detailedVenue = FakesStore.getVenue("1")
        val photoDbEntities = FakesStore.listPhotos(detailedVenue.id)

        val detailedVenueWithPhotos = PlaceAndPhotos(detailedVenue)
        detailedVenueWithPhotos.photos = photoDbEntities
        detailedVenueWithPhotosDao.insertOrReplace(detailedVenueWithPhotos.place, detailedVenueWithPhotos.photos)

        val detailedVenueHistoryDbEntity = DetailedVenueHistoryDbEntity(
                placeId = detailedVenue.id,
                createdAt = System.currentTimeMillis(),
                isLastCheckIn = false
        )
        dao.insertOrReplace(detailedVenueHistoryDbEntity)

        dao.remove(detailedVenueHistoryDbEntity.placeId)

        val history = dao.allHistory().blockingFirst()
        val allVenus = detailedVenueWithPhotosDao.streamPlaces().blockingFirst()

        //check if deleting place from history do not delete it from detail_venue table
        assertEquals(1, allVenus.size.toLong())
        assertTrue(history.isEmpty())
    }

}
