package home.oleg.placesnearme.favoritevenues.presentation.ui

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.smedialink.common.recyclerview.ItemViewType
import home.oleg.placesnearme.corepresentation.recyclerview.BaseVenueAdapter
import home.oleg.placesnearme.corepresentation.recyclerview.VenueViewItem
import home.oleg.placesnearme.corepresentation.utils.ImageLoader
import home.oleg.placesnearme.corepresentation.viewdata.PlaceViewData
import home.oleg.placesnearme.favoritevenues.R
import kotlinx.android.synthetic.main.item_favorite_venue.view.*

class FavoriteVenuesAdapter(diffCallback: DiffUtil.ItemCallback<ItemViewType>,
                            private val favoriteClicksListener: FavoriteClicksListener) : BaseVenueAdapter(diffCallback) {

    override val venueItemLayoutRes: Int= R.layout.item_favorite_venue

    override val emptyItemLayoutRes: Int = R.layout.item_empty

    interface FavoriteClicksListener {
        fun favoriteClicked(venueViewData: PlaceViewData)

        fun onItemClicked(venueViewData: PlaceViewData)
    }

    override fun getVenueItemHolder(view: View): BaseVenueAdapter.VenueItemHolder {
        return ViewHolder(view, favoriteClicksListener)
    }

    private class ViewHolder
    constructor(itemView: View,
                private val favoriteClicksListener: FavoriteClicksListener)
        : BaseVenueAdapter.VenueItemHolder(itemView) {

        override fun bind(venueViewItem: VenueViewItem) {
            val venueViewData = venueViewItem.venueViewData
            with(itemView) {
                tvName.text = venueViewData.title
                fabAddToFavorite.isSelected = venueViewData.isFavorite
                fabAddToFavorite.setOnClickListener {
                    favoriteClicksListener.favoriteClicked(venueViewData)
                }

                itemView.setOnClickListener { favoriteClicksListener.onItemClicked(venueViewData) }

                if (venueViewData.bestPhoto != null) {
                    ImageLoader.loadImage(ivPhoto, venueViewData.bestPhoto?.fullSizeUrl)
                } else {
                    ImageLoader.loadImage(ivPhoto, null)
                }

                tvAddress.text = venueViewData.address
            }
        }
    }

}
