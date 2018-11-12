package home.oleg.feature_favorite_venues.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.smedialink.feature_add_favorite.presentation.CreateFavoriteViewModel
import home.oleg.feature_favorite_venues.R
import home.oleg.feature_favorite_venues.di.FavoriteVenuesComponent
import home.oleg.placesnearme.core_presentation.ShowHideBottomBarListener
import home.oleg.placesnearme.core_presentation.base.BaseFragment
import home.oleg.placesnearme.core_presentation.extensions.observe
import home.oleg.placesnearme.core_presentation.recyclerview.ItemViewType
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import kotlinx.android.synthetic.main.fragment_favorite_places.*
import javax.inject.Inject

class FavoritePlacesFragment : BaseFragment(), FavoriteVenuesAdapter.FavoriteClicksListener {

    override val layoutRes: Int = R.layout.fragment_favorite_places

    @Inject
    lateinit var favoritePlacesViewModel: FavoritePlacesViewModel

    @Inject
    lateinit var createFavoriteViewModel: CreateFavoriteViewModel

    @Inject
    lateinit var adapter: FavoriteVenuesAdapter

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
        rvFavoriteVenues.layoutManager = LinearLayoutManager(context)
        rvFavoriteVenues.adapter = adapter

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        (activity as AppCompatActivity?)?.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setTitle(R.string.fragment_title_favorite)
        }

        observe(favoritePlacesViewModel.state) {
            if (it.isEmpty()) {
                adapter.showEmpty()
            } else {
                adapter.submitList(ArrayList<ItemViewType>(it))
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
        FavoriteVenuesComponent.Injector.inject(this)
    }

}
