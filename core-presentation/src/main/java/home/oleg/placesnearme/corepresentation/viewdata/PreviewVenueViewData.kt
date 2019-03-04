package home.oleg.placesnearme.corepresentation.viewdata

import home.oleg.placesnearme.coredomain.models.Section

interface PreviewVenueViewData {
    val id: String
    val title: String
    val distance: Double
    val address: String
    val lat: Double
    val lng: Double
    val iconViewData: IconViewData?
    val categoryName: String?
    val sectionType: Section?

    val categoryIconUrl: String
        get() = iconViewData!!.iconUrlGray

}