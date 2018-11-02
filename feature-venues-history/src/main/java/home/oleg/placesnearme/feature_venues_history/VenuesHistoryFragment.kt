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
import home.oleg.placesnearme.core_presentation.recyclerview.ItemViewType
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import home.oleg.placesnearme.feature_venues_history.di.VenueHistoryComponent
import javax.inject.Inject

class VenuesHistoryFragment : Fragment(), HistoryVenuesAdapter.HistoryClicksListener {

    @Inject
    lateinit var historyVenuesAdapter: HistoryVenuesAdapter

    @Inject
    lateinit var placesHistoryViewModel: VenuesHistoryViewModel

    @Inject
    lateinit var createFavoriteViewModel: CreateFavoriteViewModel

    lateinit var showHideBottomBarListener: ShowHideBottomBarListener

    override fun onAttach(context: Context?) {
        injectDependencies()
        super.onAttach(context)
        if (context is ShowHideBottomBarListener) {
            this.showHideBottomBarListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_places_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvHistoryVenues = view.findViewById<RecyclerView>(R.id.rvHistoryVenues)

        rvHistoryVenues.layoutManager = LinearLayoutManager(context)
        rvHistoryVenues.adapter = historyVenuesAdapter

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)?.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setTitle(R.string.history_title)
        }

        placesHistoryViewModel.state.observe(this, Observer { venues ->
            if (venues?.isEmpty() == true) {
                historyVenuesAdapter.showEmpty()
            } else {
                venues?.let {
                    val list: List<ItemViewType> = it
                    historyVenuesAdapter.submitList(list)
                }
            }
        })
    }

    private fun injectDependencies() {
        VenueHistoryComponent.Injector.inject(this)
    }

    override fun favoriteClicked(venueViewData: VenueViewData) {
        createFavoriteViewModel.manageFavorite(venueViewData)
    }

    override fun onItemClicked(venueViewData: VenueViewData) {
        showHideBottomBarListener.showVenueDetail(venueViewData)
    }

}
