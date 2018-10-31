package home.oleg.placesnearme.core_presentation.viewdata

import home.oleg.placenearme.models.Section

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