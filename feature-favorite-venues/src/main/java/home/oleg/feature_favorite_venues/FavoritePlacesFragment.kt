package home.oleg.feature_favorite_venues

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smedialink.feature_add_favorite.CreateFavoriteViewModel
import home.oleg.feature_favorite_venues.di.FavoriteVenuesComponent
import home.oleg.placesnearme.core_presentation.ShowHideBottomBarListener
import home.oleg.placesnearme.core_presentation.extensions.observeNonNull
import home.oleg.placesnearme.core_presentation.recyclerview.ItemViewType
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import javax.inject.Inject

class FavoritePlacesFragment : Fragment(), FavoriteVenuesAdapter.FavoriteClicksListener {

    @Inject
    lateinit var favoritePlacesViewModel: FavoritePlacesViewModel

    @Inject
    lateinit var createFavoriteViewModel: CreateFavoriteViewModel

    @Inject
    lateinit var adapter: FavoriteVenuesAdapter

    lateinit var showHideBottomBarListener: ShowHideBottomBarListener

    override fun onAttach(context: Context?) {
        injectDependencies()
        super.onAttach(context)
        //todo -> navigator
        if (context is ShowHideBottomBarListener) {
            this.showHideBottomBarListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_places, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvFavorites = view.findViewById<RecyclerView>(R.id.rvFavoriteVenues)

        rvFavorites.layoutManager = LinearLayoutManager(context)
        rvFavorites.adapter = adapter

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)?.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setTitle(R.string.fragment_title_favorite)
        }

        favoritePlacesViewModel.state.observeNonNull(this) { venueViewItems ->
            if (venueViewItems.isEmpty()) {
                adapter.showEmpty()
            } else {
                adapter.submitList(ArrayList<ItemViewType>(venueViewItems))
            }
        }
    }

    override fun favoriteClicked(venueViewData: VenueViewData) {
        createFavoriteViewModel.manageFavorite(venueViewData)
    }

    override fun onItemClicked(venueViewData: VenueViewData) {
        showHideBottomBarListener.showVenueDetail(venueViewData)
    }

    private fun injectDependencies() {
        FavoriteVenuesComponent.Injector.inject(this)
    }

}
