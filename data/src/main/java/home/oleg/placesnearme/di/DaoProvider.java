package home.oleg.placesnearme.di;

import home.oleg.placesnearme.dao.DetailedVenueWithPhotosDao;

public interface DaoProvider {
    DetailedVenueWithPhotosDao getDetailedVenueWithPhotosDao();
}
