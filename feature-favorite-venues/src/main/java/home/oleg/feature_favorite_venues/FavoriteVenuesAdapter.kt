package home.oleg.feature_favorite_venues

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import home.oleg.placesnearme.core_presentation.recyclerview.BaseVenueAdapter
import home.oleg.placesnearme.core_presentation.recyclerview.ItemViewType
import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem
import home.oleg.placesnearme.core_presentation.utils.ImageLoader
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import kotlinx.android.synthetic.main.item_favorite_venue.view.*

class FavoriteVenuesAdapter(diffCallback: DiffUtil.ItemCallback<ItemViewType>,
                            private val favoriteClicksListener: FavoriteVenuesAdapter.FavoriteClicksListener) : BaseVenueAdapter(diffCallback) {

    interface FavoriteClicksListener {
        fun favoriteClicked(venueViewData: VenueViewData)

        fun onItemClicked(venueViewData: VenueViewData)
    }

    override fun getVenueItemHolder(view: View): BaseVenueAdapter.VenueItemHolder {
        return FavoriteVenuesAdapter.ViewHolder(view, favoriteClicksListener)
    }

    override fun getVenueItemLayoutRes(): Int {
        return R.layout.item_favorite_venue
    }

    override fun getEmptyItemLayoutRes(): Int {
        return R.layout.item_empty
    }

    private class ViewHolder constructor(itemView: View,
                                         private val favoriteClicksListener: FavoriteVenuesAdapter.FavoriteClicksListener) : BaseVenueAdapter.VenueItemHolder(itemView) {

        override fun bind(venueViewItem: VenueViewItem) {
            val venueViewData = venueViewItem.venueViewData
            with(itemView) {
                tvName.text = venueViewData.title
                fabAddToFavorite.isSelected = venueViewData.isFavorite
                fabAddToFavorite.setOnClickListener { favoriteClicksListener.favoriteClicked(venueViewData) }

                itemView.setOnClickListener { favoriteClicksListener.onItemClicked(venueViewData) }

                if (venueViewData.bestPhoto != null) {
                    ImageLoader.loadImage(ivPhoto, venueViewData.bestPhoto!!.fullSizeUrl)
                } else {
                    ImageLoader.loadImage(ivPhoto, null)
                }

                tvAddress.text = venueViewData.address
            }
        }
    }

}
