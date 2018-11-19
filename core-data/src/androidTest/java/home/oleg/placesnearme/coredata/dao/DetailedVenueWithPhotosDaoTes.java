package home.oleg.placesnearme.coredata.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import home.oleg.placesnearme.coredata.FakesStore;
import home.oleg.placesnearme.coredata.database.AppDatabase;
import home.oleg.placesnearme.coredata.model.DetailedVenueDbEntity;
import home.oleg.placesnearme.coredata.model.DetailedVenueWithPhotos;
import home.oleg.placesnearme.coredata.model.PhotoDbEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class DetailedVenueWithPhotosDaoTes {

    private AppDatabase appDatabase;
    private DetailedVenueDao dao;

    @Before
    public void setUp() {
        appDatabase = Room
                .inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase.class)
                .build();
        dao = appDatabase.getDetailedVenueWithPhotosDao();
    }

    @Test
    public void shouldBeEmpty() {
        List<DetailedVenueWithPhotos> actual = dao.getAll().blockingFirst();

        assertEquals(0, actual.size());
    }

    @Test
    public void shouldInsertVenueAndPhotos() {
        DetailedVenueWithPhotos detailedVenueWithPhotos = new DetailedVenueWithPhotos();

        DetailedVenueDbEntity detailedVenue = FakesStore.getVenue("1");
        List<PhotoDbEntity> photoDbEntities = FakesStore.getListPhotos();

        detailedVenueWithPhotos.setVenue(detailedVenue);
        detailedVenueWithPhotos.setPhotos(photoDbEntities);

        dao.insert(detailedVenueWithPhotos);

        List<DetailedVenueWithPhotos> actual = dao.getAll().blockingFirst();

        assertEquals(1, actual.size());
        assertEquals(photoDbEntities.size(), actual.get(0).getPhotos().size());
    }

    @Test
    public void shouldReplaceVenueWhenInsertWithTheSameId() {
        DetailedVenueWithPhotos detailedVenueWithPhotos = new DetailedVenueWithPhotos();

        DetailedVenueDbEntity detailedVenue = FakesStore.getVenue("1");
        List<PhotoDbEntity> photoDbEntities = FakesStore.getListPhotos();

        detailedVenueWithPhotos.setPhotos(photoDbEntities);
        detailedVenueWithPhotos.setVenue(detailedVenue);

        //double inserting
        dao.insert(detailedVenueWithPhotos);
        dao.insert(detailedVenueWithPhotos);

        List<DetailedVenueWithPhotos> detailedVenueWithPhotos1 = dao.getAll().blockingFirst();

        assertEquals(1, detailedVenueWithPhotos1.size());
    }

    @Test
    public void photosShouldBeEmptyAfterReplaceVenue() {
        DetailedVenueWithPhotos detailedVenueWithPhotos = new DetailedVenueWithPhotos();

        DetailedVenueDbEntity detailedVenue = FakesStore.getVenue("1");
        List<PhotoDbEntity> photoDbEntities = FakesStore.getListPhotos();

        detailedVenueWithPhotos.setPhotos(photoDbEntities);
        detailedVenueWithPhotos.setVenue(detailedVenue);
        dao.insert(detailedVenueWithPhotos);

        //clear photos
        detailedVenueWithPhotos.setPhotos(Collections.emptyList());
        dao.insert(detailedVenueWithPhotos);

        List<PhotoDbEntity> actual = appDatabase.getDetailedVenueWithPhotosDao().getAllPhotos().blockingFirst();

        assertEquals(0, actual.size());
    }

    @Test
    public void photosShouldBeEmptyAfterDeleteVenue() {
        DetailedVenueWithPhotos detailedVenueWithPhotos = new DetailedVenueWithPhotos();

        DetailedVenueDbEntity detailedVenue = FakesStore.getVenue("1");
        List<PhotoDbEntity> photoDbEntities = FakesStore.getListPhotos();

        detailedVenueWithPhotos.setPhotos(photoDbEntities);
        detailedVenueWithPhotos.setVenue(detailedVenue);
        dao.insert(detailedVenueWithPhotos);
        dao.deleteAll();

        List<PhotoDbEntity> actual = appDatabase.getDetailedVenueWithPhotosDao().getAllPhotos().blockingFirst();

        assertEquals(0, actual.size());
    }

    @Test
    public void shouldClearAllVenues() {
        DetailedVenueWithPhotos detailedVenueWithPhotos = new DetailedVenueWithPhotos();
        DetailedVenueWithPhotos detailedVenueWithPhotos1 = new DetailedVenueWithPhotos();

        DetailedVenueDbEntity detailedVenue = FakesStore.getVenue("1");
        DetailedVenueDbEntity detailedVenue1 = FakesStore.getVenue("2");
        List<PhotoDbEntity> photoDbEntities = FakesStore.getListPhotos();

        detailedVenueWithPhotos.setPhotos(photoDbEntities);
        detailedVenueWithPhotos.setVenue(detailedVenue);

        detailedVenueWithPhotos1.setVenue(detailedVenue1);
        detailedVenueWithPhotos1.setPhotos(photoDbEntities);

        dao.insert(detailedVenueWithPhotos);
        dao.insert(detailedVenueWithPhotos1);
        dao.deleteAll();

        List<PhotoDbEntity> actualPhotos = appDatabase.getDetailedVenueWithPhotosDao().getAllPhotos().blockingFirst();
        List<DetailedVenueWithPhotos> actualVenues = dao.getAll().blockingFirst();

        assertEquals(0, actualPhotos.size());
        assertEquals(0, actualVenues.size());
    }

    @Test
    public void shouldUpdateToFavoriteVenue() {
        DetailedVenueWithPhotos detailedVenueWithPhotos = new DetailedVenueWithPhotos();

        DetailedVenueDbEntity detailedVenue = FakesStore.getVenue("1");
        detailedVenue.setFavorite(true);

        List<PhotoDbEntity> photoDbEntities = FakesStore.getListPhotos();

        detailedVenueWithPhotos.setPhotos(photoDbEntities);
        detailedVenueWithPhotos.setVenue(detailedVenue);
        dao.insert(detailedVenueWithPhotos);

        DetailedVenueDbEntity detailedVenueDbEntity = dao.getDetailedVenueById(detailedVenue.getId());
        detailedVenueDbEntity.setFavorite(false);
        dao.update(detailedVenueDbEntity);

        DetailedVenueWithPhotos actualVenue = dao.getById(detailedVenue.getId()).blockingGet();

        assertEquals(1, actualVenue.getPhotos().size());
        assertEquals(false, actualVenue.getVenue().isFavorite());
    }

    @Test
    public void shouldChangeToNotFavoriteVenue() {
        DetailedVenueWithPhotos detailedVenueWithPhotos = new DetailedVenueWithPhotos();

        DetailedVenueDbEntity detailedVenue = FakesStore.getVenue("1");
        detailedVenue.setFavorite(true);

        List<PhotoDbEntity> photoDbEntities = FakesStore.getListPhotos();

        detailedVenueWithPhotos.setPhotos(photoDbEntities);
        detailedVenueWithPhotos.setVenue(detailedVenue);
        dao.insert(detailedVenueWithPhotos);

        detailedVenueWithPhotos.getVenue().setFavorite(false);
        dao.insert(detailedVenueWithPhotos);

        DetailedVenueWithPhotos actualVenue = dao.getById("1").blockingGet();

        assertFalse(actualVenue.getVenue().isFavorite());
    }

}