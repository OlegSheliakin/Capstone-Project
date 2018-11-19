package home.oleg.placesnearme.feature_favorite_venues.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.smedialink.common.base.BaseFragment
import com.smedialink.common.base.handle
import com.smedialink.common.ext.observe
import com.smedialink.common.recyclerview.ItemViewType
import home.oleg.placesnearme.core_presentation.api.ShowVenueDetail
import home.oleg.placesnearme.core_presentation.delegate.ToastDelegate
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import home.oleg.placesnearme.feature_favorite_venues.R
import home.oleg.placesnearme.feature_favorite_venues.di.FavoriteVenuesComponent
import home.oleg.placesnearme.feature_favorite_venues.presentation.FavoritePlacesViewModel
import kotlinx.android.synthetic.main.fragment_favorite_places.*
import javax.inject.Inject

class FavoritePlacesFragment : BaseFragment(), FavoriteVenuesAdapter.FavoriteClicksListener {

    @Inject
    lateinit var favoritePlacesViewModel: FavoritePlacesViewModel

    @Inject
    lateinit var toastDelegate: ToastDelegate

    @Inject
    lateinit var adapter: FavoriteVenuesAdapter

    lateinit var showVenueDetail: ShowVenueDetail

    override val layoutRes: Int = R.layout.fragment_favorite_places

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        //todo -> navigator
        if (context is ShowVenueDetail) {
            this.showVenueDetail = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFavoriteVenues.layoutManager = LinearLayoutManager(context)
        rvFavoriteVenues.adapter = adapter
        rvFavoriteVenues.setHasFixedSize(true)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setTitle(R.string.fragment_title_favorite)
        }

        favoritePlacesViewModel.state.observe(this) {
            if (it.isEmpty()) {
                adapter.showEmpty()
            } else {
                adapter.submitList(ArrayList<ItemViewType>(it))
            }
        }

        favoritePlacesViewModel.favoriteMessage.observe(this) { message ->
            message.handle { toastDelegate.showSuccess(it.text) }
        }
    }

    override fun favoriteClicked(venueViewData: VenueViewData) {
        favoritePlacesViewModel.updateFavorite(venueViewData)
    }

    override fun onItemClicked(venueViewData: VenueViewData) {
        showVenueDetail.showVenueDetail(venueViewData)
    }

    override fun inject() {
        FavoriteVenuesComponent.Injector.inject(this)
    }

}
