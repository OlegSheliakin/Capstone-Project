package home.oleg.placesnearme.corepresentation.recyclerview

import home.oleg.placesnearme.corepresentation.viewdata.PlaceViewData
import java.util.*

data class VenueViewItem(val venueViewData: PlaceViewData) : com.smedialink.common.recyclerview.ItemViewType {

    override fun getViewType(): Int {
        return VIEW_TYPE
    }

    companion object {

        var VIEW_TYPE = 1

        fun map(venueViewDataList: List<PlaceViewData>): List<VenueViewItem> {
            val venueViewItems = ArrayList<VenueViewItem>()

            for (venueViewData in venueViewDataList) {
                venueViewItems.add(VenueViewItem(venueViewData))
            }

            return venueViewItems
        }
    }
}
