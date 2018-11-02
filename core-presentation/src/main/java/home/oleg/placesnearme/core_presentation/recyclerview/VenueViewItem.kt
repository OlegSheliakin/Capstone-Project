package home.oleg.placesnearme.core_presentation.recyclerview

import java.util.ArrayList
import java.util.Objects

import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData

data class VenueViewItem(val venueViewData: VenueViewData) : ItemViewType {

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
