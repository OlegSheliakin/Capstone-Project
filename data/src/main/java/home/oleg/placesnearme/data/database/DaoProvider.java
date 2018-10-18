package home.oleg.placesnearme.data.database;

import home.oleg.placesnearme.data.dao.DetailedVenueWithPhotosDao;

public interface DaoProvider {
    DetailedVenueWithPhotosDao getDetailedVenueWithPhotosDao();
}
