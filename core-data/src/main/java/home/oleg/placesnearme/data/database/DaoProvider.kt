package home.oleg.placesnearme.data.database

import home.oleg.placesnearme.data.dao.DetailedVenueDao
import home.oleg.placesnearme.data.dao.DetailedVenueHistoryDao

interface DaoProvider {
    val detailedVenueWithPhotosDao: DetailedVenueDao
    val venueHistoryDao: DetailedVenueHistoryDao
}
