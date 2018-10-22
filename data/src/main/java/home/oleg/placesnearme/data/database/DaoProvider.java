package home.oleg.placesnearme.data.database;

import home.oleg.placesnearme.data.dao.DetailedVenueWithPhotosDao;
import home.oleg.placesnearme.data.dao.VenueHistoryDao;

public interface DaoProvider {
    DetailedVenueWithPhotosDao getDetailedVenueWithPhotosDao();
    VenueHistoryDao getVenueHistoryDao();
}
