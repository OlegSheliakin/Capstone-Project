package home.oleg.placesnearme.coredata.dao

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import home.oleg.placesnearme.coredata.FakesStore
import home.oleg.placesnearme.coredata.database.AppDatabase
import home.oleg.placesnearme.coredata.model.PlaceAndPhotos
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailedVenueWithPhotosDaoTes {

    private lateinit var appDatabase: AppDatabase
    private lateinit var dao: PlacesDao

    @Before
    fun setUp() {
        appDatabase = Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase::class.java)
                .build()
        dao = appDatabase.detailedVenueWithPhotosDao
    }

    @Test
    fun shouldBeEmpty() {
        val actual = dao.streamPlaces().blockingFirst()

        assertEquals(0, actual.size.toLong())
    }

    @Test
    fun shouldInsertVenueAndPhotos() {
        val detailedVenue = FakesStore.getVenue("1")
        val detailedVenueWithPhotos = PlaceAndPhotos(detailedVenue)
        detailedVenueWithPhotos.photos = FakesStore.listPhotos(detailedVenue.id)

        dao.insertOrReplace(detailedVenueWithPhotos.venue, detailedVenueWithPhotos.photos)

        val actual = dao.streamPlaces().blockingFirst()

        assertEquals(1, actual.size.toLong())
        assertEquals(detailedVenueWithPhotos.photos.size.toLong(), actual[0].photos.size.toLong())
    }

    @Test
    fun shouldReplaceVenueWhenInsertWithTheSameId() {
        val detailedVenue = FakesStore.getVenue("1")
        val detailedVenueWithPhotos = PlaceAndPhotos(detailedVenue)
        detailedVenueWithPhotos.photos = FakesStore.listPhotos(detailedVenue.id)

        //double inserting
        dao.insertOrReplace(detailedVenueWithPhotos.venue, detailedVenueWithPhotos.photos)
        dao.insertOrReplace(detailedVenueWithPhotos.venue, detailedVenueWithPhotos.photos)

        val detailedVenueWithPhotos1 = dao.streamPlaces().blockingFirst()

        assertEquals(1, detailedVenueWithPhotos1.size.toLong())
    }

    @Test
    fun photosShouldBeEmptyAfterReplaceVenue() {
        val detailedVenue = FakesStore.getVenue("1")
        val detailedVenueWithPhotos = PlaceAndPhotos(detailedVenue)
        detailedVenueWithPhotos.photos = FakesStore.listPhotos(detailedVenue.id)

        dao.insertOrReplace(detailedVenueWithPhotos.venue, detailedVenueWithPhotos.photos)

        //clear photos
        detailedVenueWithPhotos.photos = emptyList()
        dao.insertOrReplace(detailedVenueWithPhotos.venue, detailedVenueWithPhotos.photos)

        val actual = appDatabase.detailedVenueWithPhotosDao.streamPhotos().blockingFirst()

        assertEquals(0, actual.size.toLong())
    }

    @Test
    fun photosShouldBeEmptyAfterDeleteVenue() {
        val detailedVenue = FakesStore.getVenue("1")
        val detailedVenueWithPhotos = PlaceAndPhotos(detailedVenue)
        detailedVenueWithPhotos.photos = FakesStore.listPhotos(detailedVenue.id)

        dao.insertOrReplace(detailedVenueWithPhotos.venue, detailedVenueWithPhotos.photos)

        dao.deleteAll()

        val actual = appDatabase.detailedVenueWithPhotosDao.streamPhotos().blockingFirst()

        assertEquals(0, actual.size.toLong())
    }

    @Test
    fun shouldClearAllVenues() {

        val detailedVenue = FakesStore.getVenue("1")
        val detailedVenue1 = FakesStore.getVenue("2")

        val detailedVenueWithPhotos = PlaceAndPhotos(detailedVenue)
        detailedVenueWithPhotos.photos =  FakesStore.listPhotos(detailedVenue.id)

        val detailedVenueWithPhotos1 = PlaceAndPhotos(detailedVenue1)
        detailedVenueWithPhotos1.photos = FakesStore.listPhotos(detailedVenue1.id)

        dao.insertOrReplace(detailedVenueWithPhotos.venue, detailedVenueWithPhotos.photos)
        dao.insertOrReplace(detailedVenueWithPhotos.venue, detailedVenueWithPhotos.photos)
        dao.deleteAll()

        val actualPhotos = appDatabase.detailedVenueWithPhotosDao.streamPhotos().blockingFirst()
        val actualVenues = dao.streamPlaces().blockingFirst()

        assertEquals(0, actualPhotos.size.toLong())
        assertEquals(0, actualVenues.size.toLong())
    }

    @Test
    fun shouldUpdateToFavoriteVenue() {
        val detailedVenue = FakesStore.getVenue("1").copy(isFavorite = true)
        val detailedVenueWithPhotos = PlaceAndPhotos(detailedVenue)
        detailedVenueWithPhotos.photos = FakesStore.listPhotos(detailedVenue.id)

        dao.insertOrReplace(detailedVenueWithPhotos.venue, detailedVenueWithPhotos.photos)

        val detailedVenueDbEntity = dao.getPlaceById(detailedVenue.id)!!.copy(isFavorite = false)

        dao.update(detailedVenueDbEntity)

        val actualVenue = dao.getById(detailedVenue.id).blockingGet()

        assertEquals(1, actualVenue.photos.size.toLong())
        assertEquals(false, actualVenue.venue.isFavorite)
    }

    @Test
    fun shouldChangeToNotFavoriteVenue() {
        val detailedVenue = FakesStore.getVenue("1").copy(isFavorite = true)
        val detailedVenueWithPhotos = PlaceAndPhotos(detailedVenue)
        detailedVenueWithPhotos.photos = FakesStore.listPhotos(detailedVenue.id)

        dao.insertOrReplace(detailedVenueWithPhotos.venue, detailedVenueWithPhotos.photos)

        detailedVenueWithPhotos.venue = detailedVenueWithPhotos.venue.copy(isFavorite = false)
        dao.insertOrReplace(detailedVenueWithPhotos.venue, detailedVenueWithPhotos.photos)

        val (venue) = dao!!.getById("1").blockingGet()

        assertFalse(venue.isFavorite)
    }

}
