package home.oleg.placesnearme.feature_place_detail.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.oleg.placesnearme.feature_place_detail.R
import com.smedialink.common.ext.gone
import home.oleg.placesnearme.corepresentation.utils.DistanceUtil
import home.oleg.placesnearme.corepresentation.utils.ImageLoader
import home.oleg.placesnearme.corepresentation.viewdata.PlaceViewData
import kotlinx.android.synthetic.main.content_venue_details.view.*
import kotlinx.android.synthetic.main.view_venue_details.view.*

/**
 * Created by Oleg Sheliakin on 20.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class VenueDetailsView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    private val photosAdapter = PhotosAdapter()

    init {
        init()
    }

    var isLoading: Boolean = false
        set(value) {
            field = value
            content.gone(value)
            tvError.gone(value)
            retryButton.gone(value)
            spinKit.gone(!value)
        }

    fun setRetryClickListener(retryClickListener: () -> Unit) {
        retryButton.setOnClickListener {
            retryClickListener()
        }
    }

    fun show(venue: PlaceViewData) {
        tvVenueDescription.text = venue.description
        tvContacts.text = venue.formattedPhone
        ratingBar.rating = venue.adoptedRating

        photosAdapter.setItems(venue.photos)

        tvVenueName.text = venue.title
        tvVenueAddress.text = venue.address

        val distance = DistanceUtil.convertDistanceToString(venue.distance, context)
        tvVenueDistance.text = distance

        tvCategoryName.text = venue.categoryName

        val url = venue.iconViewData?.iconUrlGray

        ImageLoader.loadIcon(ivVenueIcon, url)

        content.visibility = View.VISIBLE
    }

    fun clearContent() {
        tvVenueName.text = ""
        tvVenueAddress.text = ""
        tvVenueDescription.text = ""
        tvContacts.text = ""
        ratingBar.rating = 0f
        photosAdapter.setItems(emptyList())
        tvError.text = ""
    }

    fun showError(text: String) {
        content.visibility = View.GONE
        retryButton.visibility = View.VISIBLE
        tvError.visibility = View.VISIBLE
        tvError.text = text
        spinKit.visibility = View.GONE
    }

    private fun init() {
        LayoutInflater.from(context)
                .inflate(R.layout.view_venue_details, this, true)

        content.visibility = View.GONE

        rvPhotos.layoutManager = LinearLayoutManager(this.context)
        rvPhotos.adapter = photosAdapter
    }

}
