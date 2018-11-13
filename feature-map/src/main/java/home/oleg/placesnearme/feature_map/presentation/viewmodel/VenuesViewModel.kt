package home.oleg.placesnearme.feature_map.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import home.oleg.placenearme.models.Section
import home.oleg.placenearme.models.Venue
import home.oleg.placesnearme.core_presentation.base.BaseViewModel
import home.oleg.placesnearme.core_presentation.delegate.disposableDelegate
import com.home.olegsheliakin.corettools.error_handler.ErrorHandler
import home.oleg.placesnearme.core_presentation.mapper.VenueMapViewMapper
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData
import home.oleg.placesnearme.feature_map.presentation.state.MapViewState
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
        private val fetchRecommended: (Section) -> Single<Pair<Section, List<Venue>>>) : BaseViewModel() {

    private val venuesHolder = VenuesViewModel.VenuesHolder()

    private var searchDisposable: Disposable? by disposableDelegate()

    private val stateInternal = MutableLiveData<MapViewState>()
    private val dataInternal = MutableLiveData<List<PreviewVenueViewData>>()

    val state = stateInternal
    val data = dataInternal

    init {
        stateInternal.value = MapViewState.initial()
    }

    fun getRecommendedVenues(section: Section) {
        searchDisposable = fetchRecommended(section)
                .map { sectionListPair -> VenueMapViewMapper.map(sectionListPair.first, sectionListPair.second) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    changeState { it.copy(error = null, isVenuesLoading = true) }
                }
                .subscribe({ venues ->
                    changeState { it.copy(isVenuesLoading = false) }
                    dataInternal.setValue(venues)
                }, { throwable ->
                    changeState { it.copy(isVenuesLoading = false, error = errorHandler.handle(throwable)) }
                })

    }

    override fun onCleared() {
        super.onCleared()
        searchDisposable = null
    }

    fun openSearch() {
        changeState { it.copy(isSearchShown = true) }
    }

    fun closeSearch() {
        changeState { it.copy(isSearchShown = false) }
    }

    fun setVenues(venues: Map<String, PreviewVenueViewData>) {
        venuesHolder.set(venues)
    }

    fun getVenue(id: String): PreviewVenueViewData {
        return venuesHolder[id]
    }

    private fun changeState(function: (MapViewState) -> MapViewState) {
        stateInternal.value?.let {
            stateInternal.value = function(it)
        }
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
