package home.oleg.placesnearme.coredata.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import home.oleg.placesnearme.coredata.FakesStore;
import home.oleg.placesnearme.coredata.database.AppDatabase;
import home.oleg.placesnearme.coredata.model.DetailedVenueDbEntity;
import home.oleg.placesnearme.coredata.model.DetailedVenueHistory;
import home.oleg.placesnearme.coredata.model.DetailedVenueHistoryDbEntity;
import home.oleg.placesnearme.coredata.model.DetailedVenueWithPhotos;
import home.oleg.placesnearme.coredata.model.PhotoDbEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class VenueHistoryDaoTest {

    private DetailedVenueDao detailedVenueWithPhotosDao;
    private DetailedVenueHistoryDao dao;

    @Before
    public void setUp() {
        AppDatabase appDatabase = Room
                .inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase.class)
                .build();
        dao = appDatabase.getVenueHistoryDao();
        detailedVenueWithPhotosDao = appDatabase.getDetailedVenueWithPhotosDao();
    }

    @Test
    public void shouldAddToHistory() {
        DetailedVenueWithPhotos detailedVenueWithPhotos = new DetailedVenueWithPhotos();

        DetailedVenueDbEntity detailedVenue = FakesStore.getVenue("1");
        List<PhotoDbEntity> photoDbEntities = FakesStore.getListPhotos();

        detailedVenueWithPhotos.setPhotos(photoDbEntities);
        detailedVenueWithPhotos.setVenue(detailedVenue);
        detailedVenueWithPhotosDao.insert(detailedVenueWithPhotos);

        DetailedVenueHistoryDbEntity detailedVenueHistoryDbEntity = new DetailedVenueHistoryDbEntity();
        detailedVenueHistoryDbEntity.setCreatedAt(123);
        detailedVenueHistoryDbEntity.setVenueId(detailedVenue.getId());
        dao.insert(detailedVenueHistoryDbEntity);

        List<DetailedVenueHistory> history = dao.getAllHistory().blockingFirst();

        assertEquals(1, history.size());

        DetailedVenueDbEntity actual = history.get(0).getDetailedVenueWithPhotos().getVenue();
        assertTrue(detailedVenueWithPhotos.getVenue().equals(actual));
    }

    @Test
    public void shouldRemoveFromHistory() {
        DetailedVenueWithPhotos detailedVenueWithPhotos = new DetailedVenueWithPhotos();

        DetailedVenueDbEntity detailedVenue = FakesStore.getVenue("1");
        List<PhotoDbEntity> photoDbEntities = FakesStore.getListPhotos();

        detailedVenueWithPhotos.setPhotos(photoDbEntities);
        detailedVenueWithPhotos.setVenue(detailedVenue);
        detailedVenueWithPhotosDao.insert(detailedVenueWithPhotos);

        DetailedVenueHistoryDbEntity detailedVenueHistoryDbEntity = new DetailedVenueHistoryDbEntity();
        detailedVenueHistoryDbEntity.setCreatedAt(123);
        detailedVenueHistoryDbEntity.setVenueId(detailedVenue.getId());
        dao.insert(detailedVenueHistoryDbEntity);

        dao.remove(detailedVenueHistoryDbEntity.getVenueId());

        List<DetailedVenueHistory> history = dao.getAllHistory().blockingFirst();
        List<DetailedVenueWithPhotos> allVenus = detailedVenueWithPhotosDao.getAll().blockingFirst();

        //check if deleting venue from history do not delete it from detail_venue table
        assertEquals(1, allVenus.size());
        assertTrue(history.isEmpty());
    }

    @Test
    public void shouldReturnLatestFromHistory() {
        //insert first to detail venue
        DetailedVenueWithPhotos detailedVenueWithPhotos = new DetailedVenueWithPhotos();
        DetailedVenueDbEntity detailedVenue = FakesStore.getVenue("1");
        List<PhotoDbEntity> photoDbEntities = FakesStore.getListPhotos();

        detailedVenueWithPhotos.setPhotos(photoDbEntities);
        detailedVenueWithPhotos.setVenue(detailedVenue);
        detailedVenueWithPhotosDao.insert(detailedVenueWithPhotos);

        //insert second to detail venue
        DetailedVenueWithPhotos detailedVenueWithPhotos2 = new DetailedVenueWithPhotos();
        DetailedVenueDbEntity detailedVenue2 = FakesStore.getVenue("2");
        List<PhotoDbEntity> photoDbEntities2 = FakesStore.getListPhotos();
        detailedVenueWithPhotos2.setPhotos(photoDbEntities2);
        detailedVenueWithPhotos2.setVenue(detailedVenue2);
        detailedVenueWithPhotosDao.insert(detailedVenueWithPhotos2);

        //insert first to history
        DetailedVenueHistoryDbEntity detailedVenueHistoryDbEntity1 = new DetailedVenueHistoryDbEntity();
        detailedVenueHistoryDbEntity1.setCreatedAt(System.currentTimeMillis());
        detailedVenueHistoryDbEntity1.setVenueId(detailedVenue.getId());
        dao.insert(detailedVenueHistoryDbEntity1);

        //insert second to history(latest)
        DetailedVenueHistoryDbEntity detailedVenueHistoryDbEntity2 = new DetailedVenueHistoryDbEntity();
        detailedVenueHistoryDbEntity2.setCreatedAt(System.currentTimeMillis()); // --> latest
        detailedVenueHistoryDbEntity2.setVenueId(detailedVenue2.getId());
        dao.insert(detailedVenueHistoryDbEntity2);

        List<DetailedVenueHistory> history = dao.getAllHistory().blockingFirst();
        DetailedVenueHistory current = dao.getCurrent().blockingGet();

        assertNotNull(current);
        assertEquals(detailedVenue2.getId(), current.getDetailedVenueWithPhotos().getVenue().getId());
    }
}
