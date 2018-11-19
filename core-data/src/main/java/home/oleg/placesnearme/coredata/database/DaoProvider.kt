package home.oleg.placesnearme.coredata.database

import home.oleg.placesnearme.coredata.dao.DetailedVenueDao
import home.oleg.placesnearme.coredata.dao.DetailedVenueHistoryDao

interface DaoProvider {
    val detailedVenueWithPhotosDao: DetailedVenueDao
    val venueHistoryDao: DetailedVenueHistoryDao
}
