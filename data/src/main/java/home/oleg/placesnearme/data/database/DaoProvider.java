package home.oleg.placesnearme.data.database;

import home.oleg.placesnearme.data.dao.DetailedVenueDao;
import home.oleg.placesnearme.data.dao.DetailedVenueHistoryDao;

public interface DaoProvider {
    DetailedVenueDao getDetailedVenueWithPhotosDao();
    DetailedVenueHistoryDao getVenueHistoryDao();
}
