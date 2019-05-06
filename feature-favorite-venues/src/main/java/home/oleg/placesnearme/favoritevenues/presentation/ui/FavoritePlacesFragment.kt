package home.oleg.placesnearme.favoritevenues.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.smedialink.common.base.BaseFragment
import com.smedialink.common.base.handle
import com.smedialink.common.ext.observeExt
import com.smedialink.common.recyclerview.ItemViewType
import home.oleg.placesnearme.corepresentation.api.ShowVenueDetail
import home.oleg.placesnearme.corepresentation.delegate.ToastDelegate
import home.oleg.placesnearme.corepresentation.viewdata.PlaceViewData
import home.oleg.placesnearme.favoritevenues.R
import home.oleg.placesnearme.favoritevenues.di.FavoriteVenuesComponent
import home.oleg.placesnearme.favoritevenues.presentation.FavoritePlacesViewModel
import kotlinx.android.synthetic.main.fragment_favorite_places.*
import javax.inject.Inject

class FavoritePlacesFragment : BaseFragment(), FavoriteVenuesAdapter.FavoriteClicksListener {

    @Inject
    lateinit var favoritePlacesViewModel: FavoritePlacesViewModel

    @Inject
    lateinit var adapter: FavoriteVenuesAdapter

    lateinit var showVenueDetail: ShowVenueDetail

    override val layoutRes: Int = R.layout.fragment_favorite_places

    @Inject
    lateinit var toastDelegate: ToastDelegate

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        context?.let(toastDelegate::attach)

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

        favoritePlacesViewModel.state.observeExt(this) {
            if (it.isEmpty()) {
                adapter.showEmpty()
            } else {
                adapter.submitList(ArrayList<ItemViewType>(it))
            }
        }

      /*  favoritePlacesViewModel.favoriteMessage.observeExt(this) { message ->
            message.handle { toastDelegate.showSuccess(it.text) }
        }*/
    }

    override fun favoriteClicked(venueViewData: PlaceViewData) {
        favoritePlacesViewModel.updateFavorite(venueViewData)
    }

    override fun onItemClicked(venueViewData: PlaceViewData) {
        showVenueDetail.showVenueDetail(venueViewData)
    }

    override fun inject() {
        FavoriteVenuesComponent.Injector.inject(this)
    }

}
