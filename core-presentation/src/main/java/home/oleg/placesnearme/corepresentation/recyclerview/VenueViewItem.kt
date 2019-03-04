package home.oleg.placesnearme.corepresentation.recyclerview

import home.oleg.placesnearme.corepresentation.viewdata.VenueViewData
import java.util.*

data class VenueViewItem(val venueViewData: VenueViewData) : com.smedialink.common.recyclerview.ItemViewType {

    override fun getViewType(): Int {
        return VIEW_TYPE
    }

    companion object {

        var VIEW_TYPE = 1

        fun map(venueViewDataList: List<VenueViewData>): List<VenueViewItem> {
            val venueViewItems = ArrayList<VenueViewItem>()

            for (venueViewData in venueViewDataList) {
                venueViewItems.add(VenueViewItem(venueViewData))
            }

            return venueViewItems
        }
    }
}
