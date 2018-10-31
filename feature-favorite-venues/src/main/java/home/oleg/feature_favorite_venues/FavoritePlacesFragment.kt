package home.oleg.feature_favorite_venues

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.smedialink.common.Optional
import com.smedialink.feature_add_favorite.CreateFavoriteViewModel

import java.util.ArrayList

import javax.inject.Inject

import home.oleg.feature_favorite_venues.di.FavoriteVenuesComponent
import home.oleg.placesnearme.core_presentation.ShowHideBottomBarListener
import home.oleg.placesnearme.core_presentation.recyclerview.ItemViewType
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData

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
        Optional.of(activity as AppCompatActivity?)
                .ifPresent { appCompatActivity ->
                    appCompatActivity.setSupportActionBar(toolbar)
                    appCompatActivity.supportActionBar!!.setTitle(R.string.fragment_title_favorite)
                }

        favoritePlacesViewModel.state.observe(this, Observer { venueViewItems ->
            if (venueViewItems == null || venueViewItems.isEmpty()) {
                adapter.showEmpty()
            } else {
                adapter.submitList(ArrayList<ItemViewType>(venueViewItems))
            }
        })
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
