package home.oleg.placesnearme.coredata.database

import home.oleg.placesnearme.coredata.dao.PlacesDao
import home.oleg.placesnearme.coredata.dao.DetailedVenueHistoryDao

interface DaoProvider {
    val detailedVenueWithPhotosDao: PlacesDao
    val venueHistoryDao: DetailedVenueHistoryDao
}
