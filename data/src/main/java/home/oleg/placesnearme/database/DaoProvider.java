package home.oleg.placesnearme.database;

import home.oleg.placesnearme.dao.DetailedVenueWithPhotosDao;

public interface DaoProvider {
    DetailedVenueWithPhotosDao getDetailedVenueWithPhotosDao();
}
