package home.oleg.placesnearme.feature_venues_history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smedialink.feature_add_favorite.CreateFavoriteViewModel
import home.oleg.placesnearme.core_presentation.ShowHideBottomBarListener
import home.oleg.placesnearme.core_presentation.base.BaseFragment
import home.oleg.placesnearme.core_presentation.extensions.observeX
import home.oleg.placesnearme.core_presentation.recyclerview.ItemViewType
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import home.oleg.placesnearme.feature_venues_history.di.VenueHistoryComponent
import kotlinx.android.synthetic.main.fragment_places_history.*
import javax.inject.Inject

class VenuesHistoryFragment : BaseFragment(), HistoryVenuesAdapter.HistoryClicksListener {

    override val layoutRes: Int = R.layout.fragment_places_history

    @Inject
    lateinit var historyVenuesAdapter: HistoryVenuesAdapter

    @Inject
    lateinit var placesHistoryViewModel: VenuesHistoryViewModel

    @Inject
    lateinit var createFavoriteViewModel: CreateFavoriteViewModel

    lateinit var showHideBottomBarListener: ShowHideBottomBarListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        //todo -> navigator
        if (context is ShowHideBottomBarListener) {
            this.showHideBottomBarListener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvHistoryVenues.layoutManager = LinearLayoutManager(context)
        rvHistoryVenues.setHasFixedSize(true)
        historyVenuesAdapter.setHasStableIds(true)

        rvHistoryVenues.adapter = historyVenuesAdapter

        //todo -> toolbar title manager
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)?.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setTitle(R.string.history_title)
        }

        placesHistoryViewModel.state.observeX(this) { venues ->
            if (venues.isEmpty()) {
                historyVenuesAdapter.showEmpty()
            } else {
                val list: List<ItemViewType> = venues
                historyVenuesAdapter.submitList(list)

            }
        }
    }

    override fun favoriteClicked(venueViewData: VenueViewData) {
        createFavoriteViewModel.manageFavorite(venueViewData)
    }

    override fun onItemClicked(venueViewData: VenueViewData) {
        showHideBottomBarListener.showVenueDetail(venueViewData)
    }

    override fun inject() {
        VenueHistoryComponent.Injector.inject(this)
    }

}
