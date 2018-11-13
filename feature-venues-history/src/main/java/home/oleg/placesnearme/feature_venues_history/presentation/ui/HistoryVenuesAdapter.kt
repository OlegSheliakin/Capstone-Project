package home.oleg.placesnearme.feature_venues_history.presentation.ui

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import home.oleg.placesnearme.core_presentation.recyclerview.BaseVenueAdapter
import home.oleg.placesnearme.core_presentation.recyclerview.ItemViewType
import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem
import home.oleg.placesnearme.core_presentation.utils.DistanceUtil
import home.oleg.placesnearme.core_presentation.utils.ImageLoader
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import home.oleg.placesnearme.feature_venues_history.R
import kotlinx.android.synthetic.main.item_history_venue.view.*

class HistoryVenuesAdapter(
        diffCallback: DiffUtil.ItemCallback<ItemViewType>,
        private val favoriteClicksListener: HistoryClicksListener) : BaseVenueAdapter(diffCallback) {

    override val venueItemLayoutRes: Int = R.layout.item_history_venue

    override val emptyItemLayoutRes: Int = R.layout.item_empty

    interface HistoryClicksListener {
        fun favoriteClicked(venueViewData: VenueViewData)

        fun onItemClicked(venueViewData: VenueViewData)
    }

    override fun getVenueItemHolder(view: View): BaseVenueAdapter.VenueItemHolder {
        return ViewHolder(view, favoriteClicksListener)
    }

    private class ViewHolder constructor(
            itemView: View,
            private val historyClicksListener: HistoryClicksListener) : BaseVenueAdapter.VenueItemHolder(itemView) {

        override fun bind(venueViewItem: VenueViewItem) {
            val venueViewData = venueViewItem.venueViewData
            with(itemView) {
                tvVenueName.text = venueViewData.title
                fabAddToFavorite.isSelected = venueViewData.isFavorite

                val distance = DistanceUtil.convertDistanceTOString(venueViewData.distance, itemView.context)
                tvVenueDistance.text = distance

                tvCategoryName.text = venueViewData.categoryName
                ratingBar.rating = venueViewData.adoptedRating

                fabAddToFavorite.setOnClickListener { _ -> historyClicksListener.favoriteClicked(venueViewData) }
                itemView.setOnClickListener { _ -> historyClicksListener.onItemClicked(venueViewData) }

                if (venueViewData.bestPhoto != null) {
                    ImageLoader.loadIcon(ivVenueIcon, venueViewData.categoryIconUrl)
                } else {
                    ImageLoader.loadIcon(ivVenueIcon, null)
                }

                tvVenueAddress.text = venueViewData.address
            }
        }
    }

}
