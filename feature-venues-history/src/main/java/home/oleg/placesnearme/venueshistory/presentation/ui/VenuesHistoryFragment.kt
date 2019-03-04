package home.oleg.placesnearme.venueshistory.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.smedialink.common.base.BaseFragment
import com.smedialink.common.ext.observe
import com.smedialink.common.recyclerview.ItemViewType
import home.oleg.placesnearme.corepresentation.api.ShowVenueDetail
import home.oleg.placesnearme.corepresentation.viewdata.VenueViewData
import home.oleg.placesnearme.venueshistory.R
import home.oleg.placesnearme.venueshistory.di.VenueHistoryComponent
import home.oleg.placesnearme.venueshistory.presentation.VenuesHistoryViewModel
import kotlinx.android.synthetic.main.fragment_places_history.*
import javax.inject.Inject

class VenuesHistoryFragment : BaseFragment(), HistoryVenuesAdapter.HistoryClicksListener {

    override val layoutRes: Int = R.layout.fragment_places_history

    @Inject
    lateinit var historyVenuesAdapter: HistoryVenuesAdapter

    @Inject
    lateinit var placesHistoryViewModel: VenuesHistoryViewModel

    private lateinit var showVenueDetail: ShowVenueDetail

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ShowVenueDetail) {
            this.showVenueDetail = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvHistoryVenues.layoutManager = LinearLayoutManager(context)
        rvHistoryVenues.setHasFixedSize(true)
        rvHistoryVenues.adapter = historyVenuesAdapter

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)?.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setTitle(R.string.history_title)
        }

        placesHistoryViewModel.state.observe(this, historyVenuesAdapter::submitList)
    }

    override fun favoriteClicked(venueViewData: VenueViewData) {
        placesHistoryViewModel.updateCheckIn(venueViewData)
    }

    override fun onItemClicked(venueViewData: VenueViewData) {
        showVenueDetail.showVenueDetail(venueViewData)
    }

    override fun inject() {
        VenueHistoryComponent.Injector.inject(this)
    }

}
