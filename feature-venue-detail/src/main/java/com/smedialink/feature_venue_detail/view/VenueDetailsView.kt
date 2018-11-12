package com.smedialink.feature_venue_detail.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.smedialink.feature_venue_detail.R
import home.oleg.placesnearme.core_presentation.utils.ImageLoader
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
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

    fun setRetryClickListener(retryClickListener: () -> Unit) {
        retryButton.setOnClickListener {
            retryClickListener()
        }
    }

    fun show(venue: VenueViewData) {
        tvError.visibility = View.GONE
        retryButton.visibility = View.GONE
        spinKit.visibility = View.GONE

        tvVenueDescription.text = venue.description
        tvContacts.text = venue.formattedPhone
        ratingBar.rating = venue.adoptedRating

        photosAdapter.setItems(venue.photos)

        tvVenueName.text = venue.title
        tvVenueAddress.text = venue.address

        val distance = context.getString(R.string.meters, venue.distance)
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

    fun showLoading() {
        content.visibility = View.GONE
        tvError.visibility = View.GONE
        retryButton.visibility = View.GONE
        spinKit.visibility = View.VISIBLE
    }

    fun hideLoading() {
        content.visibility = View.VISIBLE
        tvError.visibility = View.GONE
        retryButton!!.visibility = View.GONE
        spinKit.visibility = View.GONE
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

        content.visibility = View.INVISIBLE

        rvPhotos.layoutManager = LinearLayoutManager(this.context)
        rvPhotos.adapter = photosAdapter
    }

}
