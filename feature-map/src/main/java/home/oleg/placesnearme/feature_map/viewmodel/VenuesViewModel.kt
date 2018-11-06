package home.oleg.placesnearme.feature_map.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import home.oleg.placenearme.models.Section
import home.oleg.placenearme.models.Venue
import home.oleg.placesnearme.core_presentation.error_handler.ErrorHandler
import home.oleg.placesnearme.core_presentation.mapper.VenueMapViewMapper
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData
import home.oleg.placesnearme.feature_map.state.MapViewState
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by Oleg on 10.08.2018.
 */
class VenuesViewModel(
        private val errorHandler: ErrorHandler,
        private val fetchRecommended: (Section) -> Single<Pair<Section, List<Venue>>>) : ViewModel() {

    private var searchDisposable: Disposable? = null
    private val venuesHolder = VenuesViewModel.VenuesHolder()

    private val state = MutableLiveData<MapViewState>()
    private val data = MutableLiveData<List<PreviewVenueViewData>>()

    init {
        state.value = MapViewState.initial()
    }

    fun getState(): LiveData<MapViewState> = state

    fun getData(): LiveData<List<PreviewVenueViewData>> = data

    fun getRecommendedVenues(section: Section) {
        searchDisposable?.takeUnless { it.isDisposed }?.dispose()

        searchDisposable = fetchRecommended(section)
                .map { sectionListPair -> VenueMapViewMapper.map(sectionListPair.first, sectionListPair.second) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _ ->
                    changeState { it.copy(error = null, isVenuesLoading = true) }
                }
                .subscribe({ venues ->
                    changeState { it.copy(isVenuesLoading = false) }
                    data.setValue(venues)
                }, { throwable ->
                    changeState { it.copy(isVenuesLoading = false, error = errorHandler.handle(throwable)) }
                })
    }

    fun openSearch() {
        changeState { it.copy(isSearchShown = true) }
    }

    fun closeSearch() {
        changeState { it.copy(isSearchShown = false) }
    }

    private fun changeState(function: (MapViewState) -> MapViewState) {
        state.value?.let {
            state.value = function(it)
        }
    }

    fun setVenues(venues: Map<String, PreviewVenueViewData>) {
        venuesHolder.set(venues)
    }

    fun getVenue(id: String): PreviewVenueViewData {
        return venuesHolder[id]
    }

    private class VenuesHolder {

        private var venues: Map<String, PreviewVenueViewData> = mutableMapOf()

        fun set(venues: Map<String, PreviewVenueViewData>) {
            this.venues = HashMap(venues)
        }

        operator fun get(id: String): PreviewVenueViewData {
            return venues[id]!!
        }
    }

}
